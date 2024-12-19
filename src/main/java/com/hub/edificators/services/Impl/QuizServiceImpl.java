package com.hub.edificators.services.Impl;

import com.hub.edificators.commons.GenericModelMapper;
import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.requests.payment.ListStudentQuizRequest;
import com.hub.edificators.dtos.requests.quiz.CreateQuizRequest;
import com.hub.edificators.dtos.requests.quiz.QuizSubmitRequest;
import com.hub.edificators.dtos.responses.quiz.AttemptQuizResponse;
import com.hub.edificators.dtos.responses.quiz.CourseQuizListResponse;
import com.hub.edificators.dtos.responses.quiz.QuizStatusListResponse;
import com.hub.edificators.dtos.responses.quiz.StudentQuizListResponse;
import com.hub.edificators.entities.*;
import com.hub.edificators.enums.Flag;
import com.hub.edificators.enums.QuizStatus;
import com.hub.edificators.repos.*;
import com.hub.edificators.services.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private QuizRepo quizRepo;
    @Autowired
    private QuizQuestionRepo quizQuestionRepo;
    @Autowired
    private QuizAnswerRepo quizAnswerRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    @Autowired
    private QuizSubmissionRepo quizSubmissionRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public GenericResponse createQuiz(CreateQuizRequest request) {
        try {
            logger.info("CreateQuizRequest() -> request received.");
            Quiz quiz = new Quiz();
            quiz.setTitle(request.getTitle());
            quiz.setDuration(request.getDuration());
            Course course = courseRepo.getReferenceById(request.getCourseId());
            quiz.setCourse(course);
            quiz.setNoOfQuestions(String.valueOf(request.getNoOfQuestions()));

            // Save Quiz
            quiz = quizRepo.save(quiz);

            // Create Questions
            List<QuizQuestion> questions = new ArrayList<>();
            for (CreateQuizRequest.QuizQuestionRequest questionRequest : request.getQuestions()) {
                QuizQuestion question = new QuizQuestion();
                question.setText(questionRequest.getText());
                question.setQuiz(quiz);
                question = quizQuestionRepo.save(question);

                // Create Answers
                for (CreateQuizRequest.QuizAnswerRequest answerRequest : questionRequest.getAnswers()) {
                    QuizAnswer answer = new QuizAnswer();
                    answer.setAnswer(answerRequest.getAnswer());
                    answer.setCorrect(answerRequest.isCorrect());
                    answer.setQuizQuestion(question);
                    quizAnswerRepo.save(answer);
                }
                questions.add(question);
            }

            logger.info("createQuiz() -> request completed.");
            return GenericResponse.createSuccessResponse("Quiz Created Successfully", null, HttpStatus.CREATED.value());

        } catch (
                Exception ex) {
            ex.printStackTrace();
            logger.error("createQuiz() -> Exception occurred.");
            throw new BusinessException("createQuiz() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse listCourseQuiz(Long courseId) {
        try {
            logger.info("listCourseQuiz() -> request received.");
            List<Quiz> quizes = quizRepo.findByCourseId(courseId);
            List<CourseQuizListResponse> listResponse = new ArrayList<>();
            for (Quiz quiz : quizes) {
                CourseQuizListResponse response = GenericModelMapper.mapObject(quiz, CourseQuizListResponse.class);
                listResponse.add(response);
            }
            logger.info("listCourseQuiz() -> request completed.");
            return GenericResponse.createSuccessResponse("List of Course Quizzes fetched successfully", listResponse, HttpStatus.CREATED.value());


        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("listCourseQuiz() -> Exception occurred.");
            throw new BusinessException("listCourseQuiz() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse quizStatusList(long quizId) {
        try {
            logger.info("quizStatusList() -> request received.");
            List<QuizStatusListResponse> responseList = new ArrayList<>();
            Quiz quiz = quizRepo.getReferenceById(quizId);
            List<Enrollment> enrollments = enrollmentRepo.findByCourseIdAndFlag(quiz.getCourse().getId(), Flag.ACTIVE);
            for (Enrollment enrollment : enrollments) {
                QuizStatusListResponse response = new QuizStatusListResponse();
                response.setFullName(enrollment.getStudent().getUser().getFullName());
                response.setOutOf(quiz.getNoOfQuestions());

                Optional<QuizSubmission> submission = quizSubmissionRepo
                        .findByStudentIdAndQuizId(enrollment.getStudent().getId(), quizId);
                if (submission.isPresent()) {
                    response.setMarks(submission.get().getMarks());
                    response.setStatus(submission.get().getStatus().toString());
                } else {
                    response.setMarks("Zero");
                    response.setStatus(String.valueOf(QuizStatus.NOT_SUBMIT));
                }

                responseList.add(response);

            }
            logger.info("quizStatusList() -> request completed.");
            return GenericResponse.createSuccessResponse("List of Quiz Status fetched successfully", responseList, HttpStatus.CREATED.value());

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("quizStatusList() -> Exception occurred.");
            throw new BusinessException("quizStatusList() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse listStudentQuiz(ListStudentQuizRequest request) {
        try {
            logger.info("listStudentQuiz() -> request received.");
            List<Quiz> quizList = quizRepo.findByCourseIdAndFlag(request.getCourseId(), Flag.ACTIVE);
            List<StudentQuizListResponse> responseList = new ArrayList<>();
            for (Quiz quiz : quizList) {
                List<QuizQuestion> quizQuestions = quizQuestionRepo.findByQuizId(quiz.getId());
                StudentQuizListResponse response = new StudentQuizListResponse();
                response.setOutOf(String.valueOf(quizQuestions.size()));
                response.setId(quiz.getId());
                response.setTitle(quiz.getTitle());
                response.setDuration(quiz.getDuration());
                Optional<QuizSubmission> quizSubmission = quizSubmissionRepo.findByStudentIdAndQuizId(request.getStudentId(), quiz.getId());
                if (quizSubmission.isPresent()) {
                    response.setStatus(String.valueOf(quizSubmission.get().getStatus()));
                    response.setMarks(quizSubmission.get().getMarks() == null ? "0" : quizSubmission.get().getMarks());
                } else {
                    response.setStatus(QuizStatus.NOT_SUBMIT.name());
                    response.setMarks("0");
                }
                responseList.add(response);
            }
            logger.info("listStudentQuiz() -> request completed.");
            return GenericResponse.createSuccessResponse("Student Quiz list Fetched successfully!", responseList, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("listStudentQuiz() -> Exception occurred.");
            throw new BusinessException("listStudentQuiz() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse quizAttempt(long quizId) {
        try {
            logger.info("quizAttempt() -> request received.");
            List<AttemptQuizResponse> responseList = new ArrayList<>();
            Quiz quiz = quizRepo.getReferenceById(quizId);
            List<QuizQuestion> questions = quizQuestionRepo.findByQuizId(quizId);
            for (QuizQuestion question : questions) {
                AttemptQuizResponse response = new AttemptQuizResponse();
                List<QuizAnswer> answerList = quizAnswerRepo.findByQuizQuestionId(question.getId());
                response.setQuestion(question.getText());
                response.setQuestionId(question.getId());
                response.setAnswer1(answerList.get(0).getAnswer());
                response.setAnswer1Id(answerList.get(0).getId());
                response.setAnswer2(answerList.get(1).getAnswer());
                response.setAnswer2Id(answerList.get(1).getId());
                response.setAnswer3(answerList.get(2).getAnswer());
                response.setAnswer3Id(answerList.get(2).getId());
                response.setAnswer4(answerList.get(3).getAnswer());
                response.setAnswer4Id(answerList.get(3).getId());
                response.setDuration(quiz.getDuration());
                responseList.add(response);
            }
            logger.info("quizAttempt() -> request completed.");
            return GenericResponse.createSuccessResponse("Quiz Attempt successfully!", responseList, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("quizAttempt() -> Exception occurred.");
            throw new BusinessException("quizAttempt() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse quizSubmit(QuizSubmitRequest request) {
        QuizSubmission submission = new QuizSubmission();
        Quiz quiz = quizRepo.getReferenceById(request.getQuizId());
        submission.setQuiz(quiz);
        Student student = studentRepo.getReferenceById(request.getStudentId());
        submission.setStudent(student);

        if(request.getQuestionAnwerList().isEmpty()){
            submission.setStatus(QuizStatus.SUBMITTED);
            submission.setMarks("0");
            quizSubmissionRepo.save(submission);
            return GenericResponse.createSuccessResponse("Quiz Attempt Successfully!", null, HttpStatus.OK.value());
        }

        Map<Long, QuizSubmitRequest.QuestionAnswer> questionAnswerMap = request.getQuestionAnwerList().stream()
                .collect(Collectors.toMap(QuizSubmitRequest.QuestionAnswer::getQuestionId, qa -> qa));
        List<QuizQuestion> questionList = quizQuestionRepo.findByQuizId(request.getQuizId());
        int count = 0;
        if (questionList.size() == Integer.valueOf(quiz.getNoOfQuestions()) && questionList.size() == request.getQuestionAnwerList().size()) {
            for (QuizQuestion question : questionList) {
                QuizSubmitRequest.QuestionAnswer questionAnswer = questionAnswerMap.get(question.getId());
                QuizAnswer answer = quizAnswerRepo.getReferenceById(questionAnswer.getAnswerId());
                if (answer.isCorrect()) {
                    count += 1;
                }
            }
            submission.setStatus(QuizStatus.SUBMITTED);
            submission.setMarks(String.valueOf(count));
            quizSubmissionRepo.save(submission);
        }
        return GenericResponse.createSuccessResponse("Quiz Attempt Successfully!", null, HttpStatus.OK.value());
    }
}



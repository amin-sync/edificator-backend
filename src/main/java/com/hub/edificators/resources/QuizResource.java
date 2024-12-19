package com.hub.edificators.resources;

import com.hub.edificators.dtos.requests.payment.ListStudentQuizRequest;
import com.hub.edificators.dtos.requests.quiz.CreateQuizRequest;
import com.hub.edificators.dtos.requests.quiz.QuizSubmitRequest;
import com.hub.edificators.services.QuizService;
import com.hub.edificators.validators.QuizResourseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
public class QuizResource {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizResourseValidator quizResourseValidator;

    @PostMapping("/create")
    public ResponseEntity createQuiz(@RequestBody CreateQuizRequest request) {
        quizResourseValidator.validate(request);
        return ResponseEntity.ok(quizService.createQuiz(request));
    }

    @PostMapping("/submit")
    public ResponseEntity submitQuiz(@RequestBody QuizSubmitRequest request) {
        quizResourseValidator.validate(request);
        return ResponseEntity.ok(quizService.quizSubmit(request));
    }

    @GetMapping("/attempt")
    public ResponseEntity quizInterface(@RequestParam Long quizId){
        quizResourseValidator.validate("Quiz ID ",quizId.toString());
        return ResponseEntity.ok(quizService.quizAttempt(quizId));
    }

    @GetMapping("/list-by-teacher")
    public ResponseEntity teacherQuizList(@RequestParam Long courseId){
        quizResourseValidator.validate("Course ID",courseId.toString());
        return ResponseEntity.ok(quizService.listCourseQuiz(courseId));
    }

    @PostMapping("/list-by-student")
    public ResponseEntity StudentQuizList(@RequestBody ListStudentQuizRequest request){
        quizResourseValidator.validate(request);
        return ResponseEntity.ok(quizService.listStudentQuiz(request));
    }

    @GetMapping("/status-list")
    public ResponseEntity quizStatusList(@RequestParam Long quizId){
        quizResourseValidator.validate("Quiz ID",quizId.toString());
        return ResponseEntity.ok(quizService.quizStatusList(quizId));
    }
}

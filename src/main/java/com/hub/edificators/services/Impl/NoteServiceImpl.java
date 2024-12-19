package com.hub.edificators.services.Impl;

import com.hub.edificators.commons.GenericModelMapper;
import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.constants.AppConstants;
import com.hub.edificators.dtos.requests.note.CreateNoteRequest;
import com.hub.edificators.dtos.responses.course.TeacherCourseListResponse;
import com.hub.edificators.dtos.responses.note.TeacherNoteListResponse;
import com.hub.edificators.entities.Course;
import com.hub.edificators.entities.Note;
import com.hub.edificators.enums.Flag;
import com.hub.edificators.repos.CourseRepo;
import com.hub.edificators.repos.NoteRepo;
import com.hub.edificators.repos.TeacherRepo;
import com.hub.edificators.services.NoteService;
import com.hub.edificators.utils.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private NoteRepo noteRepo;

    @Override
    public GenericResponse createNote(CreateNoteRequest request, MultipartFile file) {
        try {
            logger.info("createNote() -> request received.");

            Course course = courseRepo.getReferenceById(Long.valueOf(request.getCourseId()));
            Note note = new Note();
            note.setTitle(request.getTitle());
            note.setCourse(course);
            note = noteRepo.save(note);

            String fileName = FileUtility.saveFile(note.getId(), AppConstants.NOTES_FILE_DIRECTORY, file);
            note.setFileName(fileName);
            noteRepo.save(note);

            logger.info("createNote() -> request completed.");
            return GenericResponse.createSuccessResponse("Note Created successfully", null, HttpStatus.CREATED.value());

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("createNote() -> Exception occurred.");
            throw new BusinessException("createNote() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse deleteNoteById(long noteId) {
        try {
            logger.info("deleteNoteById() -> request received.");
            Note note = noteRepo.getReferenceById(noteId);
            note.setFlag(Flag.INACTIVE);
            noteRepo.save(note);
            logger.info("deleteNoteById() -> request completed.");
            return GenericResponse.createSuccessResponse("Note Deleted successfully", null, HttpStatus.CREATED.value());

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("deleteNoteById() -> Exception occurred.");
            throw new BusinessException("deleteNoteById() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse listTeacherNote(long courseId) {
        try {
            logger.info("listTeacherNote()-> Request Received.!");
            List<Note> noteList = noteRepo.findByCourseIdAndFlag(courseId, Flag.ACTIVE);
            List<TeacherNoteListResponse> responseList = new ArrayList<>();
            for (Note note : noteList) {
                TeacherNoteListResponse response = GenericModelMapper.mapObject(note, TeacherNoteListResponse.class);
                responseList.add(response);
            }
            logger.info("listTeacherNote() -> request completed.");
            return GenericResponse.createSuccessResponse("Notes Fetched successfully", responseList, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("listTeacherNote() -> Exception occurred.");
            throw new BusinessException("listTeacherNote() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}

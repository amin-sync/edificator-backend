package com.hub.edificators.resources;

import com.hub.edificators.dtos.requests.note.CreateNoteRequest;
import com.hub.edificators.services.NoteService;
import com.hub.edificators.validators.NoteResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/note")
public class NoteResource {

    @Autowired
    private NoteResourceValidator noteResourceValidator;

    @Autowired
    private NoteService noteService;

    @PostMapping("/create")
    public ResponseEntity createNote(@RequestParam String title, @RequestParam String courseId, @RequestParam("file") MultipartFile file) {
        CreateNoteRequest request = CreateNoteRequest.builder().title(title).courseId(courseId).build();
        noteResourceValidator.validate(request);
        return ResponseEntity.ok(noteService.createNote(request, file));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteNote(@RequestParam Long noteId) {
        noteResourceValidator.validate("Note ID", noteId.toString());
        return ResponseEntity.ok(noteService.deleteNoteById(noteId));
    }

    @GetMapping("/list")
    public ResponseEntity getTeacherNoteList(@RequestParam Long courseId) {
        noteResourceValidator.validate("Course ID", courseId.toString());
        return ResponseEntity.ok(noteService.listTeacherNote(courseId));
    }
}

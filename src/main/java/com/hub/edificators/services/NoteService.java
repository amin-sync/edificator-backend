package com.hub.edificators.services;

import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.dtos.requests.note.CreateNoteRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface NoteService {

    GenericResponse createNote(CreateNoteRequest request, MultipartFile file);

    GenericResponse deleteNoteById(long noteId);

    GenericResponse listTeacherNote(long teacherId);
}

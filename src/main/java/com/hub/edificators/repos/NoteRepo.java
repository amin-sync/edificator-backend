package com.hub.edificators.repos;

import com.hub.edificators.entities.Note;
import com.hub.edificators.enums.Flag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note,Long> {

        List<Note> findByCourseIdAndFlag(Long courseId, Flag flag);
}

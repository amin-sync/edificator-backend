package com.hub.edificators.repos;

import com.hub.edificators.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement,Long> {
    List<Announcement> findByCourseId(Long courseId);
}

package com.hub.edificators.repos;

import com.hub.edificators.entities.Role;
import com.hub.edificators.enums.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
}

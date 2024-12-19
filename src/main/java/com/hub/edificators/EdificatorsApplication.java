package com.hub.edificators;

import com.hub.edificators.entities.Role;
import com.hub.edificators.enums.AccessLevel;
import com.hub.edificators.repos.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EdificatorsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EdificatorsApplication.class, args);
    }

    @Autowired
    RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {
        long roleCount = roleRepo.count();
        if (roleCount == 0) {
            Role adminRole = new Role();
            adminRole.setName(AccessLevel.ADMIN);

            Role teacherRole = new Role();
            teacherRole.setName(AccessLevel.TEACHER);

            Role studentRole = new Role();
            studentRole.setName(AccessLevel.STUDENT);

            List<Role> roles = new ArrayList<>();
            roles.add(adminRole);
            roles.add(teacherRole);
            roles.add(studentRole);
            roleRepo.saveAll(roles);
        }


    }
}

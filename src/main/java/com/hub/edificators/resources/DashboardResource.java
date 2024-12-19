package com.hub.edificators.resources;

import com.hub.edificators.services.DashboardService;
import com.hub.edificators.validators.DashboardResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardResource {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private DashboardResourceValidator dashboardResourceValidator;

    @GetMapping("/teacher")
    public ResponseEntity teacherDashboard(@RequestParam Long teacherId) {
        dashboardResourceValidator.validate("Teacher ID", teacherId.toString());
        return ResponseEntity.ok(dashboardService.teacherDashboard(teacherId));
    }

    @GetMapping("/student")
    public ResponseEntity studentDashboard(@RequestParam Long studentId){
        dashboardResourceValidator.validate("Student ID",studentId.toString());
        return ResponseEntity.ok(dashboardService.studentDashboard(studentId));
    }
}

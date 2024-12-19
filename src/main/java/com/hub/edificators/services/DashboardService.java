package com.hub.edificators.services;

import com.hub.edificators.commons.GenericResponse;


public interface DashboardService {
    GenericResponse teacherDashboard(long teacherId);
    GenericResponse studentDashboard(long studentId);
}

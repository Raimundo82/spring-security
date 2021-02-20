package com.raims.springsecurity.student;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "management")
public class StudentManagementController {

    private final StudentService studentService;

    @GetMapping(path = "/students")
    @PreAuthorize("hasAuthority('student:read') AND hasAnyRole('ROLE_ADMIN','ROLE_NEW_ADMIN')")
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    @DeleteMapping(path = "/delete/{studentId}")
    @PreAuthorize("hasAuthority('student:write') AND hasAnyRole('ROLE_ADMIN')")
    public void deleteStudentById(@PathVariable("studentId") Integer id) {
        studentService.deleteStudentById(id);
    }

}

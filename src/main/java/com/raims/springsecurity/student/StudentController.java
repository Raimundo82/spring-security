package com.raims.springsecurity.student;

import com.raims.springsecurity.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "student")
public class StudentController {

    private final StudentService studentService;
    private final RegistrationService registrationService;


    @GetMapping(path = "/{studentId}")
    @PreAuthorize("hasAuthority('student:read') OR hasAnyRole('ROLE_STUDENT')")
    public StudentDto getStudentById(@PathVariable("studentId") Integer id) {
        return studentService.getStudentById(id);
    }

    @PutMapping(path = "/update/{studentId}")
    @PreAuthorize("hasAuthority('student:write') OR hasAnyRole('ROLE_STUDENT')")
    public void updateStudentById(
            @PathVariable("studentId") Integer id,
            @RequestBody StudentDto studentDto) {
        registrationService.updateStudentById(id, studentDto);
    }


}

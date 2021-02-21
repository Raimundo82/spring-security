package com.raims.springsecurity.registration;

import com.raims.springsecurity.auth.AuthUser;
import com.raims.springsecurity.auth.AuthUserService;
import com.raims.springsecurity.exceptions.AppException;
import com.raims.springsecurity.security.AppUserRole;
import com.raims.springsecurity.student.Student;
import com.raims.springsecurity.student.StudentDto;
import com.raims.springsecurity.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static com.raims.springsecurity.exceptions.ErrorMessage.EMAIL_INVALID;
import static com.raims.springsecurity.exceptions.ErrorMessage.PASSWORD_INVALID;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final StudentService studentService;
    private final AuthUserService authUserService;
    private static final String EMAIL_REGEX = "^(.+)@(.+)$";
    private static final String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[0-9]).{8,}$";


    public void userRegistration(RegistrationRequest request, String role) {
        checkEmailRegex(request.getEmail());

        if (patternTest(PASSWORD_REGEX, request.getPassword()))
            throw new AppException(PASSWORD_INVALID, request.getPassword());

        authUserService.registerUser(new AuthUser(
                        request.getPassword(),
                        request.getEmail(),
                        AppUserRole.valueOf(role)
                )
        );

        if (role.equals(AppUserRole.STUDENT.name()))
            studentService.addStudent(new Student(
                            request.getFirstName(),
                            request.getLastName(),
                            request.getEmail(),
                            LocalDate.parse(request.getDob())
                    )
            );
    }

    private void checkEmailRegex(String email) {
        if (patternTest(EMAIL_REGEX, email))
            throw new AppException(EMAIL_INVALID, email);
    }


    private boolean patternTest(String patternRegex, String value) {
        return !Pattern.compile(patternRegex).matcher(value).matches();
    }

    public void updateStudentById(Integer id, StudentDto studentDto) {
        checkEmailRegex(studentDto.getEmail());
        authUserService.checkIfEmailExists(studentDto.getEmail());
        studentService.updateStudent(id, studentDto);
    }
}

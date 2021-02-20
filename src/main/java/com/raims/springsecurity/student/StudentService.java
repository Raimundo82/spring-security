package com.raims.springsecurity.student;

import com.raims.springsecurity.exceptions.AppException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import static com.raims.springsecurity.exceptions.ErrorMessage.*;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public StudentDto getStudentById(Integer id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new AppException(STUDENT_NOT_FOUND_ID, id)
        );
        return new StudentDto(student);
    }

    public Integer addStudent(Student student) {
        checkIfEmailExists(student.getEmail());
        checkIfIsAdult(student.getAge());
        student.setAge(student.getAge());
        student.setPassword(bcryptPasswordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
        return student.getId();
    }

    private void checkIfIsAdult(Integer age) {
        if (age < 18)
            throw new AppException(STUDENT_IS_NOT_ADULT, age);
    }

    private void checkIfEmailExists(String email) {
        if (studentRepository.findStudentByEmail(email).isPresent())
            throw new AppException(EMAIL_ALREADY_REGISTERED, email);
    }


    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentDto::new)
                .collect(Collectors.toList());
    }

    public void deleteStudentById(Integer id) {
        if (studentRepository.findById(id).isEmpty()) {
            throw new AppException(STUDENT_NOT_FOUND_ID, id);
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Integer id, StudentDto studentDto) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new AppException(STUDENT_NOT_FOUND_ID, id));

        if (!student.getEmail().equals(studentDto.getEmail())) {
            checkIfEmailExists(studentDto.getEmail());
            student.setEmail(studentDto.getEmail());
        }
        LocalDate newDob = LocalDate.parse(studentDto.getDob());

        if (!student.getDob().equals(newDob)) {
            checkIfIsAdult(Period.between(newDob, LocalDate.now()).getYears());
            student.setDob(newDob);
            student.setAge(student.getAge());
        }

        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

    }
}

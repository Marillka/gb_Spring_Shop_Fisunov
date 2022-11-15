package ru.rayumov.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rayumov.market.entities.StudentEntity;
import ru.rayumov.market.repositories.StudentRepository;
import ru.rayumov.market.soap.students.Student;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public static final Function<StudentEntity, Student> functionEntityToSoap = se -> {
        Student s = new Student();
        s.setId(se.getId());
        s.setName(se.getName());
        s.setAge(se.getAge());
        s.setGroupTitle(se.getGroup().getTitle());
        return s;
    };

    public List<Student> getAllStudents() {
        return studentRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public Student getByName(String name) {
        return studentRepository.findByName(name).map(functionEntityToSoap).get();
    }
}

package com.example.crudspring.service;

import com.example.crudspring.entity.Student;
import com.example.crudspring.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Optional<Student> getStudent(Long id){
        return studentRepository.findById(id);
    }

    public void saveOrUpdate(Student studentNvo){
        studentRepository.save(studentNvo);
    }
    public Optional<Student> updateStudent(Student student, Long studentId){
        Optional<Student> studentData = studentRepository.findById(studentId);
        if(studentData.isPresent()){
            Student _student = studentData.get();
            _student.setFirstName(student.getFirstName());
            _student.setLastName(student.getLastName());
            _student.setEmail(student.getEmail());
            studentRepository.save(_student);
        }

        return studentData;
    }

    public  Optional<Student> deleteStudent(Long id){
        Optional<Student> studentData = studentRepository.findById(id);
        if(studentData.isPresent()){
            studentRepository.deleteById(id);
        }
        return studentData;
    }
}

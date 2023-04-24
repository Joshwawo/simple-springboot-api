package com.example.crudspring.service;

import com.example.crudspring.entity.Teacher;
import com.example.crudspring.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacher(Long id){
        return  teacherRepository.findById(id);
    }

    public void saveTeacher(Teacher teacher){
        teacherRepository.save(teacher);
    }

    public Optional<Teacher> updateTeacher(Teacher teacher, Long teacherId){
        Optional<Teacher> teacherData = teacherRepository.findById(teacherId);
        if(teacherData.isPresent()){
            Teacher _teacher = teacherData.get();
            _teacher.setFirstName(teacher.getFirstName());
            _teacher.setLastName(teacher.getLastName());
            _teacher.setEmail(teacher.getEmail());
            teacherRepository.save(_teacher);
        }
        return teacherData;
    }

    public Optional<Teacher> deleteTeacher(Long id){
        Optional<Teacher> teacherData = teacherRepository.findById(id);
        if(teacherData.isPresent()){
            teacherRepository.deleteById(id);
        }
        return teacherData;
    }
}

package com.example.crudspring.controller;

import com.example.crudspring.entity.Teacher;
import com.example.crudspring.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/teachers")
public class teacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public List<Teacher> getAll(){
        return teacherService.getTeachers();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getTeacher(@PathVariable("id") Long teacherId){
        Optional<Teacher> teacher = teacherService.getTeacher(teacherId);

        if(teacher.isEmpty()){
            Map<String,Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "No existe el profesor");
            errorResponse.put("StatusCode",404);
            return ResponseEntity.status(404).body(errorResponse);
        }
        return ResponseEntity.ok(teacher);
    }

    @PostMapping
    public ResponseEntity<?> createTeacher(@RequestBody Teacher teacher ){
        try {
           teacherService.saveTeacher(teacher);
           return ResponseEntity.ok(teacher);

        }catch (DataIntegrityViolationException error){
            return ResponseEntity.status(409).body(error);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable("id") Long TeacherId, @RequestBody Teacher teacher){
        try {
            Optional<Teacher> teacherData = teacherService.updateTeacher(teacher, TeacherId);
            if(teacherData.isEmpty()){
                Map<String,Object> errorResponse = new HashMap<>();
                errorResponse.put("message", "No existe el profesor, que intentas actualizar");
                errorResponse.put("StatusCode",404);
                return ResponseEntity.status(404).body(errorResponse);
            }
            return ResponseEntity.ok(teacherData);

        }catch (DataIntegrityViolationException error){
            return ResponseEntity.status(409).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable("id") Long teacherId){
        try {
            Optional<Teacher> teacher = teacherService.deleteTeacher(teacherId);
            if(teacher.isEmpty()){
                Map<String,Object> errorResponse = new HashMap<>();
                errorResponse.put("message", "No existe el profesor, que intentas eliminar");
                errorResponse.put("StatusCode",404);
                return ResponseEntity.status(404).body(errorResponse);
            }
            Map<String,Object> successResponse = new HashMap<>();
            successResponse.put("message", "Profesor eliminado con exito");
            successResponse.put("StatusCode",200);
            return ResponseEntity.ok(successResponse);
        }catch (DataIntegrityViolationException error){
            return ResponseEntity.status(409).body(error);
        }
    }


}

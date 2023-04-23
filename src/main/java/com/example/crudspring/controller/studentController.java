package com.example.crudspring.controller;

import com.example.crudspring.entity.Student;
import com.example.crudspring.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/students")
public class studentController {
    @Autowired
    private  StudentService studentService;

    @GetMapping
    public List<Student> getAll(){
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getStudent(@PathVariable("id") Long stundentId){
        Optional<Student> student = studentService.getStudent(stundentId);

        if(student.isEmpty()){
            Map<String,Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "No existe el estudiante");
            errorResponse.put("StatusCode",404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student){
            try{
                studentService.saveOrUpdate(student);
                return ResponseEntity.ok(student);
            }catch (DataIntegrityViolationException error){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error.getRootCause());

            }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") Long StundentId,@RequestBody Student student){
        try{
            Optional<Student> studentData = studentService.updateStudent(student, StundentId);
            if(studentData.isEmpty()){
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("message", "No existe el estudiante, que intentas actualizar");
                errorResponse.put("statusConde", 404);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

            return ResponseEntity.ok(studentData);
        }catch (DataIntegrityViolationException error){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error.getRootCause());

        }

    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") Long studentId){
        try{
            Optional<Student> studentData = studentService.deleteStudent(studentId);
            if(studentData.isEmpty()){
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("message", "No existe el estudiante, que intentas eliminar");
                errorResponse.put("statusCode", 404);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            //hola
            return ResponseEntity.ok(
                    Map.of("message", "Estudiante eliminado correctamente", "statusCode", 200, "Data", studentData)
            );

        }catch (DataIntegrityViolationException error){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getRootCause());
        }
    }

}

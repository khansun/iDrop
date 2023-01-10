package io.khansun.iDrop.Controllers;

import io.khansun.iDrop.Models.Student;
import io.khansun.iDrop.Services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = studentService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @GetMapping("/find/{ID}")
    public ResponseEntity<Student> getStudent(@PathVariable("ID") Long id) {
        Student student = studentService.findStudentById(id);
        if(student.getId() == null)
            return new ResponseEntity<>(student, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        Student newStudent = studentService.addStudent(student);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        Student updatedStudent = studentService.updateStudent(student);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{ID}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("ID") Long id) {
        Student student = studentService.findStudentById(id);
        if(student.getId() == null)
            return new ResponseEntity<>(student, HttpStatus.NOT_FOUND);
        studentService.deleteStudent(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}

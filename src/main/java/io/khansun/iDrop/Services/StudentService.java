package io.khansun.iDrop.Services;

import io.khansun.iDrop.Exceptions.userNotFoundException;
import io.khansun.iDrop.Models.Student;
import io.khansun.iDrop.Repos.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepo studentRepo;
    @Autowired
    public StudentService(StudentRepo studentRepo){
        this.studentRepo = studentRepo;
    }

    public Student addStudent(Student student){
        student.setId(Long.valueOf(UUID.randomUUID().toString()));
        return studentRepo.save(student);
    }

    public List<Student>findAllStudents(){
        return studentRepo.findAll();
    }

    public Student findStudentById(Long id){
        return studentRepo.findStudentBtId(id).orElseThrow(() -> new userNotFoundException("This ID does not exit for any user"));
    }

    public Student updateStudent(Student student){
        return studentRepo.save(student);
    }

    public void deleteStudent(Long id){
        studentRepo.deleteStudentById(id);
    }


}

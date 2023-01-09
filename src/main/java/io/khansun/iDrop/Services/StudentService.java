package io.khansun.iDrop.Services;

import io.khansun.iDrop.Exceptions.userNotFoundException;
import io.khansun.iDrop.Models.Student;
import io.khansun.iDrop.Repos.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;


@Service
public class StudentService {
    private final StudentRepo studentRepo;
    @Autowired
    public StudentService(StudentRepo studentRepo){
        this.studentRepo = studentRepo;
    }

    public Student addStudent(Student student){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        student.setId(timestamp.getTime());
        return studentRepo.save(student);
    }

    public List<Student>findAllStudents(){
        return studentRepo.findAll();
    }

    public Student findStudentById(Long id){
        return studentRepo.findStudentById(id).orElseThrow(() -> new userNotFoundException("This ID does not exit for any user"));
    }

    public Student updateStudent(Student student){
        return studentRepo.save(student);
    }

    public void deleteStudent(Long id){
        studentRepo.deleteStudentById(id);
    }


}

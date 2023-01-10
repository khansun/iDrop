package io.khansun.iDrop.Services;

import io.khansun.iDrop.Models.Student;
import io.khansun.iDrop.Repos.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class StudentService {
    private final StudentRepo studentRepo;
    @Autowired
    public StudentService(StudentRepo studentRepo){
        this.studentRepo = studentRepo;
    }

    public Student addStudent(Student student){
        return studentRepo.save(student);
    }

    public List<Student>findAllStudents(){
        return studentRepo.findAll();
    }

    public Student findStudentById(Long id){
        Student unknownStudent = new Student();
        unknownStudent.setName("Unknown");
        return studentRepo.findStudentById(id).orElse(unknownStudent);
    }

    public Student updateStudent(Student student){
        return studentRepo.save(student);
    }

    public void deleteStudent(Long id){
        studentRepo.deleteStudentById(id);
    }


}

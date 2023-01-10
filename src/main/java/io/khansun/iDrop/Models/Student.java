package io.khansun.iDrop.Models;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String department;
    private String roomNumber;
    private int phoneNumber;
    private String photoURL;
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
    public Student(){}
    public Student(Long id, String name, String department, String roomNumber, int phoneNumber, String photoURL) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.roomNumber = roomNumber;
        this.phoneNumber = phoneNumber;
        this.photoURL = photoURL;
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }

}

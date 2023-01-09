package io.khansun.iDrop.Models;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private int id;
    private String name;
    private String department;
    private String roomNumber;
    private int phoneNumber;
    private String photoURL;
}

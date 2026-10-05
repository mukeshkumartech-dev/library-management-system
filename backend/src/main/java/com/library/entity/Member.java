package com.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "members")
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "membership_number", unique = true, nullable = false)
    private String membershipNumber;
    
    @Column(nullable = false)
    private String name;
    
    private String email;
    private String phone;
    private String address;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    
    private String status = "ACTIVE"; // ACTIVE, INACTIVE
    
    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDate.now();
    }
}

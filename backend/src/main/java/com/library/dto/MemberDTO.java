package com.library.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class MemberDTO {
    private Long id;
    
    @NotBlank(message = "Membership number is required")
    private String membershipNumber;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @Email(message = "Invalid email")
    private String email;
    
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDate registrationDate;
    private String status;
}

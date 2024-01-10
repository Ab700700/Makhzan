package com.example.makhzan.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LandLordDTO {

    private Integer userid;
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 4,max = 10, message = "Password should not be empty")
    private String username;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max=20, message = "Name should be between 2 characters and 20 characters")
    private String name;
    @NotEmpty(message = "Role should not be empty")
    @Pattern(regexp = "CUSTOMER|LANDLORD|ADMIN",message = "Role should be customer, employee or admin")
    private String role;
    @NotEmpty(message = "Phone number should not be empty")
    @Size(min=10,max = 10,message = "Phone number should be 10 numbers")
    private String phonenumber;
    @NotNull(message = "is company cannot be null")
    private Boolean isCompany;
    //@NotNull(message = "license cannot be null")
    private String license;
}

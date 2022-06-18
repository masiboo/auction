package com.cognizant.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String id;
    @NotEmpty(message = "fullN name is mandatory")
    private String fullName;
    @Size(min = 4, max = 24, message = "username must have a minimum of 4 characters and maximum 24")
    private String userName;
    @Email(message = "please provide a valid email address")
    private String email;
    @Size(min = 8, message = "password must have a minimum of 8 characters")
    private String password;
    @NotEmpty(message = "userType is mandatory")
    private UserType userType;
}

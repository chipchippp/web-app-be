package com.example.webapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @JsonProperty("full_name")
    private String fullName;

    private String email;
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    private String password;
    @JsonProperty("confirm_password")
    private String confirmPassword;
    private String address;
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
    @JsonProperty("facebook_account_id")
    private Integer facebookAccountId;

    @JsonProperty("google_account_id")
    private Integer googleAccountId;
    @NotNull(message = "Role id is required")
    @JsonProperty("role_id")
    private Long roleId;
}

package com.thangavel.bankmanagement.dto.response;

import com.thangavel.bankmanagement.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String authToken;
    private String refreshToken;
    private Role role;
}

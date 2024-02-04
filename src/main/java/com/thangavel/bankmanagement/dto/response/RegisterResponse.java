package com.thangavel.bankmanagement.dto.response;

import com.thangavel.bankmanagement.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterResponse {
    private final String authToken;
    private final String refreshToken;
    private final Role role;
}

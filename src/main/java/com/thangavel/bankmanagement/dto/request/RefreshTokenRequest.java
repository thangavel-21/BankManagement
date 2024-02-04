package com.thangavel.bankmanagement.dto.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String authToken;
    private String refreshToken;
}

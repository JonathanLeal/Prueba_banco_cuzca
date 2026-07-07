package com.banco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;
    
    @Builder.Default
    private String type = "Bearer";

    public LoginResponseDTO(String token) {
        this.token = token;
        this.type = "Bearer";
    }
}
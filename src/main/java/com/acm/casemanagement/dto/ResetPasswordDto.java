package com.acm.casemanagement.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResetPasswordDto {
    private String username;
    private String oldPassword;
    private String newPassword;

}

package com.ssag.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordForm {

//	@Length(min = 8, max = 50)
    private String newPassword;
//    @Length(min = 8, max = 50)
    private String newPasswordConfirm;
}

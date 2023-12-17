package com.ic.votemachinev1.DTOs.Common;

import com.ic.votemachinev1.Model.UsersEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseDTO<T> {
    private UsersEntity currentUser;
    private T data;
    private HttpStatus status;
    private String message;
    private String redirect;
    private boolean error;
}

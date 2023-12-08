package com.ic.votemachinev1.Utils;


import com.ic.votemachinev1.Model.UsersEntity;
import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
//@SessionScope
@NoArgsConstructor
@Setter
@Getter
public class SessionData implements Serializable {

    private UsersEntity usersEntity;
    private String token;

}



package com.ic.votemachinev1.DTOs.Voter;

import com.ic.votemachinev1.Model.ConstituenciesEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class VoterDTO {

    Long Id;
    String name;
    Long cnic;

    String email;
    String password;
    ConstituenciesEntity residentialConstituency;
    Integer otp;
    Integer userInputOTP;
    LocalDateTime otpExpiration;


}

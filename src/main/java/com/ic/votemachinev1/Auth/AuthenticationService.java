package com.ic.votemachinev1.Auth;

import com.ic.votemachinev1.Auth.AuthenticationRequest;
import com.ic.votemachinev1.DTOs.Common.ResponseDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.ConstituenciesEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface AuthenticationService {


    public String authenticate(Model model, AuthenticationRequest authenticationRequest);

    List<ConstituenciesEntity> AllConstituencies();

    ResponseDTO SaveVoter(Model model,VoterDTO voterDTO);

     String invalidateSession(HttpServletRequest request);


    Integer generateRandomOtp();

    String VoterRegistration(Model model);


    ResponseDTO<String> verifyOtp(String email, int otp);




}

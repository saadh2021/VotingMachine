package com.ic.votemachinev1.Auth;

import com.ic.votemachinev1.DTOs.Common.ResponseDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Repository.ConstituencyRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final ModelMapper modelMapper;

    private final ConstituencyRepository constituencyRepository;

    @GetMapping("/login")
    public String Login(Model model) {

         ResponseDTO responseDTO = new ResponseDTO<>();
         model.addAttribute("responseDTO", responseDTO);


        return "login";
    }


    @PostMapping("/Auth/Login")
    public String ShowDashboardAfterSuccessLogin(Model model, AuthenticationRequest authenticationRequest ) throws IOException {

        return authenticationService.authenticate(model, authenticationRequest);


    }


    @GetMapping("/VoterRegistration")

    public String VoterRegistration(Model model) {
        return authenticationService.VoterRegistration(model);
    }

    @PostMapping("/OTPVerification")
    public String VerifyOTP(Model model, @ModelAttribute VoterDTO voterDTO) {
        ResponseDTO responseDTO = authenticationService.verifyOtp(voterDTO.getEmail(), voterDTO.getUserInputOTP());
        if (responseDTO.getStatus() == HttpStatus.OK) {
            return "VoterSaved";
        } else {
            model.addAttribute("newVoterDTO", voterDTO);
            model.addAttribute("responseDTO", responseDTO);

            System.out.println("The Status Code is: " + " " + responseDTO.getStatus() + "Msg" + responseDTO.getMessage());

            return "OtpVerification";
        }


    }

    @GetMapping("/invalidateSession")
    public String invalidateSession(HttpServletRequest request) {
        return authenticationService.invalidateSession(request);
    }


    @PostMapping("/SaveVoter")
    public String SaveVoter(Model model, @ModelAttribute VoterDTO voterDTO) {

        ResponseDTO responseDTO = authenticationService.SaveVoter(model, voterDTO);

        model.addAttribute("newVoterDTO", voterDTO);
        model.addAttribute("responseDTO", responseDTO);

        if (responseDTO.getStatus() == null) {
            return "OtpVerification";
        } else {

            model.addAttribute("AvailableAddress", authenticationService.AllConstituencies());
            return "VoterRegistration";
        }
    }


}




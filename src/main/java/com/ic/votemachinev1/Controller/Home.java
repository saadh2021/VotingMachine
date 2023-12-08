package com.ic.votemachinev1.Controller;

import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Service.Imp.AdminServiceImp;
import com.ic.votemachinev1.Service.Imp.HomeServiceImp;
import com.ic.votemachinev1.Utils.SessionData;
import com.ic.votemachinev1.Security.JWTUtility;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class Home {

    private final ModelMapper modelMapper;
    private final HomeServiceImp homeServiceImp;
    private final SessionData sessionData;
    private final AdminServiceImp userService;
    private final JWTUtility helper;

    @GetMapping("/login")
    public String VoterRegistration() {
        return "login";
    }


  /*  @PostMapping("/auth/login/{username}/{password}")
    public String ShowDashboardAfterSuccessLogin(@RequestParam(name="username") String userName,
                                                 @RequestParam(name="password") String password,
                                                 @RequestParam("profilePicture") MultipartFile profilePicture
    ) throws IOException {

        homeServiceImp.ShowDashboardAfterAuthentication(userName, password, profilePicture);

        return homeServiceImp.getWhereToRedirect();
    }*/

       @PostMapping("/auth/login/{username}/{password}")
    public String ShowDashboardAfterSuccessLogin(@PathVariable("username") String userName,
                                                 @PathVariable("password") String password,
                                                 @PathVariable("profilePicture") MultipartFile profilePicture
    ) throws IOException {

        homeServiceImp.ShowDashboardAfterAuthentication(userName, password, profilePicture);
           System.out.println(sessionData.getToken());

        return homeServiceImp.getWhereToRedirect();
    }


    @GetMapping("/VoterRegistration")

    public String VoterRegistration(Model model) {
        UsersEntity usersEntity = new UsersEntity();

        VoterDTO voterDTO = modelMapper.map(usersEntity, VoterDTO.class);
        model.addAttribute("voterDTO", voterDTO);
        model.addAttribute("AvailableAddress", GetAvaialbleAddress());
        return "VoterRegisteration";

    }

    @PostMapping("/SaveVoter")
    public String SaveVoter(@ModelAttribute VoterDTO voterDTO) {
        homeServiceImp.SaveVoter(voterDTO);
        return "VoterSaved";
    }


    public List<ConstituenciesEntity> GetAvaialbleAddress() {
        List<ConstituenciesEntity> addressList = homeServiceImp.AllConstituencies();
        return addressList;
    }
}




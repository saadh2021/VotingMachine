package com.ic.votemachinev1.Service.Imp;

import com.cloudinary.Cloudinary;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.UserRolesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Repository.ConstituencyRepository;
import com.ic.votemachinev1.Repository.UsersRepository;
import com.ic.votemachinev1.Security.JWTUtility;
import com.ic.votemachinev1.Service.Home;
import com.ic.votemachinev1.Utils.SessionData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Getter
@RequiredArgsConstructor
public class HomeServiceImp implements Home {

    private final SessionData sessionData;

    private final AdminServiceImp userService;

    private final UsersRepository usersRepository;

    private final Cloudinary cloudinary;

    private final JWTUtility helper;

    private final ConstituencyRepository constituencyRepository;

     String whereToRedirect;

    @Override
    public String ShowDashboardAfterAuthentication(String userName, String password, MultipartFile profilePicture) {
        try {
            UsersEntity userDetails = userService.loadUserByUsername(userName);
            sessionData.setUsersEntity(userDetails);
            String token = helper.generateToken(userDetails);
            sessionData.setToken(token);

            if (!profilePicture.isEmpty()) {
                Map<String, String> uploadResult = cloudinary.uploader().upload(profilePicture.getBytes(), Map.of("transformation", "w_100,h_100,c_fill"));
                userDetails.setUserImg((String) uploadResult.get("public_id"));
                usersRepository.save(userDetails);
            }


            if (userDetails.getRole().equals(UserRolesEntity.Voter)) {
                whereToRedirect = "redirect:/voter/dashboard";

                return whereToRedirect;
            } else if (userDetails.getRole().equals(UserRolesEntity.Admin)) {
                whereToRedirect = "redirect:/admin/dashboard";
                return whereToRedirect;
            } else {
                whereToRedirect = "redirect:/candidate/dashboard";
                return whereToRedirect;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

            whereToRedirect = "redirect:/login";
            return whereToRedirect;
        }
    }

    @Override
    public List<ConstituenciesEntity> AllConstituencies() {
        List<ConstituenciesEntity> constituenciesEntities = constituencyRepository.findAll();
        return constituenciesEntities;
    }

    @Override
    public void SaveVoter(VoterDTO voterDTO) {
        UsersEntity usersEntity = new UsersEntity();//modelMapper.map(voterDTO, UsersEntity.class);
        usersEntity.setUserName(voterDTO.getEmail());
        usersEntity.setName(voterDTO.getName());
        usersEntity.setCnic(voterDTO.getCnic());

        usersEntity.setPassword(voterDTO.getPassword());
        Optional<ConstituenciesEntity> constituenciesEntity =
                constituencyRepository.findById(voterDTO.getResidentialConstituency());
        if (constituenciesEntity.isPresent()) usersEntity.setResidentialConstituency(constituenciesEntity.get());

        usersRepository.save(usersEntity);


    }
}

package com.ic.votemachinev1.Auth;

import com.cloudinary.Cloudinary;
import com.ic.votemachinev1.DTOs.Common.ResponseDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.UserAccountStatus;
import com.ic.votemachinev1.Model.UserRolesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Repository.ConstituencyRepository;
import com.ic.votemachinev1.Repository.UsersRepository;
import com.ic.votemachinev1.Security.JWTUtility;
import com.ic.votemachinev1.Service.Imp.AdminServiceImp;
import com.ic.votemachinev1.Service.Imp.MailService;
import com.ic.votemachinev1.Utils.SessionData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@Getter
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

    private final SessionData sessionData;

    private final AdminServiceImp userService;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;


    private final UsersRepository usersRepository;

    private final Cloudinary cloudinary;

    private final JWTUtility helper;
    private final MailService mailService;

    private final ConstituencyRepository constituencyRepository;

    String whereToRedirect;


    @Override
    public String authenticate(Model model, AuthenticationRequest authenticationRequest) {


        try {
            UsersEntity userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());
            sessionData.setUsersEntity(userDetails);
            String token = helper.generateToken(userDetails);
            sessionData.setToken(token);
            if (!passwordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {
                whereToRedirect = "redirect:/login";
                return whereToRedirect;
            }

            if (!authenticationRequest.getProfilePicture().isEmpty()) {
                Map<String, String> uploadResult = cloudinary.uploader().upload(authenticationRequest.getProfilePicture().getBytes(), Map.of("transformation", "w_100,h_100,c_fill"));
                userDetails.setUserImg((String) uploadResult.get("public_id"));
                usersRepository.save(userDetails);
            }


            if (userDetails.getRole().equals(UserRolesEntity.Voter)) {
                whereToRedirect = "redirect:/Voter/Dashboard";

                return whereToRedirect;
            } else if (userDetails.getRole().equals(UserRolesEntity.Admin)) {
                whereToRedirect = "redirect:/Admin/Dashboard";
                return whereToRedirect;
            } else {
                whereToRedirect = "redirect:/Candidate/Dashboard";
                return whereToRedirect;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ResponseDTO responseDTO = new ResponseDTO<>();
            responseDTO.setMessage(e.getMessage());
            //redirectAttributes.addFlashAttribute("responseDTO", responseDTO);'
            System.out.println("Login error msg is:" + e.getMessage());
            whereToRedirect = "redirect:/login";
            return whereToRedirect;
        }
    }

    @Override
    public String invalidateSession(HttpServletRequest request) {
        // Invalidate the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login"; // Redirect to the login page or any other page
    }

    @Override
    public List<ConstituenciesEntity> AllConstituencies() {
        List<ConstituenciesEntity> constituenciesEntities = constituencyRepository.findAll();
        return constituenciesEntities;
    }


    @Override
    public ResponseDTO SaveVoter(Model model, VoterDTO voterDTO) {


        System.out.println("The Resi is" + voterDTO.getResidentialConstituency().getConstituencyName());
        Optional<UsersEntity> optionalUserEntity = usersRepository.findByEmail(voterDTO.getEmail());
        Optional<UsersEntity> optionalUsersEntityByCnic = usersRepository.findByCnicAndStatus(voterDTO.getCnic(), UserAccountStatus.VERIFIED);
        Integer randomOtp = generateRandomOtp();

        if (optionalUsersEntityByCnic.isPresent()) {
            System.out.println("User is Already Present with this CNIC");
            return new ResponseDTO<>(null, null, HttpStatus.BAD_REQUEST, "CNIC Already Exists!!!", null, true);
        }

        if (optionalUserEntity.isPresent()) {
            UsersEntity userEntity = optionalUserEntity.get();


            if (userEntity.getStatus() == UserAccountStatus.VERIFIED) {
                return new ResponseDTO<>(null, null, HttpStatus.BAD_REQUEST, "Email Already Exists!!!", null, true);

            }


            UsersEntity usersEntity = optionalUserEntity.get();
            usersEntity.setEmail(voterDTO.getEmail());
            usersEntity.setName(voterDTO.getName());
            usersEntity.setCnic(voterDTO.getCnic());
            usersEntity.setStatus(UserAccountStatus.UNVERIFIED);
            usersEntity.setOtp(randomOtp);
            usersEntity.setResidentialConstituency(voterDTO.getResidentialConstituency());
            usersEntity.setRole(UserRolesEntity.Voter);
            usersEntity.setPassword(passwordEncoder.encode(voterDTO.getPassword()));
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime expireTime = currentDateTime.plusMinutes(15);
            usersEntity.setOtpExpiration(expireTime);
            model.addAttribute("optExpiryTime", usersEntity.getOtpExpiration());

            if (!mailService.sendVerificationEmail(voterDTO.getEmail(), randomOtp)) {
                return new ResponseDTO<>(null, null, HttpStatus.BAD_GATEWAY, "Failed To send Email!!!", null, true);
            }
            usersRepository.save(userEntity);
            // return new ResponseDTO<>(null, null, HttpStatus.OK, "A verification Email is sent on your Email!!!", null, false);
            return new ResponseDTO<>();
        } else {


            UsersEntity usersEntity = new UsersEntity();//modelMapper.map(voterDTO, UsersEntity.class);
            usersEntity.setEmail(voterDTO.getEmail());
            usersEntity.setName(voterDTO.getName());
            usersEntity.setCnic(voterDTO.getCnic());
            usersEntity.setResidentialConstituency(voterDTO.getResidentialConstituency());

            usersEntity.setStatus(UserAccountStatus.UNVERIFIED);
            usersEntity.setOtp(randomOtp);
            usersEntity.setRole(UserRolesEntity.Voter);
            usersEntity.setPassword(passwordEncoder.encode(voterDTO.getPassword()));
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime expireTime = currentDateTime.plusMinutes(15);

            usersEntity.setOtpExpiration(expireTime);
            model.addAttribute("optExpiryTime", usersEntity.getOtpExpiration());

            if (!mailService.sendVerificationEmail(voterDTO.getEmail(), randomOtp)) {
                return new ResponseDTO<>(null, null, HttpStatus.BAD_GATEWAY, "Failed To send Email!!!", null, true);
            }

            usersRepository.save(usersEntity);

            // return new ResponseDTO<>(null, null, HttpStatus.OK, "A verification Email is sent on your Email!!!", null, false);
            return new ResponseDTO<>();

        }
    }


    @Override
    public Integer generateRandomOtp() {
        int otpLength = 6;
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }
        return Integer.parseInt(String.valueOf(otp));
    }

    @Override
    public String VoterRegistration(Model model) {
        UsersEntity usersEntity = new UsersEntity();
        Integer randomOtp = generateRandomOtp();
        VoterDTO newVoterDTO = modelMapper.map(usersEntity, VoterDTO.class);
        model.addAttribute("newVoterDTO", newVoterDTO);
        model.addAttribute("AvailableAddress", AllConstituencies());
        ResponseDTO responseDTO = new ResponseDTO();
        model.addAttribute("responseDTO", responseDTO);
        return "VoterRegistration";

    }

    @Override
    public ResponseDTO<String> verifyOtp(String email, int otp) {
        System.out.println("The Email to Search is: " + email);
        Optional<UsersEntity> optionalUserEntity = usersRepository.findByEmail(email);
        if (optionalUserEntity.isEmpty()) {
            return new ResponseDTO<>(null, null, HttpStatus.BAD_REQUEST, "Email Does Not Exists Please Register First!!!", null, true);
        }
        UsersEntity user = optionalUserEntity.get();
        if (user.getOtp() == otp) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime otpExpiration = user.getOtpExpiration();
            boolean isExpired = currentDateTime.isAfter(otpExpiration);
            if (isExpired) {

                return new ResponseDTO<>(null, null, HttpStatus.BAD_REQUEST, "OTP expired Please register again!!!", null, true);
            }

            user.setStatus(UserAccountStatus.VERIFIED);
            usersRepository.save(user);
            return new ResponseDTO<>(null, null, HttpStatus.OK, "Email Verified Successfully!!!", null, false);
        } else {
            return new ResponseDTO<>(null, null, HttpStatus.BAD_REQUEST, "Invalid OTP!!!", null, true);
        }
    }


}

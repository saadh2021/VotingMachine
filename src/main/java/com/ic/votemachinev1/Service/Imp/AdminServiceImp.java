package com.ic.votemachinev1.Service.Imp;

import com.cloudinary.Cloudinary;
import com.ic.votemachinev1.DTOs.Admin.ConstituencyDTO;
import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.DTOs.Candidate.CandidatesOrPartyDTO;
import com.ic.votemachinev1.DTOs.DateTimeDTO.VotingTimeDTO;
import com.ic.votemachinev1.DTOs.Party.ElectionResultsDTO;
import com.ic.votemachinev1.DTOs.Party.PartyDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.PartiesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Model.VotingTimeEntity;
import com.ic.votemachinev1.Repository.ConstituencyRepository;
import com.ic.votemachinev1.Repository.PartiesRepository;
import com.ic.votemachinev1.Repository.UsersRepository;
import com.ic.votemachinev1.Repository.VotingTimeRepository;
import com.ic.votemachinev1.Service.AdminService;
import com.ic.votemachinev1.Utils.SessionData;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ic.votemachinev1.Model.UserRolesEntity.Candidate;
import static com.ic.votemachinev1.Model.UserRolesEntity.Voter;

@Service
@RequiredArgsConstructor

public class AdminServiceImp implements AdminService, UserDetailsService {

    private final UsersRepository userRepository;

    private final ConstituencyRepository constituencyRepository;

    private final ModelMapper modelMapper;

    private final EmailService emailService;


    private final VotingTimeRepository votingTimeRepository;





    @Override
    public void SaveConstituency(ConstituencyDTO constituencyName) {
        ConstituenciesEntity constituenciesEntity = new ConstituenciesEntity();
        constituenciesEntity.setConstituencyName(constituencyName.getName());
        constituencyRepository.save(constituenciesEntity);

    }

    @Override
    public void ApproveCandidate(Long cnic) {

        UsersEntity ToBeApprovedCandidate = userRepository.findByCnic(cnic);
        ToBeApprovedCandidate.setApprovedCandidate(true);
        ToBeApprovedCandidate.setRole(Candidate);
        ToBeApprovedCandidate.party.partyPresident = ToBeApprovedCandidate;

        userRepository.save(ToBeApprovedCandidate);


    }

    @Override
    public void SendVoterCandidateInvite(Long cnic) {

        UsersEntity voter = userRepository.findByCnic(cnic);
        AllCandidateOrPartyDTO candidateOrPartyDTO = modelMapper.map(voter, AllCandidateOrPartyDTO.class);
        // Send email to admin
        String adminEmail = "saad.intern@devsinc.com";
        String subject = "Request To be a Candiate";
        String message = "You are requested to be a candidate.Please Click on the link below and fill the form" + "     " + "http://localhost:8080/voter/ApplyForCandidate/" + cnic;
        ;
        emailService.sendApprovalEmail(adminEmail, subject, message);
    }


    @Override
    public UsersEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}

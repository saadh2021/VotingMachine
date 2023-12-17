package com.ic.votemachinev1.Service.Imp;

import com.ic.votemachinev1.DTOs.Admin.ConstituencyDTO;
import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.DTOs.DateTimeDTO.VotingTimeDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTOToFetch;
import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.PartiesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Repository.ConstituencyRepository;
import com.ic.votemachinev1.Repository.UsersRepository;
import com.ic.votemachinev1.Service.AdminService;
import com.ic.votemachinev1.Service.VotingTimeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.ic.votemachinev1.Model.UserRolesEntity.Candidate;

@Service
@RequiredArgsConstructor

public class AdminServiceImp implements AdminService, UserDetailsService {

    private final UsersRepository userRepository;

    private final ConstituencyRepository constituencyRepository;

    private final ModelMapper modelMapper;

    private final EmailService emailService;


    private final VotingTimeService votingTimeServiceImp;

    private final CommonServiceImp commonServiceImp;

    @Override
    public ModelAndView ShowDashBoard(Model model) {


        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("profilePicture", commonServiceImp.ShowProfilePic());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        ModelAndView modelAndView = new ModelAndView("Admin/AdminDashboard");

        return modelAndView;


    }

    @Override

    public String ElectionResults(int page, int size, Model model) {
        Page<PartiesEntity> parties = commonServiceImp.getSortedParties(page, size);
        model.addAttribute("Parties", parties);
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        return "Admin/ElectionResults";
    }

    @Override

    public String ResultsByPartyNames(Model model, String search, int page, int size) {
        PartiesEntity filteredParty = commonServiceImp.SearchByPartyName(search); // Fetch the list of voters from the service

        model.addAttribute("filteredParty", filteredParty);
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        return "Admin/ElectionResults"; // Thymeleaf template for displaying the list

    }

    @Override

    public String listVoters(Model model) {
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        List<VoterDTOToFetch> voters = commonServiceImp.ListAllVoters(); // Fetch the list of voters from the service

        model.addAttribute("voters", voters);
        return "Admin/VoterList"; // Thymeleaf template for displaying the list
    }

    @Override

    public String ListAllApprovedCandidates(Model model) {
        model.addAttribute("approvedCandidate", commonServiceImp.ListAllApprovedCandidate());
        model.addAttribute("SessionData", commonServiceImp.getSessionData());
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        return "Admin/CastVoteDashBoard"; // Thymeleaf template for displaying the list
    }

    @Override

    public String SaveCastedVote(@RequestParam("PartyID") Long partyID, Model model) {
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        commonServiceImp.SaveCastedVote(partyID);
        return "Admin/VoteCastedSuccess";
    }

    @Override

    public String ScheduleElections(VotingTimeDTO votingTimeDTO, Model model) {

        votingTimeServiceImp.ScheduleElections(votingTimeDTO);
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());


        return "Admin/ElectionScheduledSuccess";
    }

    @Override

    public String Logout() {
        commonServiceImp.Logout();
        return "redirect:/login";
    }

    @Override

    public String VoterByCNIC(Model model, @RequestParam Long search) {
        model.addAttribute("filteredVoter", commonServiceImp.searchVoter(search));
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        return "Admin/VoterList"; // Thymeleaf template for displaying the list

    }

    @Override

    public String InviteVoterToCandidate(Model model) {

        List<VoterDTOToFetch> voters = commonServiceImp.ListAllVoters(); // Fetch the list of voters from the service
        model.addAttribute("voters", voters);
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());

        return "Admin/VoterListWithInvitation";
    }


    @Override
    public String SaveConstituency(ConstituencyDTO constituencyName, Model model) {
        ConstituenciesEntity constituenciesEntity = new ConstituenciesEntity();
        constituenciesEntity.setConstituencyName(constituencyName.getName());
        constituencyRepository.save(constituenciesEntity);
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        return "Admin/AdminDashboard";

    }

    @Override

    public String ScheduleElection(Model model) {
        VotingTimeDTO votingTimeDTO = new VotingTimeDTO();
        model.addAttribute("VotingSchedule", votingTimeDTO);
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        return "Admin/ScheduleElection";

    }


    @Override
    public void ApproveCandidate(Model model,Long cnic) {

        UsersEntity ToBeApprovedCandidate = userRepository.findByCnic(cnic);
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        ToBeApprovedCandidate.setApprovedCandidate(true);
        ToBeApprovedCandidate.setRole(Candidate);
        ToBeApprovedCandidate.party.partyPresident = ToBeApprovedCandidate;

        userRepository.save(ToBeApprovedCandidate);


    }


    @Override
    public String SendVoterCandidateInvite(Model model, Long cnic) {

        UsersEntity voter = userRepository.findByCnic(cnic);
        AllCandidateOrPartyDTO candidateOrPartyDTO = modelMapper.map(voter, AllCandidateOrPartyDTO.class);
        // Send email to admin
        String adminEmail = "saad.intern@devsinc.com";
        String subject = "Request To be a Candidate";
        String message = "You are requested to be a candidate.Please Click on the link below and fill the form" + "     " + "http://localhost:8080/Voter/ApplyForCandidate/" + cnic;

        emailService.sendApprovalEmail(adminEmail, subject, message);
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        return "/Admin/InviteSendForVoterToCandidate";
    }

    @Override

    public String listCandidates(Model model) {

        model.addAttribute("Candidates", commonServiceImp.ListAllPartiesOrCandidates());
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        return "Admin/CandidatesListForAdmin"; // Thymeleaf template for displaying the list
    }

    @Override

    public String listCandidatesByCNIC(Model model, Long search) {

        model.addAttribute("filterCandidates", commonServiceImp.searchCandidate(search));
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        return "Admin/CandidatesListForAdmin";
    }

    @Override

    public String RegisterConstituency(Model model) {
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());

        return "Admin/ConstituencyRegistration";
    }

    /***
     * loads a user from database with given email
     * @param email: String. User email
     * @return UserEntity
     */
    @Override
    public UsersEntity loadUserByUsername(String email) throws UsernameNotFoundException{
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }


}

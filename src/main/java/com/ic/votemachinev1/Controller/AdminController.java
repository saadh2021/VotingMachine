package com.ic.votemachinev1.Controller;

import com.ic.votemachinev1.DTOs.Admin.ConstituencyDTO;
import com.ic.votemachinev1.DTOs.Candidate.CandidatesOrPartyDTO;
import com.ic.votemachinev1.DTOs.DateTimeDTO.VotingTimeDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTOToFetch;
import com.ic.votemachinev1.Model.PartiesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Service.Imp.AdminServiceImp;
import com.ic.votemachinev1.Service.Imp.PartiesServiceImp;
import com.ic.votemachinev1.Service.Imp.VotingTimeServiceImp;
import com.ic.votemachinev1.Utils.CommonService;
import com.ic.votemachinev1.Utils.SessionData;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")

public class AdminController {


    private final AdminServiceImp adminServiceImp;

    private final VotingTimeServiceImp votingTimeServiceImp;

    private final ModelMapper modelMapper;

    private final PartiesServiceImp partiesServiceImp;
    private final CommonService commonService;


    @RequestMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    @GetMapping("/approve/{cnic}")
    public String Approve(@PathVariable Long cnic) {
        adminServiceImp.ApproveCandidate(cnic);
        return "Admin/ApprovedCandidateSuccessForAdmin";
    }

    @GetMapping("/dashboard")
    public ModelAndView ShowDashBoard(Model model) {

        ModelAndView modelAndView = new ModelAndView("Admin/AdminDashboard");

        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("profilePicture", commonService.ShowProfilePic());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return modelAndView;


    }

    @GetMapping("/ElectionResults")
    public String ElectionResults(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        Page<PartiesEntity> parties = commonService.getSortedParties(page, size);
        model.addAttribute("Parties", parties);
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Admin/ElectionResults";
    }

    @GetMapping(value = "/ElectionResultsByPartyName")
    public String ResultsByPartyNames(Model model, @RequestParam String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PartiesEntity filteredParty = commonService.SearchByPartyName(search); // Fetch the list of voters from the service

        model.addAttribute("filteredParty", filteredParty);
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Admin/ElectionResults"; // Thymeleaf template for displaying the list

    }

    @GetMapping(value = "/Voters")
    public String listVoters(Model model) {
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        List<VoterDTOToFetch> voters = commonService.ListAllVoters(); // Fetch the list of voters from the service

        model.addAttribute("voters", voters);
        return "Admin/VoterList"; // Thymeleaf template for displaying the list
    }

    @GetMapping(value = "/CastVote")
    public String ListAllApprovedCandidates(Model model) {
        model.addAttribute("approvedCandidate", commonService.ListAllApprovedCandidate());
        model.addAttribute("SessionData", commonService.getSessionData());
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Admin/CastVoteDashBoard"; // Thymeleaf template for displaying the list
    }

    @PostMapping("/CastVote")
    public String SaveCastedVote(@RequestParam("PartyID") Long partyID,Model model) {
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        commonService.SaveCastedVote(partyID);
        return "Admin/VoteCastedSuccess";
    }


    @GetMapping(value = "/VotersByCNIC")
    public String VoterByCNIC(Model model, @RequestParam Long search) {
        model.addAttribute("filteredVoter", commonService.searchVoter(search));
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Admin/VoterList"; // Thymeleaf template for displaying the list

    }

    @GetMapping(value = "/InviteVoters")
    public String InviteVoterToCandidate(Model model) {

        List<VoterDTOToFetch> voters = commonService.ListAllVoters(); // Fetch the list of voters from the service
        model.addAttribute("voters", voters);
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());

        return "Admin/VoterListWithInvitation";
    }

    @PostMapping("/SendInvite")
    public String SendInvite(@RequestParam("selectedVoterId") Long selectedVoterId,Model model) {

        adminServiceImp.SendVoterCandidateInvite(selectedVoterId);
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "/Admin/InviteSendForVoterToCandidate";


    }


    @GetMapping(value = "/Candidates")

    public String listCandidates(Model model) {

        model.addAttribute("Candidates", commonService.ListAllPartiesOrCandidates());
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Admin/CandidatesListForAdmin"; // Thymeleaf template for displaying the list
    }

    @GetMapping(value = "/CandidatesByCnic")
    public String listCandidates(Model model, @RequestParam Long search) {

        model.addAttribute("filterCandidates", commonService.searchCandidate(search));
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Admin/CandidatesListForAdmin";
    }

    @GetMapping("/constituentyregistration")
    public String RegisterConstituency(Model model) {
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());

        return "Admin/ConstituencyRegisteration";
    }

    @PostMapping("/SaveConstituency")
    public String SaveConstituency(@ModelAttribute ConstituencyDTO constituencyName) {
        adminServiceImp.SaveConstituency(constituencyName);
        return "Admin/AdminDashboard";
    }

    @GetMapping("/ScheduleElections")
    public String ScheduleElection(Model model) {
        VotingTimeDTO votingTimeDTO = new VotingTimeDTO();
        model.addAttribute("VotingSchedule", votingTimeDTO);
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Admin/ScheduleElection";

    }

    @PostMapping("/SaveScheduleElections")
    public String ScheduleElection(@ModelAttribute VotingTimeDTO VotingTimeDTO,Model model) {

        votingTimeServiceImp.ScheduleElections(VotingTimeDTO);
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());


        return "Admin/ElectionScheduledSuccess";
    }

    @GetMapping("/Logout")
    public String Logout()
    {
        commonService.Logout();
        return "redirect:/login";
    }

}

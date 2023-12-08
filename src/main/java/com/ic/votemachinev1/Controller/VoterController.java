package com.ic.votemachinev1.Controller;

import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.PartiesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Model.VotingTimeEntity;
import com.ic.votemachinev1.Service.Imp.VoterServiceImp;
import com.ic.votemachinev1.Utils.CommonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/voter")
public class VoterController {

    public final ModelMapper modelMapper;


    public final VoterServiceImp voterServiceImp;

    public final CommonService commonService;


    @RequestMapping("/dashboard")
    public ModelAndView ShowDashBoard(Model model) {

        ModelAndView modelAndView = new ModelAndView("Voter/VoterDashboard");

        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("profilePicture", commonService.ShowProfilePic());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return modelAndView;

    }

    @GetMapping(value = "/Candidates")

    public String listCandidates(Model model) {

        model.addAttribute("Candidates", commonService.ListAllPartiesOrCandidates());
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Voter/CandidatesList"; // Thymeleaf template for displaying the list
    }

    @GetMapping(value = "/CandidatesByCnic")
    public String listCandidates(Model model, @RequestParam Long search) {

        model.addAttribute("filterCandidates", commonService.searchCandidate(search));
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Voter/CandidatesList";
    }



    @GetMapping("/ApplyForCandidate")

    public String ApplyForCandidate(Model model) {

        AllCandidateOrPartyDTO voterToBeCandidate = voterServiceImp.ApplyToBeCandidate(); //TEMP Change it with actual user cnic

        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        model.addAttribute("voterToBeCandidate", voterToBeCandidate);
        model.addAttribute("listAllConstituencies", listOfALLConstituencies());

        return "Voter/VoterToCandidate";
    }

    public List<ConstituenciesEntity> listOfALLConstituencies() {
        List<ConstituenciesEntity> allConstituencies = voterServiceImp.listAllConstituencies();
        return allConstituencies;
    }

    @PostMapping("/SaveCandidateApplication")

    public String UpdateVoterToBeCandidate(@ModelAttribute AllCandidateOrPartyDTO voterToCandidateDto) {


        return "redirect:/voter/SendApprovalRequest/" + voterServiceImp.UpdateVoterToBeCandidate(voterToCandidateDto);

    }

    @GetMapping("/SendApprovalRequest/{cnic}")

    public String SendApprovalRequest(@PathVariable Long cnic) {
        voterServiceImp.approveCandidate(cnic);
        return "ApprovedCandidateSuccessForVoter";
    }

    @GetMapping(value = "/CastVote")
    public String ListAllApprovedCandidates(Model model) {
        model.addAttribute("approvedCandidate", commonService.ListAllApprovedCandidate());
        model.addAttribute("SessionData", commonService.getSessionData());
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Voter/CastVoteDashBoard"; // Thymeleaf template for displaying the list
    }

    @PostMapping("/CastVote")
    public String SaveCastedVote(@RequestParam("PartyID") Long partyID,Model model) {
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        commonService.SaveCastedVote(partyID);
        return "Voter/VoteCastedSuccess";
    }

    @GetMapping("/ElectionResults")
    public String ElectionResults(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        Page<PartiesEntity> parties = commonService.getSortedParties(page, size);
        model.addAttribute("Parties", parties);
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Voter/ElectionResults";
    }

    @GetMapping(value = "/ElectionResultsByPartyName")
    public String ResultsByPartyNames(Model model, @RequestParam String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PartiesEntity filteredParty = commonService.SearchByPartyName(search); // Fetch the list of voters from the service

        model.addAttribute("filteredParty", filteredParty);
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Voter/ElectionResults"; // Thymeleaf template for displaying the list

    }


    public List<ConstituenciesEntity> GetAvaialbleAddress() {
        List<ConstituenciesEntity> addressList = voterServiceImp.ListOfConstituencies();
        return addressList;
    }

    @GetMapping("/Logout")
    public String Logout()
    {
        commonService.Logout();
        return "redirect:/login";
    }
}

package com.ic.votemachinev1.Controller;

import com.cloudinary.Cloudinary;
import com.ic.votemachinev1.DTOs.Candidate.CandidatesOrPartyDTO;
import com.ic.votemachinev1.DTOs.DateTimeDTO.VotingTimeDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTOToFetch;
import com.ic.votemachinev1.Model.PartiesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Model.VotingTimeEntity;
import com.ic.votemachinev1.Repository.VotingTimeRepository;
import com.ic.votemachinev1.Service.Imp.CandidateServiceImp;
import com.ic.votemachinev1.Utils.CommonService;
import com.ic.votemachinev1.Utils.SessionData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/candidate")
public class CandidateController {

    private final SessionData sessionData;

    private final CandidateServiceImp candidateServiceImp;
    private final CommonService commonService;


    @GetMapping("/dashboard")
    public ModelAndView ShowDashBoard(Model model) {
        ModelAndView modelAndView = new ModelAndView("Candidate/candidatedashboard");

        UsersEntity userDetails = candidateServiceImp.GetLoggedInUserDetails();

        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("profilePicture", commonService.ShowProfilePic());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return modelAndView;

    }

    @GetMapping(value = "/Voters")
    public String listVoters(Model model) {
        List<VoterDTOToFetch> voters = commonService.ListAllVoters(); // Fetch the list of voters from the service
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        //  model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        model.addAttribute("voters", voters);
        return "Candidate/VoterList"; // Thymeleaf template for displaying the list
    }

    @GetMapping(value = "/VotersByCNIC")
    public String VoterByCNIC(Model model, @RequestParam Long search) {
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        model.addAttribute("filteredVoter", commonService.searchVoter(search));
        return "Candidate/VoterList"; // Thymeleaf template for displaying the list

    }

    @GetMapping(value = "/CastVote")
    public String ListAllApprovedCandidates(Model model) {
        List<CandidatesOrPartyDTO> approvedCandidate = commonService.ListAllApprovedCandidate();
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        model.addAttribute("approvedCandidate", approvedCandidate);
        /*  model.addAttribute("SessionData", commonService.getSessionData());*/
        return "Candidate/CastVoteDashBoard"; // Thymeleaf template for displaying the list
    }

    @PostMapping("/CastVote")
    public String SaveCastedVote(@RequestParam("PartyID") Long partyID, Model model) {
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        commonService.SaveCastedVote(partyID);
        return "Candidate/VoteCastedSuccess";
    }

    @GetMapping("/ElectionResults")
    public String ElectionResults(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        Page<PartiesEntity> parties = commonService.getSortedParties(page, size);
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        model.addAttribute("Parties", parties);
        return "Candidate/ElectionResults";
    }

    @GetMapping(value = "/ElectionResultsByPartyName")
    public String ResultsByPartyNames(Model model, @RequestParam String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PartiesEntity filteredParty = commonService.SearchByPartyName(search); // Fetch the list of voters from the service
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        model.addAttribute("filteredParty", filteredParty);
        return "Candidate/ElectionResults"; // Thymeleaf template for displaying the list

    }

    @GetMapping("/Logout")
    public String Logout()
    {
        commonService.Logout();
        return "redirect:/login";
    }


}

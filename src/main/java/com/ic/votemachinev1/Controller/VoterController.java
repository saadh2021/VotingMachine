package com.ic.votemachinev1.Controller;

import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.Service.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Voter")
public class VoterController {


    public final VoterService voterService;


    @RequestMapping("/Dashboard")
    public ModelAndView ShowDashBoard(Model model) {

        return voterService.ShowDashBoard(model);

    }

    @GetMapping(value = "/Candidates")

    public String listCandidates(Model model) {

        return voterService.listCandidates(model);
    }


    @GetMapping(value = "/CandidatesByCnic")
    public String listCandidates(Model model, @RequestParam Long search) {

        return voterService.listCandidates(model, search);
    }

    @GetMapping("/ApplyForCandidate")

    public String ApplyForCandidate(Model model) {

        voterService.ApplyToBeCandidate(model);
        return "Voter/VoterToCandidate";

    }


    @PostMapping("/SaveCandidateApplication")

    public String UpdateVoterToBeCandidate(@ModelAttribute AllCandidateOrPartyDTO voterToCandidateDto) {

      //  System.out.println("The is is " + voterToCandidateDto.toString());
        return "redirect:/Voter/SendApprovalRequest/" + voterService.UpdateVoterToBeCandidate(voterToCandidateDto);

    }

    @GetMapping("/SendApprovalRequest/{cnic}")

    public String SendApprovalRequest(@PathVariable Long cnic) {
        voterService.approveCandidate(cnic);
        return "/Voter/ApprovedCandidateSuccessForVoter";
    }

    @GetMapping(value = "/CastVote")
    public String ListAllApprovedCandidates(Model model) {
        return voterService.ListAllApprovedCandidates(model);
    }

    @PostMapping("/CastVote")
    public String SaveCastedVote(@RequestParam("PartyID") Long partyID, Model model) {
        return voterService.SaveCastedVote(partyID, model);
    }

    @GetMapping("/ElectionResults")
    public String ElectionResults(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        return voterService.ElectionResults(page, size, model);
    }

    @GetMapping(value = "/ElectionResultsByPartyName")
    public String ResultsByPartyNames(Model model, @RequestParam String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return voterService.ResultsByPartyNames(model, search, page, size);

    }


    @GetMapping("/Logout")
    public String Logout() {
        return voterService.Logout();
    }
}

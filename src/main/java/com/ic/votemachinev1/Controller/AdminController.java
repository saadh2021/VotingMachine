package com.ic.votemachinev1.Controller;

import com.ic.votemachinev1.DTOs.Admin.ConstituencyDTO;
import com.ic.votemachinev1.DTOs.DateTimeDTO.VotingTimeDTO;
import com.ic.votemachinev1.Service.AdminService;
import com.ic.votemachinev1.Service.Imp.CommonServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Admin")

public class AdminController {


    private final AdminService adminService;

    //Controller For Login
    @RequestMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    // Controller For Approve Candidate
    @GetMapping("/approve/{cnic}")
    public String Approve(Model model, @PathVariable Long cnic) {
        adminService.ApproveCandidate(model, cnic);
        return "Admin/ApprovedCandidateSuccessForAdmin";
    }

// Controller For Dashboard

    @GetMapping("/Dashboard")
    public ModelAndView ShowDashBoard(Model model) {
        return adminService.ShowDashBoard(model);
    }

    // Controller For Election Results
    @GetMapping("/ElectionResults")
    public String ElectionResults(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {

        return adminService.ElectionResults(page, size, model);
    }

    // Controller For Election Results by Name

    @GetMapping(value = "/ElectionResultsByPartyName")
    public String ResultsByPartyNames(Model model, @RequestParam String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        return adminService.ResultsByPartyNames(model, search, page, size);

    }
// Controller For All Voters

    @GetMapping(value = "/Voters")
    public String listVoters(Model model) {

        return adminService.listVoters(model);
    }
// Controller For Cast Vote

    @GetMapping(value = "/CastVote")
    public String ListAllApprovedCandidates(Model model) {
        return adminService.ListAllApprovedCandidates(model);
    }

// Controller For Save Vote

    @PostMapping("/CastVote")
    public String SaveCastedVote(@RequestParam("PartyID") Long partyID, Model model) {
        return adminService.SaveCastedVote(partyID, model);
    }

    // Controller For Search Voter By CNIC
    @GetMapping(value = "/VotersByCNIC")
    public String VoterByCNIC(Model model, @RequestParam Long search) {
        return adminService.VoterByCNIC(model, search);

    }
// Controller For Invite

    @GetMapping(value = "/InviteVoters")
    public String InviteVoterToCandidate(Model model) {


        return adminService.InviteVoterToCandidate(model);
    }

    @PostMapping("/SendInvite")
    public String SendInvite(@RequestParam("selectedVoterId") Long selectedVoterId, Model model) {

        return adminService.SendVoterCandidateInvite(model, selectedVoterId);

    }


    @GetMapping(value = "/Candidates")

    public String listCandidates(Model model) {

        return adminService.listCandidates(model);
    }

    @GetMapping(value = "/CandidatesByCnic")
    public String listCandidates(Model model, @RequestParam Long search) {
        return adminService.listCandidatesByCNIC(model, search);

    }

    @GetMapping("/ConstituencyRegistration")
    public String RegisterConstituency(Model model) {
        return adminService.RegisterConstituency(model);
    }

    @PostMapping("/SaveConstituency")
    public String SaveConstituency(@ModelAttribute ConstituencyDTO constituencyName, Model model) {
        return adminService.SaveConstituency(constituencyName, model);

    }

    @GetMapping("/ScheduleElections")
    public String ScheduleElection(Model model) {
        return adminService.ScheduleElection(model);

    }

    @PostMapping("/SaveScheduleElections")
    public String ScheduleElection(@ModelAttribute VotingTimeDTO votingTimeDTO, Model model) {
        return adminService.ScheduleElections(votingTimeDTO, model);

    }

    @GetMapping("/Logout")
    public String Logout() {
        return adminService.Logout();
    }

}

package com.ic.votemachinev1.Controller;

import com.ic.votemachinev1.Service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Candidate")
public class CandidateController {


    private final CandidateService candidateService;

    // Dashboard Controller
    @GetMapping("/Dashboard")
    public ModelAndView ShowDashBoard(Model model) {
        return candidateService.ShowDashBoard(model);

    }

    @GetMapping(value = "/Voters")
    public String listVoters(Model model) {
        return candidateService.listVoters(model);
    }

    // Search Voter By CNIC
    @GetMapping(value = "/VotersByCNIC")
    public String VoterByCNIC(Model model, @RequestParam Long search) {
        return candidateService.VoterByCNIC(model, search);

    }

    // Cast Vote Controller
    @GetMapping(value = "/CastVote")
    public String ListAllApprovedCandidates(Model model) {
        return candidateService.ListAllApprovedCandidates(model);
    }

    // Save Vote Controller
    @PostMapping("/CastVote")
    public String SaveCastedVote(@RequestParam("PartyID") Long partyID, Model model) {
        return candidateService.SaveCastedVote(partyID, model);
    }


    // Election Results Controller
    @GetMapping("/ElectionResults")
    public String ElectionResults(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        return candidateService.ElectionResults(page, size, model);
    }

    @GetMapping(value = "/ElectionResultsByPartyName")
    public String ResultsByPartyNames(Model model, @RequestParam String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return candidateService.ResultsByPartyNames(model, search, page, size);

    }


    // Election Results Controller
    @GetMapping("/Logout")
    public String Logout() {
        return candidateService.Logout();
    }


}

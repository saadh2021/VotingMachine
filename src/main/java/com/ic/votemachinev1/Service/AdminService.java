package com.ic.votemachinev1.Service;

import com.ic.votemachinev1.DTOs.Admin.ConstituencyDTO;
import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.DTOs.Candidate.CandidatesOrPartyDTO;
import com.ic.votemachinev1.DTOs.DateTimeDTO.VotingTimeDTO;
import com.ic.votemachinev1.DTOs.Party.ElectionResultsDTO;
import com.ic.votemachinev1.DTOs.Party.PartyDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.UsersEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface AdminService {


    public String SaveConstituency(ConstituencyDTO constituencyName,Model model);

    public String ScheduleElection(Model model);

    public void ApproveCandidate(Model model,Long cnic);

    public String SendVoterCandidateInvite(Model model, Long cnic);

    public String listCandidates(Model model);

    public String listCandidatesByCNIC(Model model, Long search);

    public String RegisterConstituency(Model model);


    public String ScheduleElections(VotingTimeDTO votingTimeDTO, Model model);

    public ModelAndView ShowDashBoard(Model model);

    public String ElectionResults(int page, int size, Model model);

    public String ResultsByPartyNames(Model model, String search, int page, int size);

    public String listVoters(Model model);

    public String ListAllApprovedCandidates(Model model);


    public String SaveCastedVote(Long partyID, Model model);

    public String VoterByCNIC(Model model, Long search);

    public String InviteVoterToCandidate(Model model);

    public String Logout();

}

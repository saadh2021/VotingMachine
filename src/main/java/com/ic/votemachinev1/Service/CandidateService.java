package com.ic.votemachinev1.Service;


import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.DTOs.Party.ElectionResultsDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.UsersEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

public interface CandidateService {
    AllCandidateOrPartyDTO CandidateViewProfile(Long id);

    public String listVoters(Model model);

    public ModelAndView ShowDashBoard(Model model);

    public String ListAllApprovedCandidates(Model model);

    public String SaveCastedVote(Long partyID, Model model);

    public String ElectionResults(int page, int size, Model model);

    public String ResultsByPartyNames(Model model, String search, int page, int size);

    public String Logout();

    public String VoterByCNIC(Model model, Long search);

    AllCandidateOrPartyDTO CandidateUpdateProfile(AllCandidateOrPartyDTO candidateDTO);

    ResponseEntity<String> CandidateCastVote(Long CNIC, Long PartyID);

    ResponseEntity<List<ElectionResultsDTO>> AllPartiesElectionResults();

    ResponseEntity<VoterDTO> ListOfAllVotersInCandidatesConstituency(Long ConstituencyID);

    ResponseEntity<LocalDateTime> ScheduleOfElectionsInCandidateConstit(Long ConstituencyID);

    String ShowProfilePic();

    UsersEntity GetLoggedInUserDetails();


}

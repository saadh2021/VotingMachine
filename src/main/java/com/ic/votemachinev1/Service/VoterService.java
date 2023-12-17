package com.ic.votemachinev1.Service;

import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.DTOs.Common.CastVoteDTO;
import com.ic.votemachinev1.DTOs.Party.ElectionResultsDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface VoterService {
    ResponseEntity<CastVoteDTO> VoterCastVote(Long CNIC, Long PartyID);

    ResponseEntity<ElectionResultsDTO> ElectionResultsInVoterConstituency(Integer VoterID);

    ResponseEntity<VoterDTO> RegisterVoter(VoterDTO voterDTO);


    ResponseEntity<VoterDTO> ViewVoterProfile(Long CNIC);

    ResponseEntity<VoterDTO> UpdateVoterProfile(VoterDTO voterDTO);

    List<ConstituenciesEntity> listAllConstituencies();

    String listCandidates(Model model);

    String listCandidates(Model model, @RequestParam Long search);


    void approveCandidate(Long cnic);

    AllCandidateOrPartyDTO ApplyToBeCandidate(Model model);

    ModelAndView ShowDashBoard(Model model);

    String ListAllApprovedCandidates(Model model);

    String SaveCastedVote(Long partyID, Model model);

    String ElectionResults(int page, int size, Model model);

    public String ResultsByPartyNames(Model model, String search, int page, int size);


    ResponseEntity<List<AllCandidateOrPartyDTO>> ListOfAllCandidateForElectionsInVoterConstit(Long CNIC);

    void AcceptAdminInvitataionToBeACandidate();

    String Logout();

    String ShowProfilePic();

    UsersEntity GetLoggedInUserDetails();

    byte[] displayProfilePicture(String publicID);

    List<ConstituenciesEntity> ListOfConstituencies();

    Long UpdateVoterToBeCandidate(AllCandidateOrPartyDTO voterToCandidateDto);



}

package com.ic.votemachinev1.Service;

import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.DTOs.Common.CastVoteDTO;
import com.ic.votemachinev1.DTOs.Party.ElectionResultsDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VoterService {
    ResponseEntity<CastVoteDTO> VoterCastVote(Long CNIC, Long PartyID);

    ResponseEntity<ElectionResultsDTO> ElectionResultsInVoterConstituency(Integer VoterID);

    ResponseEntity<VoterDTO> RegisterVoter(VoterDTO voterDTO);


    ResponseEntity<VoterDTO> ViewVoterProfile(Long CNIC);

    ResponseEntity<VoterDTO> UpdateVoterProfile(VoterDTO voterDTO);

    AllCandidateOrPartyDTO ApplyToBeCandidate();


    ResponseEntity<List<AllCandidateOrPartyDTO>> ListOfAllCandidateForElectionsInVoterConstit(Long CNIC);

    void AcceptAdminInvitataionToBeACandidate();

    String ShowProfilePic();

    UsersEntity GetLoggedInUserDetails();

    byte[] displayProfilePicture(String publicID);
    List<ConstituenciesEntity> ListOfConstituencies();

    Long UpdateVoterToBeCandidate(AllCandidateOrPartyDTO voterToCandidateDto);


}

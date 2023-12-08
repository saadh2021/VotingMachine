package com.ic.votemachinev1.Service;


import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.DTOs.Party.ElectionResultsDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.UsersEntity;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface CandidateService {
    AllCandidateOrPartyDTO CandidateViewProfile(Long id);

    AllCandidateOrPartyDTO CandidateUpdateProfile(AllCandidateOrPartyDTO candidateDTO);

    ResponseEntity<String> CandidateCastVote(Long CNIC, Long PartyID);

    ResponseEntity<List<ElectionResultsDTO>> AllPartiesElectionResults();

    ResponseEntity<VoterDTO> ListOfAllVotersInCandidatesConstituency(Long ConstituencyID);

    ResponseEntity<LocalDateTime> ScheduleOfElectionsInCandidateConstit(Long ConstituencyID);
     String ShowProfilePic();

     UsersEntity GetLoggedInUserDetails();


}

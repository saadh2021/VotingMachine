package com.ic.votemachinev1.Service;

import com.ic.votemachinev1.DTOs.Candidate.CandidatesOrPartyDTO;
import com.ic.votemachinev1.DTOs.DateTimeDTO.VotingTimeDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTOToFetch;
import com.ic.votemachinev1.Model.PartiesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Utils.SessionData;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommonService {

    public byte[] GetPictureDataInBytes(String publicID);

    public List<VoterDTOToFetch> ListAllVoters();

    public CandidatesOrPartyDTO searchCandidate(Long cnic);

    public SessionData getSessionData();

    public VoterDTOToFetch searchVoter(Long cnic);

    public VotingTimeDTO votingTimeEntity();

    public String ShowProfilePic();

    public PartiesEntity SearchByPartyName(String name);

    public UsersEntity GetLoggedInUserDetails();

    public List<CandidatesOrPartyDTO> ListAllPartiesOrCandidates();

    public Page<PartiesEntity> getSortedParties(int page, int size);

    public List<CandidatesOrPartyDTO> ListAllApprovedCandidate();

    public CandidatesOrPartyDTO convertCandidateEntityToDTO(UsersEntity entity);

    public void SaveCastedVote(Long partyID);

    public CandidatesOrPartyDTO ConvertVoterEntityToCandidatesDTO(UsersEntity entity);

    public VoterDTOToFetch convertVoterEntityToDTO(UsersEntity entity);

    public void Logout();
}

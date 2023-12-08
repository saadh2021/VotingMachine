
package com.ic.votemachinev1.Utils;

import com.cloudinary.Cloudinary;
import com.ic.votemachinev1.DTOs.Candidate.CandidatesOrPartyDTO;
import com.ic.votemachinev1.DTOs.DateTimeDTO.VotingTimeDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTOToFetch;
import com.ic.votemachinev1.Model.PartiesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Model.VotingTimeEntity;
import com.ic.votemachinev1.Repository.PartiesRepository;
import com.ic.votemachinev1.Repository.UsersRepository;
import com.ic.votemachinev1.Repository.VotingTimeRepository;
import com.ic.votemachinev1.Service.Imp.PartiesServiceImp;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ic.votemachinev1.Model.UserRolesEntity.Candidate;
import static com.ic.votemachinev1.Model.UserRolesEntity.Voter;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final Cloudinary cloudinary;
    private final SessionData sessionData;
    private final ModelMapper modelMapper;
    private final UsersRepository usersRepository;
    private final VotingTimeRepository votingTimeRepository;
    private final PartiesRepository partiesRepository;
    private final PartiesServiceImp partiesServiceImp;

    public List<VoterDTOToFetch> ListAllVoters() {
        List<UsersEntity> Voters = usersRepository.findByRole(Voter);
        List<VoterDTOToFetch> voterDTOList = Voters.stream().map(usersEntity -> convertVoterEntityToDTO(usersEntity)).collect(Collectors.toList());
        return voterDTOList;
    }

    public byte[] GetPictureDataInBytes(String publicID) {


        String cloudUrl = cloudinary.url().publicId(publicID).generate();
        try {
            // Get a ByteArrayResource from the URL
            URL url = new URL(cloudUrl);
            InputStream inputStream = url.openStream();
            byte[] out = org.apache.commons.io.IOUtils.toByteArray(inputStream);
            ByteArrayResource resource = new ByteArrayResource(out);
            return resource.getByteArray();

        } catch (Exception ex) {

            return null;
        }

    }

    public CandidatesOrPartyDTO searchCandidate(Long cnic) {
        UsersEntity filterCandidate = usersRepository.findByCnic(cnic);
        CandidatesOrPartyDTO filterCandidateDTO = modelMapper.map(filterCandidate, CandidatesOrPartyDTO.class);
        return filterCandidateDTO;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public VoterDTOToFetch searchVoter(Long cnic) {
        UsersEntity filterCandidate = usersRepository.findByCnic(cnic);
        VoterDTOToFetch VoterDTOToFetch = convertVoterEntityToDTO(filterCandidate);
        return VoterDTOToFetch;
    }

    public VotingTimeDTO votingTimeEntity() {

        Optional<VotingTimeEntity> votingTimeEntity = votingTimeRepository.findById(1L);
        VotingTimeDTO votingTimeDTO = modelMapper.map(votingTimeEntity, VotingTimeDTO.class);
        return votingTimeDTO;

    }


    public String ShowProfilePic() {
        UsersEntity userDetails = sessionData.getUsersEntity();

        byte[] imageData = GetPictureDataInBytes(userDetails.getUserImg());
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        return base64Image;
    }

    public PartiesEntity SearchByPartyName(String name) {
        PartiesEntity partiesEntity = partiesServiceImp.SearchParty(name);
        return partiesEntity;
    }

    public UsersEntity GetLoggedInUserDetails() {
        UsersEntity userDetails = sessionData.getUsersEntity();
        return userDetails;
    }

    public List<CandidatesOrPartyDTO> ListAllPartiesOrCandidates() {
        List<UsersEntity> candidates = usersRepository.findByRole(Candidate);
        List<CandidatesOrPartyDTO> candidateDTO = candidates.stream().map(candidates1 -> ConvertVoterEntityToCandidatesDTO(candidates1)).collect(Collectors.toList());
        return candidateDTO;
    }

    public Page<PartiesEntity> getSortedParties(int page, int size) {
        Page<PartiesEntity> parties = partiesServiceImp.getSortedParties(page, size);
        return parties;
    }


    public List<CandidatesOrPartyDTO> ListAllApprovedCandidate() {
        String currentUserResidentialConstituency = String.valueOf(sessionData.getUsersEntity().getResidentialConstituency().getConstituencyName());
        System.out.println("Admin current residential constituency is: " + currentUserResidentialConstituency);
        List<UsersEntity> approvedCandidate = usersRepository.findByApprovedCandidateAndElectionConstituency(true, currentUserResidentialConstituency);

        List<CandidatesOrPartyDTO> approvedCandidateDTOList = approvedCandidate.stream().map(candidate -> convertCandidateEntityToDTO(candidate)).collect(Collectors.toList());
        return approvedCandidateDTOList;
    }

    public CandidatesOrPartyDTO convertCandidateEntityToDTO(UsersEntity entity) {
        CandidatesOrPartyDTO candidateDTO = modelMapper.map(entity, CandidatesOrPartyDTO.class);
        return candidateDTO;
    }

    public void SaveCastedVote(Long partyID) {
        Optional<PartiesEntity> optionalPartiesEntity = partiesRepository.findById(partyID);

        PartiesEntity addVoteToParty = optionalPartiesEntity.get();
        addVoteToParty.setEarnedVotes(addVoteToParty.getEarnedVotes() + 1);
        partiesRepository.save(addVoteToParty);
        UsersEntity currentUser = sessionData.getUsersEntity();
        currentUser.setVote_Casted(true);
        currentUser.setVotedForWhichParty(addVoteToParty.getPartyName());
        usersRepository.save(currentUser);

    }

    public CandidatesOrPartyDTO ConvertVoterEntityToCandidatesDTO(UsersEntity entity) {
        CandidatesOrPartyDTO candidatesOrPartyDTO = modelMapper.map(entity, CandidatesOrPartyDTO.class);
        return candidatesOrPartyDTO;
    }


    public VoterDTOToFetch convertVoterEntityToDTO(UsersEntity entity) {
        VoterDTOToFetch voterDTO = new VoterDTOToFetch();
       voterDTO.Id = entity.getId();
       voterDTO.setName(entity.getName());
       voterDTO.setCnic(entity.getCnic());
       voterDTO.setPassword(entity.getPassword());
       voterDTO.setEmail(entity.getUsername());
       voterDTO.setResidentialConstituency(entity.getResidentialConstituency());
        return voterDTO;
    }

    public void Logout()
    {
        getSessionData().setToken(null);
        getSessionData().setUsersEntity(null);
    }
}


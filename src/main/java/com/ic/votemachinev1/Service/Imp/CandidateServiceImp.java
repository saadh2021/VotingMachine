package com.ic.votemachinev1.Service.Imp;

import com.cloudinary.Cloudinary;
import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.DTOs.Party.ElectionResultsDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Model.VotingTimeEntity;
import com.ic.votemachinev1.Repository.VotingTimeRepository;
import com.ic.votemachinev1.Service.CandidateService;
import com.ic.votemachinev1.Utils.SessionData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CandidateServiceImp implements CandidateService {

    private final Cloudinary cloudinary;

    private final SessionData sessionData;

    private final VotingTimeRepository votingTimeRepository;

    @Override
    public AllCandidateOrPartyDTO CandidateViewProfile(Long id) {
        return null;
    }

    @Override
    public AllCandidateOrPartyDTO CandidateUpdateProfile(AllCandidateOrPartyDTO candidateDTO) {
        return null;
    }

    @Override
    public ResponseEntity<String> CandidateCastVote(Long CNIC, Long PartyID) {
        return null;
    }

    @Override
    public ResponseEntity<List<ElectionResultsDTO>> AllPartiesElectionResults() {
        return null;
    }

    @Override
    public ResponseEntity<VoterDTO> ListOfAllVotersInCandidatesConstituency(Long ConstituencyID) {
        return null;
    }

    @Override
    public ResponseEntity<LocalDateTime> ScheduleOfElectionsInCandidateConstit(Long ConstituencyID) {
        return null;
    }

    @Override
    public String ShowProfilePic() {
        UsersEntity userDetails = sessionData.getUsersEntity();

        byte[] imageData = displayProfilePicture(userDetails.getUserImg());
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        return base64Image;
    }

    @Override
    public UsersEntity GetLoggedInUserDetails() {
        UsersEntity userDetails = sessionData.getUsersEntity();
        return userDetails;
    }

    public Optional<VotingTimeEntity> votingTimeEntity() {
        Optional<VotingTimeEntity> votingTimeEntity = votingTimeRepository.findById(1L);
        return votingTimeEntity;

    }

    public byte[] displayProfilePicture(String publicID) {


        String cloudUrl = cloudinary.url()
                .publicId(publicID)
                .generate();
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

}

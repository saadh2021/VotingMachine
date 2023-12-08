package com.ic.votemachinev1.Service.Imp;

import com.cloudinary.Cloudinary;
import com.ic.votemachinev1.DTOs.Admin.ConstituencyDTO;
import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.DTOs.Common.CastVoteDTO;
import com.ic.votemachinev1.DTOs.Party.ElectionResultsDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.PartiesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Model.VotingTimeEntity;
import com.ic.votemachinev1.Repository.ConstituencyRepository;
import com.ic.votemachinev1.Repository.PartiesRepository;
import com.ic.votemachinev1.Repository.UsersRepository;
import com.ic.votemachinev1.Repository.VotingTimeRepository;
import com.ic.votemachinev1.Service.VoterService;
import com.ic.votemachinev1.Utils.SessionData;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class VoterServiceImp implements VoterService, UserDetailsService {

    private final UsersRepository usersRepository;

    private final ModelMapper modelMapper;

    private final EmailService emailService;

    private final PartiesRepository partiesRepository;

    private final ConstituencyRepository constituencyRepository;

    private final SessionData sessionData;

    private final Cloudinary cloudinary;

    private final VotingTimeRepository votingTimeRepository;


    public void approveCandidate(Long cnic) {

        UsersEntity voter = usersRepository.findByCnic(cnic);
        AllCandidateOrPartyDTO candidateOrPartyDTO = modelMapper.map(voter, AllCandidateOrPartyDTO.class);
        // Send email to admin
        String adminEmail = "saad.intern@devsinc.com";
        String subject = "Candidate Approval Request";
        String message = "A new voter has applied for Candidate approval. Please review and approve." + candidateOrPartyDTO.toString() + "Please Click on the link below to approve" + "     " + "http://localhost:8080/admin/approve/" + cnic;
        ;
        emailService.sendApprovalEmail(adminEmail, subject, message);
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

    @Override
    public ResponseEntity<CastVoteDTO> VoterCastVote(Long CNIC, Long PartyID) {
        return null;
    }

    @Override
    public ResponseEntity<ElectionResultsDTO> ElectionResultsInVoterConstituency(Integer VoterID) {
        return null;
    }

    @Override
    public ResponseEntity<VoterDTO> RegisterVoter(VoterDTO voterDTO) {
        return null;
    }

    @Override
    public ResponseEntity<VoterDTO> ViewVoterProfile(Long CNIC) {
        return null;
    }

    @Override
    public ResponseEntity<VoterDTO> UpdateVoterProfile(VoterDTO voterDTO) {
        return null;
    }

    @Override
    public List<ConstituenciesEntity> ListOfConstituencies() {
        List<ConstituenciesEntity> ListOfConstituencies = constituencyRepository.findAll();
        return ListOfConstituencies;
    }

    public Optional<VotingTimeEntity> votingTimeEntity() {
        Optional<VotingTimeEntity> votingTimeEntity = votingTimeRepository.findById(1L);
        return votingTimeEntity;

    }

    @Override
    public AllCandidateOrPartyDTO ApplyToBeCandidate() {

        //TEMP//
        UsersEntity user = usersRepository.findByCnic(sessionData.getUsersEntity().getCnic());

        AllCandidateOrPartyDTO voterToBeCandidate = modelMapper.map(user, AllCandidateOrPartyDTO.class);
        return voterToBeCandidate;


    }

    @Override
    public Long UpdateVoterToBeCandidate(AllCandidateOrPartyDTO voterToCandidateDto) {
        System.out.println("The Enter User name is: " + voterToCandidateDto.name + "  " + "The Enter User  is: " + voterToCandidateDto.toString());
        UsersEntity NewCandidate = modelMapper.map(voterToCandidateDto, UsersEntity.class);
        PartiesEntity newParty = NewCandidate.getParty();
        if (newParty != null) {
            PartiesEntity party1 = newParty;
            partiesRepository.save(party1);
            System.out.println("The New party president is :" + party1.getPartyPresident());

        }

        UsersEntity existingVoter = usersRepository.findByCnic(NewCandidate.getCnic());
        existingVoter.setParty(NewCandidate.getParty());
        existingVoter.setElectionConstituency(NewCandidate.getElectionConstituency());
        usersRepository.save(existingVoter);
        Long existingVoterCNIC = existingVoter.getCnic();
        return existingVoterCNIC;
    }

    public List<ConstituenciesEntity> listAllConstituencies() {
        List<ConstituenciesEntity> allConstituencies = constituencyRepository.findAll();

        return allConstituencies;
    }

    ConstituencyDTO ConvertConstituencyEntityToDTO(ConstituenciesEntity constituenciesEntity) {
        ConstituencyDTO constituencyDTO = modelMapper.map(constituenciesEntity, ConstituencyDTO.class);
        return constituencyDTO;
    }

    @Override
    public ResponseEntity<List<AllCandidateOrPartyDTO>> ListOfAllCandidateForElectionsInVoterConstit(Long CNIC) {
        return null;
    }


    @Override
    public void AcceptAdminInvitataionToBeACandidate() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}

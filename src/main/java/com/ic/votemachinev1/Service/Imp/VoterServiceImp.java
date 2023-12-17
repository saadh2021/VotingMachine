package com.ic.votemachinev1.Service.Imp;

import com.cloudinary.Cloudinary;
import com.ic.votemachinev1.DTOs.Admin.ConstituencyDTO;
import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.DTOs.Common.CastVoteDTO;
import com.ic.votemachinev1.DTOs.Common.ResponseDTO;
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
import com.ic.votemachinev1.Service.CommonService;
import com.ic.votemachinev1.Service.VoterService;
import com.ic.votemachinev1.Utils.SessionData;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    private final CommonService commonService;

    @Override
    public void approveCandidate(Long cnic) {

        UsersEntity voter = usersRepository.findByCnic(cnic);
        AllCandidateOrPartyDTO candidateOrPartyDTO = modelMapper.map(voter, AllCandidateOrPartyDTO.class);
        // Send email to admin
        String adminEmail = "saad.intern@devsinc.com";
        String subject = "Candidate Approval Request";
        String message = "A new voter has applied for Candidate approval. Please review and approve." + candidateOrPartyDTO.toString() + "Please Click on the link below to approve" + "     " + "http://localhost:8080/Admin/approve/" + cnic;

        emailService.sendApprovalEmail(adminEmail, subject, message);
    }

    @Override
    public String ListAllApprovedCandidates(Model model) {
        model.addAttribute("approvedCandidate", commonService.ListAllApprovedCandidate());
        model.addAttribute("SessionData", commonService.getSessionData());
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Voter/CastVoteDashBoard"; // Thymeleaf template for displaying the list
    }

    @Override
    public String ElectionResults(int page, int size, Model model) {
        Page<PartiesEntity> parties = commonService.getSortedParties(page, size);
        model.addAttribute("Parties", parties);
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Voter/ElectionResults";
    }

    @Override
    public String ResultsByPartyNames(Model model, String search, int page, int size) {
        PartiesEntity filteredParty = commonService.SearchByPartyName(search); // Fetch the list of voters from the service

        model.addAttribute("filteredParty", filteredParty);
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Voter/ElectionResults"; // Thymeleaf template for displaying the list

    }

    @Override
    public String SaveCastedVote(Long partyID, Model model) {
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        commonService.SaveCastedVote(partyID);
        return "Voter/VoteCastedSuccess";
    }


    @Override
    public String ShowProfilePic() {
        UsersEntity userDetails = sessionData.getUsersEntity();
        byte[] imageData = displayProfilePicture(userDetails.getUserImg());
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        return base64Image;
    }

    @Override
    public String Logout() {
        commonService.Logout();
        return "redirect:/login";
    }

    @Override
    public UsersEntity GetLoggedInUserDetails() {
        UsersEntity userDetails = sessionData.getUsersEntity();
        return userDetails;
    }

    public byte[] displayProfilePicture(String publicID) {


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

    @Override
    public ModelAndView ShowDashBoard(Model model) {

        ModelAndView modelAndView = new ModelAndView("Voter/VoterDashboard");

        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("profilePicture", commonService.ShowProfilePic());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return modelAndView;

    }

    public Optional<VotingTimeEntity> votingTimeEntity() {
        Optional<VotingTimeEntity> votingTimeEntity = votingTimeRepository.findById(1L);
        return votingTimeEntity;

    }

    @Override
    public AllCandidateOrPartyDTO ApplyToBeCandidate(Model model) {

        //TEMP//
        Optional<UsersEntity> optionalUsersEntity = usersRepository.findByEmail(sessionData.getUsersEntity().getEmail());
        try {
            if (optionalUsersEntity.isPresent()) {
                AllCandidateOrPartyDTO voterToBeCandidate = modelMapper.map(optionalUsersEntity, AllCandidateOrPartyDTO.class);
                model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
                model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
                model.addAttribute("voterToBeCandidate", voterToBeCandidate);
                model.addAttribute("listAllConstituencies", listOfALLConstituencies());

                return voterToBeCandidate;
            }
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO(null, null, HttpStatus.BAD_GATEWAY, "User Not Found with this Email", null, true);
            return null;
        }
        ResponseDTO responseDTO = new ResponseDTO(null, null, HttpStatus.BAD_GATEWAY, "User Not Found with this Email", null, true);
        return null;


    }

    public List<ConstituenciesEntity> listOfALLConstituencies() {
        return listAllConstituencies();
    }

    @Override
    public Long UpdateVoterToBeCandidate(AllCandidateOrPartyDTO voterToCandidateDto) {

        System.out.println("The New Voter To BE Candidate is:  " + voterToCandidateDto.toString());
        System.out.println("The Enter User name is: " + voterToCandidateDto.getName() + "  " + "The Enter User  is: " + voterToCandidateDto.toString());
        UsersEntity NewCandidate = modelMapper.map(voterToCandidateDto, UsersEntity.class);
        PartiesEntity newParty = NewCandidate.getParty();
        if (newParty != null) {
            PartiesEntity party1 = newParty;
            partiesRepository.save(party1);
            System.out.println("The New party president is :" + party1.getPartyPresident());

        }

        UsersEntity existingVoter = usersRepository.findByCnic(NewCandidate.getCnic());
        existingVoter.setParty(NewCandidate.getParty());
        Optional<ConstituenciesEntity> electoralEntity = constituencyRepository.findById(NewCandidate.getElectionConstituency().getId());
        if (electoralEntity.isPresent()) {
            ConstituenciesEntity newElectoralCons = electoralEntity.get(); // Use get() to retrieve the actual entity
            existingVoter.setElectionConstituency(newElectoralCons);
        }
        usersRepository.save(existingVoter);
        Long existingVoterCNIC = existingVoter.getCnic();
        return existingVoterCNIC;
    }

    public String listCandidates(Model model) {

        model.addAttribute("Candidates", commonService.ListAllPartiesOrCandidates());
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Voter/CandidatesList"; // Thymeleaf template for displaying the list
    }

    public String listCandidates(Model model, @RequestParam Long search) {

        model.addAttribute("filterCandidates", commonService.searchCandidate(search));
        model.addAttribute("votingTimeDTO", commonService.votingTimeEntity());
        model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        return "Voter/CandidatesList";
    }

    @Override
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
        return usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }


}

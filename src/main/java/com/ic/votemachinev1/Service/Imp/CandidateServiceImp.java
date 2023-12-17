package com.ic.votemachinev1.Service.Imp;

import com.cloudinary.Cloudinary;
import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.DTOs.Candidate.CandidatesOrPartyDTO;
import com.ic.votemachinev1.DTOs.Party.ElectionResultsDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTOToFetch;
import com.ic.votemachinev1.Model.PartiesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Model.VotingTimeEntity;
import com.ic.votemachinev1.Repository.VotingTimeRepository;
import com.ic.votemachinev1.Service.CandidateService;
import com.ic.votemachinev1.Utils.SessionData;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    private final CommonServiceImp commonServiceImp;
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

    @Override
    public ModelAndView ShowDashBoard(Model model) {
        ModelAndView modelAndView = new ModelAndView("Candidate/CandidateDashboard");

        UsersEntity userDetails = GetLoggedInUserDetails();

        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("profilePicture", commonServiceImp.ShowProfilePic());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        return modelAndView;

    }

    @Override
    public String ListAllApprovedCandidates(Model model) {
        List<CandidatesOrPartyDTO> approvedCandidate = commonServiceImp.ListAllApprovedCandidate();
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        model.addAttribute("approvedCandidate", approvedCandidate);
        /*  model.addAttribute("SessionData", commonService.getSessionData());*/
        return "Candidate/CastVoteDashBoard"; // Thymeleaf template for displaying the list
    }

    public String SaveCastedVote(@RequestParam("PartyID") Long partyID, Model model) {
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        commonServiceImp.SaveCastedVote(partyID);
        return "Candidate/VoteCastedSuccess";
    }

    public String ElectionResults(int page, int size, Model model) {
        Page<PartiesEntity> parties = commonServiceImp.getSortedParties(page, size);
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        model.addAttribute("Parties", parties);
        return "Candidate/ElectionResults";
    }

    public String ResultsByPartyNames(Model model, String search, int page, int size) {
        PartiesEntity filteredParty = commonServiceImp.SearchByPartyName(search); // Fetch the list of voters from the service
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        model.addAttribute("filteredParty", filteredParty);
        return "Candidate/ElectionResults"; // Thymeleaf template for displaying the list

    }

    public String Logout() {
        commonServiceImp.Logout();
        return "redirect:/login";
    }

    @Override
    public String VoterByCNIC(Model model, Long search) {
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        model.addAttribute("filteredVoter", commonServiceImp.searchVoter(search));
        return "Candidate/VoterList"; // Thymeleaf template for displaying the list

    }

    public String listVoters(Model model) {
        List<VoterDTOToFetch> voters = commonServiceImp.ListAllVoters(); // Fetch the list of voters from the service
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        model.addAttribute("userDetails", commonServiceImp.GetLoggedInUserDetails());
        model.addAttribute("votingTimeDTO", commonServiceImp.votingTimeEntity());
        //  model.addAttribute("userDetails", commonService.GetLoggedInUserDetails());
        model.addAttribute("voters", voters);
        return "Candidate/VoterList"; // Thymeleaf template for displaying the list
    }

    public Optional<VotingTimeEntity> votingTimeEntity() {
        Optional<VotingTimeEntity> votingTimeEntity = votingTimeRepository.findById(1L);
        return votingTimeEntity;

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

}

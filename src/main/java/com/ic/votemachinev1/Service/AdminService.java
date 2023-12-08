package com.ic.votemachinev1.Service;

import com.ic.votemachinev1.DTOs.Admin.ConstituencyDTO;
import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.DTOs.Candidate.CandidatesOrPartyDTO;
import com.ic.votemachinev1.DTOs.Party.ElectionResultsDTO;
import com.ic.votemachinev1.DTOs.Party.PartyDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.UsersEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface AdminService {


    public void SaveConstituency(ConstituencyDTO constituencyName);

    public void ApproveCandidate(Long cnic);

    public void SendVoterCandidateInvite(Long cnic);
}

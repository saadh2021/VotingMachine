package com.ic.votemachinev1.Service;

import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.ConstituenciesEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Home {
    String ShowDashboardAfterAuthentication(String userName, String password, MultipartFile multipartFile);

    public void SaveVoter(VoterDTO voterDTO);

    public List<ConstituenciesEntity> AllConstituencies();

}

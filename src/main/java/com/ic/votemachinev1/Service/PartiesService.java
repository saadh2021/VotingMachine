package com.ic.votemachinev1.Service;

import com.ic.votemachinev1.DTOs.Party.PartyDTO;
import com.ic.votemachinev1.Model.PartiesEntity;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PartiesService {

    ResponseEntity<PartyDTO> RegisterParty(PartyDTO partyDTO);

    ResponseEntity<List<PartyDTO>> AllPartiesOrCandidate();

    public Page<PartiesEntity> getSortedParties(int page, int size);
}

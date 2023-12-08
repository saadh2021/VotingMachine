package com.ic.votemachinev1.Service.Imp;

import com.ic.votemachinev1.DTOs.Party.PartyDTO;
import com.ic.votemachinev1.DTOs.Voter.VoterDTO;
import com.ic.votemachinev1.Model.PartiesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Repository.PartiesRepository;
import com.ic.votemachinev1.Service.PartiesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class PartiesServiceImp implements PartiesService {

    private final PartiesRepository partiesRepository;

    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<PartyDTO> RegisterParty(PartyDTO partyDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<PartyDTO>> AllPartiesOrCandidate() {
        return null;
    }

    @Override
    public Page<PartiesEntity> getSortedParties(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Fetch sorted parties using repository method
        return partiesRepository.findAllByOrderByEarnedVotesDesc(pageable);
    }

    public PartiesEntity SearchParty(String name) {
        PartiesEntity filterParty = partiesRepository.findByPartyName(name);
        // PartyDTO filterPartyDTO = modelMapper.map(filterParty, PartyDTO.class);
        return filterParty;
    }
}

package com.ic.votemachinev1.Service.Imp;

import com.ic.votemachinev1.DTOs.Admin.ConstituencyDTO;
import com.ic.votemachinev1.Service.ConstituenciesService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConstituencyServiceImp implements ConstituenciesService {
    @Override
    public ResponseEntity<ConstituencyDTO> CreateConstituency(ConstituencyDTO constituencyDTO) {
        return null;
    }
}

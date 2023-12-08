package com.ic.votemachinev1.Service;

import com.ic.votemachinev1.DTOs.Admin.ConstituencyDTO;
import org.springframework.http.ResponseEntity;

public interface ConstituenciesService {

    ResponseEntity<ConstituencyDTO> CreateConstituency(ConstituencyDTO constituencyDTO);

}

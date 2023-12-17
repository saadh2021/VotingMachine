package com.ic.votemachinev1.DTOs.Candidate;

import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.PartiesEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class CandidatesOrPartyDTO {
    Long id;
    String name;
    String email;
    String password;
    Long cnic;
    boolean voteCasted;
    String votedForWhichParty;
    ConstituenciesEntity residentialConstituency;
    PartiesEntity party;

    ConstituenciesEntity electionConstituency;


}

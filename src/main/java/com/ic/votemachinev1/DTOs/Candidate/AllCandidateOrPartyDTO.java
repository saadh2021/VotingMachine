package com.ic.votemachinev1.DTOs.Candidate;

import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.PartiesEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AllCandidateOrPartyDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

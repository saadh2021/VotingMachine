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
   public Long id;
    public String name;
    public String userName;
    public String password;
    public Long cnic;
    public boolean vote_Casted;
    public String votedForWhichParty;
    public ConstituenciesEntity residential_Constituency;
    public PartiesEntity party;

    public Long electionConstituency;




}

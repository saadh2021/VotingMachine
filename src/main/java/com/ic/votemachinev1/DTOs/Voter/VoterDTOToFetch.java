package com.ic.votemachinev1.DTOs.Voter;

import com.ic.votemachinev1.Model.ConstituenciesEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class VoterDTOToFetch {

   public Long Id;
   public String name;
   public Long cnic;

   String email;
   String password;
   ConstituenciesEntity residentialConstituency;

}

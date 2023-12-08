package com.ic.votemachinev1.DTOs.Party;

import com.ic.votemachinev1.Model.UsersEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;

public class PartyDTO {
    Long id;
    String partyName;
    UsersEntity partyPresident;
    String partySign;
    int earnedVotes;
    boolean approvedByAdmin;

}

package com.ic.votemachinev1.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PartiesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String partyName;
    @OneToOne(mappedBy = "party",cascade = CascadeType.ALL)
   public   UsersEntity partyPresident;
    String partySign;
    int earnedVotes;
    boolean approvedByAdmin;





}

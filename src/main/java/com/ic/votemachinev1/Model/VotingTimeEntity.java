package com.ic.votemachinev1.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class VotingTimeEntity {
    @Id
    public Long id ;
    public LocalTime startVotingTime;
    LocalTime endVotingTime;
    LocalDate startVotingDate;
    LocalDate endVotingDate;
    int VotingStatus; // 0 For Not Schedule  // 1 For Scheduled // 2 For Finished


}

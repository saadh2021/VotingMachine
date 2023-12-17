package com.ic.votemachinev1.DTOs.DateTimeDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Data
public class VotingTimeDTO {
    Long id;
    LocalTime startVotingTime;
    LocalTime endVotingTime;
    LocalDate startVotingDate;
    LocalDate endVotingDate;
    int VotingStatus; // 0 For Not Schedule  // 1 For Scheduled // 2 For Finished
}

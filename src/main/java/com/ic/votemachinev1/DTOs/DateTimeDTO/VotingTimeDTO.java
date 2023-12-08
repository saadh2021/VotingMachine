package com.ic.votemachinev1.DTOs.DateTimeDTO;

import com.ic.votemachinev1.Model.VotingScheduleStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class VotingTimeDTO {
    public Long id;
    public LocalTime startVotingTime;
    public LocalTime endVotingTime;
    public LocalDate startVotingDate;
    public LocalDate endVotingDate;
    public int  VotingStatus; // 0 For Not Schedule  // 1 For Scheduled // 2 For Finished
}

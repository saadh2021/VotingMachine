package com.ic.votemachinev1.Service;

import com.ic.votemachinev1.DTOs.DateTimeDTO.VotingTimeDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface VotingTimeService {

    void ScheduleElections(VotingTimeDTO votingTimeDTO);

    boolean isFutureDateTime(LocalDate date, LocalTime time);

    void scheduleEndElectionTrigger(LocalDate startDate, LocalTime startTime);

     long calculateInitialDelay(LocalDateTime startDateTime);

    public void StartElection();

    public void EndElection();


     void scheduleStartElectionTrigger(LocalDate startDate, LocalTime startTime);
}

package com.ic.votemachinev1.Service.Imp;

import com.ic.votemachinev1.DTOs.DateTimeDTO.VotingTimeDTO;
import com.ic.votemachinev1.Model.VotingTimeEntity;
import com.ic.votemachinev1.Repository.VotingTimeRepository;
import com.ic.votemachinev1.Service.VotingTimeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service


public class VotingTimeServiceImp implements VotingTimeService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    VotingTimeRepository votingTimeRepository;
    VotingTimeEntity newVotingSchedule = new VotingTimeEntity();


    /* public void ScheduleElections(VotingTimeDTO VotingTimeDTO) {
         newVotingSchedule = modelMapper.map(VotingTimeDTO, VotingTimeEntity.class);
         // if (NewVotingSchedule.getStartVotingDate().isAfter(LocalDate.now())) {
         if (newVotingSchedule.getStartVotingTime().isAfter(LocalTime.now())) {
             newVotingSchedule.set(1);
             newVotingSchedule.setId(1L);
             DeletePreviousScheduleExists();
             votingTimeRepository.save(newVotingSchedule);

             //      }
         } else {
             newVotingSchedule.setVotingStatus(0);

         }
     }*/

    @Override

    public void ScheduleElections(VotingTimeDTO votingTimeDTO) {
        System.out.println("The Scheduled Elections are: " + votingTimeDTO);
        VotingTimeEntity newVotingSchedule = modelMapper.map(votingTimeDTO, VotingTimeEntity.class);

        if (isFutureDateTime(newVotingSchedule.getStartVotingDate(), newVotingSchedule.getStartVotingTime())) {
            newVotingSchedule.setId(1L);
            DeletePreviousScheduleExists();
            newVotingSchedule.setVotingStatus(1); //Elections Scheduled
            votingTimeRepository.save(newVotingSchedule);

            // Schedule the StartElection method exactly at the specified date and time
            scheduleStartElectionTrigger(newVotingSchedule.getStartVotingDate(), newVotingSchedule.getStartVotingTime());
            scheduleEndElectionTrigger(newVotingSchedule.getEndVotingDate(), newVotingSchedule.getEndVotingTime());
        } else {
            newVotingSchedule.setVotingStatus(0); //Elections Not Scheduled
        }
    }

    @Override

    public boolean isFutureDateTime(LocalDate date, LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return dateTime.isAfter(LocalDateTime.now());
    }

    @Override
    public void scheduleStartElectionTrigger(LocalDate startDate, LocalTime startTime) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        long initialDelay = calculateInitialDelay(startDateTime);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> StartElection(), initialDelay, TimeUnit.SECONDS);
    }

    @Override
    public void scheduleEndElectionTrigger(LocalDate startDate, LocalTime startTime) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        long initialDelay = calculateInitialDelay(startDateTime);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> EndElection(), initialDelay, TimeUnit.SECONDS);
    }

    @Override

    public long calculateInitialDelay(LocalDateTime startDateTime) {
        LocalDateTime now = LocalDateTime.now();
        return Math.max(0, TimeUnit.SECONDS.convert(now.until(startDateTime, ChronoUnit.SECONDS), TimeUnit.SECONDS));
    }

    @Override

    public void StartElection() {
        System.out.println("Elections are started");
        Optional<VotingTimeEntity> setVotingToStart = votingTimeRepository.findById(1L);
        if (setVotingToStart.isPresent()) {
            VotingTimeEntity votingTimeEntity = setVotingToStart.get();
            votingTimeEntity.setVotingStatus(2); // Elections Started
            votingTimeRepository.save(votingTimeEntity);
        }
    }

    @Override
    public void EndElection() {
        System.out.println("Elections are Ended");
        Optional<VotingTimeEntity> setVotingTEnd = votingTimeRepository.findById(1L);
        if (setVotingTEnd.isPresent()) {
            VotingTimeEntity votingTimeEntity = setVotingTEnd.get();
            votingTimeEntity.setVotingStatus(3); // Elections Started
            votingTimeRepository.save(votingTimeEntity);
        }

    }

    void DeletePreviousScheduleExists() {
        if (votingTimeRepository.existsById(1L)) {
            votingTimeRepository.deleteById(1L);
        }
    }
}



package com.ic.votemachinev1.Service.Imp;

import com.ic.votemachinev1.DTOs.DateTimeDTO.VotingTimeDTO;
import com.ic.votemachinev1.Model.VotingTimeEntity;
import com.ic.votemachinev1.Repository.VotingTimeRepository;
import com.ic.votemachinev1.Service.VotingTimeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service


public class VotingTimeServiceImp implements VotingTimeService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    VotingTimeRepository votingTimeRepository;


    public void ScheduleElections(VotingTimeDTO VotingTimeDTO) {
        VotingTimeEntity NewVotingSchedule = new VotingTimeEntity();
        NewVotingSchedule = modelMapper.map(VotingTimeDTO, VotingTimeEntity.class);
        if (NewVotingSchedule.getStartVotingDate().isAfter(LocalDate.now())) {
            if (NewVotingSchedule.getStartVotingTime().isAfter(LocalTime.now())) {
                NewVotingSchedule.setVotingStatus(1);
                NewVotingSchedule.setId(1L);
                DeletePreviousScheduleExists();
                votingTimeRepository.save(NewVotingSchedule);

            }
        } else {
            NewVotingSchedule.setVotingStatus(0);

        }
    }

    void DeletePreviousScheduleExists() {
        if (votingTimeRepository.existsById(1L)) {
            votingTimeRepository.deleteById(1L);
        }
    }
}

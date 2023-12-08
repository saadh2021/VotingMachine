package com.ic.votemachinev1.Utils;

import com.ic.votemachinev1.Model.VotingTimeEntity;
import com.ic.votemachinev1.Repository.VotingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunr implements ApplicationRunner {

    @Autowired
    VotingTimeRepository votingTimeRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (votingTimeRepository.existsById(1L)) {
            return;
        } else {
            VotingTimeEntity votingTimeEntity = new VotingTimeEntity();
            votingTimeEntity.setVotingStatus(0);
            votingTimeEntity.setId(1L);
            votingTimeRepository.save(votingTimeEntity);


        }
    }
}

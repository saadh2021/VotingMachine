package com.ic.votemachinev1.Utils;

import com.ic.votemachinev1.Model.*;
import com.ic.votemachinev1.Repository.ConstituencyRepository;
import com.ic.votemachinev1.Repository.UsersRepository;
import com.ic.votemachinev1.Repository.VotingTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApplicationRunr implements ApplicationRunner {

    private final VotingTimeRepository votingTimeRepository;
    private final ConstituencyRepository constituencyRepository;
    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        ConstituenciesEntity optionalConstituenciesEntity = constituencyRepository.findByConstituencyName("DHA1");
        if (optionalConstituenciesEntity == null) {
            optionalConstituenciesEntity = new ConstituenciesEntity();
            optionalConstituenciesEntity.setConstituencyName("DHA1");
            constituencyRepository.save(optionalConstituenciesEntity);

            Optional<UsersEntity> optionalUsers = usersRepository.findByEmail("Admin@gmail.com");
            if (optionalUsers.isEmpty()) {
                UsersEntity adminUser = new UsersEntity();
                adminUser.setName("Admin");
                adminUser.setEmail("admin@gmail.com");
                adminUser.setCnic(352023687451L);
                adminUser.setResidentialConstituency(constituencyRepository.findByConstituencyName("DHA1"));
                adminUser.setPassword(passwordEncoder.encode("321..123mM"));
                adminUser.setRole(UserRolesEntity.Admin);
                adminUser.setStatus(UserAccountStatus.VERIFIED);
                adminUser.setVoteCasted(false);
                usersRepository.save(adminUser);
            }
        }
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

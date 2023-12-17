package com.ic.votemachinev1;

import com.cloudinary.Cloudinary;
import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.UserAccountStatus;
import com.ic.votemachinev1.Model.UserRolesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Repository.ConstituencyRepository;
import com.ic.votemachinev1.Repository.UsersRepository;
import com.ic.votemachinev1.Security.JWTUtility;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
public class VoteMachineV1Application implements CommandLineRunner {



    private final UsersRepository usersRepository;



    public static void main(String[] args) {
        SpringApplication.run(VoteMachineV1Application.class, args);

        //UsersEntity usersEntity = new UsersEntity();

    }

    @Bean
    public Cloudinary getCloudinary() {

        Map config = new HashMap<>();
        config.put("cloud_name", "djkkn5ddr");
        config.put("api_key", "851142864395468");
        config.put("api_secret", "Elkws_P190HBChIQOb0vo8wg9wg");

        return new Cloudinary(config);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5); // Set the pool size according to your requirements
        scheduler.setThreadNamePrefix("VotingSchedulerService-");
        scheduler.initialize();
        return scheduler;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();

    }

    @Override
    public void run(String... args) throws Exception {





    }
}

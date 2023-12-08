package com.ic.votemachinev1.Utils;

import com.ic.votemachinev1.Model.UserRolesEntity;
import com.ic.votemachinev1.Security.JWTAuthEntry;
import com.ic.votemachinev1.Security.JWTAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    //private final UserServiceImpl userService;
    private final JWTAuthEntry point;
    private final JWTAuthFilter filter;
    private final PasswordEncoder passwordEncoder;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeRequests()
                .requestMatchers(Constants.AUTH_WHITELIST).permitAll()
                .requestMatchers(Constants.ADMIN_LIST).hasAuthority(UserRolesEntity.Admin.toString())
                .requestMatchers(Constants.CANDIDATE_LIST).hasAuthority(UserRolesEntity.Candidate.toString())
                .requestMatchers(Constants.Voter_LIST).hasAuthority(UserRolesEntity.Voter.toString())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userService);
//        provider.setPasswordEncoder(passwordEncoder);
//        return provider;
//    }
}








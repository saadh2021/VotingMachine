package com.ic.votemachinev1.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UsersEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    // @Id
    @Column(unique = true)
    Long cnic;
    String name;
    @Column(unique = true)

    String userName;
    String password;

    String userImg;

    boolean vote_Casted;
    String votedForWhichParty;
    @ManyToOne()
    @JoinColumn(name = "constituency_id", referencedColumnName = "id")
    ConstituenciesEntity residentialConstituency;
    String electionConstituency;
    Boolean approvedCandidate;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    UserRolesEntity role;

    //Candidate Represents Which Party
    @OneToOne
    @JoinColumn(name = "party_id")
    public PartiesEntity party;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}

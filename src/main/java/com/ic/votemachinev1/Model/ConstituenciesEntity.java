package com.ic.votemachinev1.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ConstituenciesEntity {
    @Id
    @GeneratedValue
    Long id;
    @Column(unique = true)
    String constituencyName;


    @JsonIgnore
    @OneToMany(mappedBy = "residentialConstituency",cascade = CascadeType.ALL)
    List<UsersEntity> voters;
}

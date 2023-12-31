package com.ic.votemachinev1.Repository;

import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.UserAccountStatus;
import com.ic.votemachinev1.Model.UserRolesEntity;
import com.ic.votemachinev1.Model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UsersRepository  extends JpaRepository<UsersEntity, Long> {

    List<UsersEntity> findByRole(UserRolesEntity role);
    List<UsersEntity> findByApprovedCandidateAndElectionConstituency(Boolean approvedCandidate, ConstituenciesEntity constituenciesEntity);

    Optional<UsersEntity> findByCnicAndStatus(Long cnic, UserAccountStatus userAccountStatus);

    UsersEntity findByCnic(Long CNIC);
    UsersEntity findByName(String name);


  /*  @Query(value = "SELECT DISTINCT u.* FROM user u " +
            "JOIN constituencies_entity c ON u.residential_constituency_id = c.user_id " +
            "WHERE c.name = :name", nativeQuery = true)
    List<UsersEntity> findUsersByResidentialConstituency(@Param("name") String name);
*/
    //List<UsersEntity> findByConstituencyId(Long id);
    List<UsersEntity> findByResidentialConstituencyId(Long id);
    Optional<UsersEntity> findByEmail(String email);
}

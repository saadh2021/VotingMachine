package com.ic.votemachinev1.Repository;

import com.ic.votemachinev1.Model.VotingTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface VotingTimeRepository  extends JpaRepository<VotingTimeEntity, Long> {
}

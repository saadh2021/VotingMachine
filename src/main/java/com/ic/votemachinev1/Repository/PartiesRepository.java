package com.ic.votemachinev1.Repository;

import com.ic.votemachinev1.Model.ConstituenciesEntity;
import com.ic.votemachinev1.Model.PartiesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PartiesRepository extends JpaRepository<PartiesEntity, Long> {


    Page<PartiesEntity> findAllByOrderByEarnedVotesDesc(Pageable pageable);

    PartiesEntity findByPartyName(String name);


}

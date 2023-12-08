package com.ic.votemachinev1.Repository;

import com.ic.votemachinev1.Model.ConstituenciesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConstituencyRepository extends JpaRepository <ConstituenciesEntity, Long> {

    //List<Long> findByConstituencyName(String Name);
    ConstituenciesEntity findByConstituencyName(String Name);
}

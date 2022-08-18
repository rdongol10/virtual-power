package com.rdongol.virtualpower.repository;

import com.rdongol.virtualpower.model.entity.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {

    boolean existsByName( String name);

    @Query("Select b from Battery b where b.postCode in :postCodes" )
    List<Battery> findByPostCodes(List<String> postCodes);
}

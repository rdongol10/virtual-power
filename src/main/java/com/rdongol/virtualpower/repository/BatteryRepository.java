package com.rdongol.virtualpower.repository;

import com.rdongol.virtualpower.model.entity.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {

    boolean existsByPostCodeAndName(String postcode, String name);

}

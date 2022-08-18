package com.rdongol.virtualpower.service;

import com.rdongol.virtualpower.model.entity.Battery;
import com.rdongol.virtualpower.repository.BatteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryServiceImpl implements BatteryService {

    private final BatteryRepository batteryRepository;

    @Autowired
    public BatteryServiceImpl(BatteryRepository batteryRepository) {
        this.batteryRepository = batteryRepository;
    }

    @Override
    public List<Battery> saveAll(List<Battery> batteries) {
        return this.batteryRepository.saveAll(batteries);
    }

    @Override
    public boolean existsByName(String name) {
        return this.batteryRepository.existsByName(name);
    }

    @Override
    public List<Battery> findByPostcodes(List<String> postCodes) {
        return this.batteryRepository.findByPostCodes(postCodes);
    }
}

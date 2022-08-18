package com.rdongol.virtualpower.service;

import com.rdongol.virtualpower.model.entity.Battery;

import java.util.List;

public interface BatteryService {

    List<Battery> saveAll(List<Battery> batteries);

    boolean existsByName(String name);

}

package com.rdongol.virtualpower.service;

import com.rdongol.virtualpower.commons.utils.Utils;
import com.rdongol.virtualpower.exception_handler.exception.UnProcessableEntityException;
import com.rdongol.virtualpower.model.entity.Battery;
import com.rdongol.virtualpower.model.request.BatteryRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatterySaveHelper {

    private List<String> errors;
    private final BatteryService batteryService;

    public BatterySaveHelper(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    public List<Battery> processBatteries(List<BatteryRequest> batteryRequests) {

        this.validateBatteryRequests(batteryRequests);

        if (!this.errors.isEmpty()) {
            throw new UnProcessableEntityException(this.errors, "Un processable entity");
        }

        batteryRequests = this.removeDuplicate(batteryRequests);

        List<Battery> batteries = batteryRequests.stream().map(
                        fromValue -> Utils.mapObject(fromValue, Battery.class))
                .collect(Collectors.toList());

        return this.batteryService.saveAll(batteries);
    }

    private List<BatteryRequest> removeDuplicate(List<BatteryRequest> batteryRequests) {
        return new ArrayList<>(new HashSet<>(batteryRequests));
    }

    private void validateBatteryRequests(List<BatteryRequest> batteryRequests) {
        errors = new ArrayList<>();
        int count = 1;
        for (BatteryRequest batteryRequest : batteryRequests) {
            if (StringUtils.isEmpty(batteryRequest.getName())) {
                errors.add("Name is missing for entity at position " + count);
            }

            if (StringUtils.isEmpty(batteryRequest.getPostCode())) {
                errors.add("Post Code is missing for entity at position " + count);
            }

            if (batteryRequest.getWattCapacity() <= 0) {
                errors.add("Watt is invalid at position " + count);
            }

            if (StringUtils.isNotEmpty(batteryRequest.getPostCode()) &&
                    StringUtils.isNotEmpty(batteryRequest.getName()) &&
                    batteryService.existsByName(batteryRequest.getName())
            ) {
                errors.add("Name:" + batteryRequest.getName() + " already exists");
            }

            count++;
        }
    }


}

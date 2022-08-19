package com.rdongol.virtualpower.controller;

import com.rdongol.virtualpower.commons.enums.SuccessMessage;
import com.rdongol.virtualpower.model.request.BatteryRequest;
import com.rdongol.virtualpower.model.request.PostCodeRequest;
import com.rdongol.virtualpower.model.response.RestDataResponse;
import com.rdongol.virtualpower.model.response.RestResponse;
import com.rdongol.virtualpower.service.BatterySaveHelper;
import com.rdongol.virtualpower.service.BatteryListHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/battery")
public class BatteryController {

    private final BatterySaveHelper batterySaveHelper;

    private final BatteryListHelper batteryListHelper;

    public BatteryController(BatterySaveHelper batterySaveHelper, BatteryListHelper batteryListHelper) {
        this.batterySaveHelper = batterySaveHelper;
        this.batteryListHelper = batteryListHelper;
    }

    @PostMapping
    public ResponseEntity<RestResponse> add(@RequestBody List<BatteryRequest> batteryRequests) {
        this.batterySaveHelper.processBatteries(batteryRequests);
        return ResponseEntity.ok(new RestResponse(SuccessMessage.SAVED.getCode(), SuccessMessage.SAVED.getMessage()));
    }

    @PostMapping("/findByPostCodes")
    public ResponseEntity<RestResponse> findByPostCodes(@RequestBody PostCodeRequest postCodeRequest) {

        return ResponseEntity.ok(new RestDataResponse<>(SuccessMessage.SEARCHED.getCode(), SuccessMessage.SEARCHED.getMessage(), this.batteryListHelper.getBatteries(postCodeRequest)));
    }

}

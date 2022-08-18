package com.rdongol.virtualpower.controller;

import com.rdongol.virtualpower.model.request.BatteryRequest;
import com.rdongol.virtualpower.model.response.RestResponse;
import com.rdongol.virtualpower.service.BatterSaveHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/battery")
public class BatteryController {

    private final BatterSaveHelper batterSaveHelper;


    public BatteryController(BatterSaveHelper batterSaveHelper) {
        this.batterSaveHelper = batterSaveHelper;
    }

    @PostMapping
    public ResponseEntity<RestResponse> add(@RequestBody List<BatteryRequest> batteryRequests) {
        this.batterSaveHelper.processBatteries(batteryRequests);
        return ResponseEntity.ok(new RestResponse("Saved", "saved"));
    }

}

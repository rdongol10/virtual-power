package com.rdongol.virtualpower.service;

import com.rdongol.virtualpower.model.entity.Battery;
import com.rdongol.virtualpower.model.request.PostCodeRequest;
import com.rdongol.virtualpower.model.response.BatteriesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BatteryListHelperTest {

    @Mock
    BatteryService batteryService;

    @InjectMocks
    BatteryListHelper batteryListHelper;

    PostCodeRequest postCodeRequest;

    List<Battery> batteries;

    private void initializeEmptyPostCodes() {
        List<String> postCodes = new ArrayList<>();
        postCodeRequest = new PostCodeRequest();
        postCodeRequest.setPostCodes(postCodes);
    }

    private void initializeNullPostCodes() {
        postCodeRequest = new PostCodeRequest();
        postCodeRequest.setPostCodes(null);
    }

    private void initializePostCodes() {
        postCodeRequest = new PostCodeRequest();
        postCodeRequest.setPostCodes(Arrays.asList("123", "345", "456"));
    }


    private Battery initializeBattery(String name, String postCode, int wattCapacity) {
        Battery battery = new Battery();
        battery.setName(name);
        battery.setPostCode(postCode);
        battery.setWattCapacity(wattCapacity);
        return battery;
    }

    private void initializeBatteries() {

        batteries = new ArrayList<>();
        batteries.add(initializeBattery("test8", "456", 100));
        batteries.add(initializeBattery("test2", "345", 50));
        batteries.add(initializeBattery("test6", "123", 90));
        batteries.add(initializeBattery("test1", "123", 100));
        batteries.add(initializeBattery("test3", "456", 200));
        batteries.add(initializeBattery("test5", "456", 70));
        batteries.add(initializeBattery("test4", "345", 500));
        batteries.add(initializeBattery("test7", "345", 100));

    }

    @Test
    void testForEmptyPostCodes() {
        initializeEmptyPostCodes();
        BatteriesResponse batteriesResponse = batteryListHelper.getBatteries(postCodeRequest);
        assertTrue(batteriesResponse.getBatteries().isEmpty());
        assertEquals(0, batteriesResponse.getAverageWatt());
        assertEquals(0, batteriesResponse.getTotalWatts());
    }

    @Test
    void testForNullPostCodes() {
        initializeNullPostCodes();
        BatteriesResponse batteriesResponse = batteryListHelper.getBatteries(postCodeRequest);
        assertTrue(batteriesResponse.getBatteries().isEmpty());
        assertEquals(0, batteriesResponse.getAverageWatt());
        assertEquals(0, batteriesResponse.getTotalWatts());
    }

    @Test
    void testForNullPostCodeRequests() {
        BatteriesResponse batteriesResponse = batteryListHelper.getBatteries(null);
        assertTrue(batteriesResponse.getBatteries().isEmpty());
        assertEquals(0, batteriesResponse.getAverageWatt());
        assertEquals(0, batteriesResponse.getTotalWatts());
    }

    @Test
    void testForEmptyBatteries() {
        initializePostCodes();
        Mockito.when(batteryService.findByPostcodes(Mockito.any())).thenReturn(Collections.emptyList());
        BatteriesResponse batteriesResponse = batteryListHelper.getBatteries(postCodeRequest);
        assertTrue(batteriesResponse.getBatteries().isEmpty());
        assertEquals(0, batteriesResponse.getAverageWatt());
        assertEquals(0, batteriesResponse.getTotalWatts());
    }

    @Test
    void successTest() {
        initializePostCodes();
        initializeBatteries();
        Mockito.when(batteryService.findByPostcodes(Mockito.any())).thenReturn(batteries);
        BatteriesResponse batteriesResponse = batteryListHelper.getBatteries(postCodeRequest);
        assertEquals(8, batteriesResponse.getBatteries().size());
        assertEquals(151.25, batteriesResponse.getAverageWatt());
        assertEquals(1210, batteriesResponse.getTotalWatts());
        assertThat(batteriesResponse.getBatteries(), contains("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8"));
    }
}

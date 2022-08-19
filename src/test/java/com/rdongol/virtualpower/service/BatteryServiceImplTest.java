package com.rdongol.virtualpower.service;

import com.rdongol.virtualpower.model.entity.Battery;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BatteryServiceImplTest {

    @Autowired
    BatteryService batteryService;

    List<Battery> batteries;

    private Battery initializeBattery(String name, String postCode, int wattCapacity) {
        Battery battery = new Battery();
        battery.setName(name);
        battery.setPostCode(postCode);
        battery.setWattCapacity(wattCapacity);
        return battery;
    }

    @BeforeEach
    private void initializeBatteries() {

        batteries = new ArrayList<>();
        batteries.add(initializeBattery("test2", "345", 50));
        batteries.add(initializeBattery("test1", "123", 100));
        batteries.add(initializeBattery("test3", "456", 200));
        batteries.add(initializeBattery("test4", "345", 500));

    }


    @Test
    @Transactional
    void saveAllTest() {
        List<Battery> savedBatteries = batteryService.saveAll(batteries);
        assertNotNull(savedBatteries);
        assertEquals(4, savedBatteries.size());
        assertThat(
                savedBatteries,
                hasItem(allOf(
                        Matchers.<Battery>hasProperty("name", is("test2")),
                        Matchers.<Battery>hasProperty("postCode", is("345")),
                        Matchers.<Battery>hasProperty("wattCapacity", is(50))

                ))
        );
    }

    @Test
    @Transactional
    void existsByName() {
        batteryService.saveAll(batteries);
        assertTrue(batteryService.existsByName("test1"));
    }

    @Test
    void doesNotExistsByName() {
        assertFalse(batteryService.existsByName("test1"));
    }


    @Test
    @Transactional
    void findByPostcodes() {

        batteryService.saveAll(batteries);
        List<String> postCodes = Arrays.asList("345", "123");

        List<Battery> batteryResponse = batteryService.findByPostcodes(postCodes);
        assertNotNull(batteryResponse);
        assertEquals(3, batteryResponse.size());
        assertThat(
                batteryResponse,
                hasItem(allOf(
                        Matchers.<Battery>hasProperty("name", is("test2")),
                        Matchers.<Battery>hasProperty("postCode", is("345")),
                        Matchers.<Battery>hasProperty("wattCapacity", is(50))

                ))
        );
        assertThat(
                batteryResponse,
                hasItem(allOf(
                        Matchers.<Battery>hasProperty("name", is("test4")),
                        Matchers.<Battery>hasProperty("postCode", is("345")),
                        Matchers.<Battery>hasProperty("wattCapacity", is(500))

                ))
        );
        assertThat(
                batteryResponse,
                hasItem(allOf(
                        Matchers.<Battery>hasProperty("name", is("test1")),
                        Matchers.<Battery>hasProperty("postCode", is("123")),
                        Matchers.<Battery>hasProperty("wattCapacity", is(100))
                ))
        );
    }

}

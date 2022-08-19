package com.rdongol.virtualpower.service;

import com.rdongol.virtualpower.commons.utils.Utils;
import com.rdongol.virtualpower.exception_handler.exception.UnProcessableEntityException;
import com.rdongol.virtualpower.model.entity.Battery;
import com.rdongol.virtualpower.model.request.BatteryRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class BatterySaveHelperTest {

    @InjectMocks
    BatterySaveHelper batterySaveHelper;

    List<BatteryRequest> requestList;
    List<Battery> responseList;

    @Mock
    BatteryService batteryService;

    private void initializeValidationFailEntity() {
        requestList = new ArrayList<>();
        requestList.addAll(Arrays.asList(
                new BatteryRequest("Test", "", 100),
                new BatteryRequest("", "1234", 100),
                new BatteryRequest("Test2", "2345", 0)
        ));
    }

    private void initializeSuccessEntity() {
        requestList = new ArrayList<>();
        requestList.addAll(Arrays.asList(
                new BatteryRequest("Test", "1234", 100),
                new BatteryRequest("Test1", "1234", 100),
                new BatteryRequest("Test2", "2345", 105)
        ));
    }

    private void initializeSuccessResponse() {
        responseList = new ArrayList<>();
        responseList = requestList.stream().map(
                        fromValue -> Utils.mapObject(fromValue, Battery.class))
                .collect(Collectors.toList());
    }

    private void initializeEntityForExistsTest() {
        requestList = new ArrayList<>();
        requestList.add(new BatteryRequest("Test", "1234", 100));
    }

    @Test
    void validationFailTest() {
        initializeValidationFailEntity();

        UnProcessableEntityException exception =
                assertThrows(UnProcessableEntityException.class, () -> batterySaveHelper.processBatteries(requestList));
        assertEquals(exception.getMessage(), "Un processable entity");
        assertEquals(3, exception.getErrors().size());
        assertThat(exception.getErrors(), contains("Post Code is missing for entity at position 1",
                "Name is missing for entity at position 2",
                "Watt is invalid at position 3"
        ));
    }

    @Test
    void nameAlreadyExistsTest() {
        initializeEntityForExistsTest();
        Mockito.when(batteryService.existsByName(Mockito.any())).thenReturn(true);
        UnProcessableEntityException exception =
                assertThrows(UnProcessableEntityException.class, () -> batterySaveHelper.processBatteries(requestList));
        assertEquals(exception.getMessage(), "Un processable entity");
        assertEquals(1, exception.getErrors().size());
        assertThat(exception.getErrors(), contains("Name:Test already exists"));
    }


    @Test
    void successTest() {
        initializeSuccessEntity();
        initializeSuccessResponse();
        Mockito.when(batteryService.existsByName(Mockito.any())).thenReturn(false);
        Mockito.when(batteryService.saveAll(Mockito.any())).thenReturn(responseList);
        assertEquals(3, batterySaveHelper.processBatteries(requestList).size());

    }


}

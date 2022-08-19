package com.rdongol.virtualpower.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdongol.virtualpower.model.request.BatteryRequest;
import com.rdongol.virtualpower.model.request.PostCodeRequest;
import com.rdongol.virtualpower.model.response.BatteriesResponse;
import com.rdongol.virtualpower.model.response.ErrorResponse;
import com.rdongol.virtualpower.model.response.RestDataResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BatteryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    List<BatteryRequest> requestList;

    PostCodeRequest postCodeRequest;

    private void initializeSuccessEntity() {
        requestList = new ArrayList<>();
        requestList.addAll(Arrays.asList(
                new BatteryRequest("Test", "123", 100),
                new BatteryRequest("Test1", "345", 100),
                new BatteryRequest("Test2", "456", 105),
                new BatteryRequest("Test3", "123", 105),
                new BatteryRequest("Test4", "345", 105),
                new BatteryRequest("Test5", "123", 105)
        ));
    }

    private void initializeValidationFailEntity() {
        requestList = new ArrayList<>();
        requestList.addAll(Arrays.asList(
                new BatteryRequest("Test", "", 100),
                new BatteryRequest("", "1234", 100),
                new BatteryRequest("Test2", "2345", 0)
        ));
    }

    private void initializePostCodeRequest() {
        postCodeRequest = new PostCodeRequest();
        postCodeRequest.setPostCodes(Arrays.asList("123", "345"));
    }

    @Test
    @Transactional
    void successSave() throws Exception {
        initializeSuccessEntity();

        mockMvc.perform(post("/battery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestList)))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void unsupportedEntityTest() throws Exception {
        initializeValidationFailEntity();
        MvcResult mvcResult = mockMvc.perform(post("/battery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestList)))
                .andExpect(status().is4xxClientError())
                .andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
        assertEquals("Un processable entity", errorResponse.getMessage());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), errorResponse.getStatus());
        assertThat(errorResponse.getErrors(), contains("Post Code is missing for entity at position 1",
                "Name is missing for entity at position 2",
                "Watt is invalid at position 3"
        ));
    }


    @Test
    @Transactional
    void findByPostCodeTest() throws Exception {
        initializePostCodeRequest();
        initializeSuccessEntity();
        mockMvc.perform(post("/battery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestList)));

        MvcResult mvcResult = mockMvc.perform(post("/battery/findByPostCodes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCodeRequest)))
                .andExpect(status().isOk())
                .andReturn();

        RestDataResponse<BatteriesResponse> batteryResponses = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<RestDataResponse<BatteriesResponse>>() {
        });
        assertEquals(515, batteryResponses.getData().getTotalWatts());
        assertEquals(103, batteryResponses.getData().getAverageWatt());
        assertThat(batteryResponses.getData().getBatteries(), contains("Test", "Test1", "Test3", "Test4", "Test5"));
        assertEquals(5, batteryResponses.getData().getBatteries().size());


    }
}

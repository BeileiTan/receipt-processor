package com.springdemo.receiptprocessoronline.reciept;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springdemo.receiptprocessoronline.exception.ObjectNotFoundException;
import com.springdemo.receiptprocessoronline.system.StatusCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReceiptService receiptService;

    @Autowired
    ObjectMapper objectMapper;

    private Receipt r1;

    @BeforeEach
    void setUp() {
        r1 = new Receipt();
        r1.setRetailer("target");
        r1.setPurchaseDate("2022-01-01");
        r1.setPurchaseTime("13:01");
        r1.setTotal("6.49");

        ReceiptItem item1 = new ReceiptItem();
        item1.setShortDescription("Item one");
        item1.setPrice("18.8");

        ReceiptItem item2 = new ReceiptItem();
        item2.setShortDescription("Item two");
        item2.setPrice("24.5");

        r1.setItems(Arrays.asList(item1, item2));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findReceiptById() throws Exception{
        r1 = new Receipt();
        r1.setId("adb6b560-0eef-42bc-9d16-df48f30e89b2");

        given(receiptService.findById("adb6b560-0eef-42bc-9d16-df48f30e89b2")).willReturn(r1);
        when(receiptService.calculatePoints(r1)).thenReturn(126);

        // Perform GET /receipts/uuid-test/points.
        mockMvc.perform(get("/receipts/adb6b560-0eef-42bc-9d16-df48f30e89b2/points"))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.point").value(126));
    }

    @Test
    public void findReceiptByIdNotFound() throws Exception {
        // Simulate the service returning null for a non-existent receipt.
        given(receiptService.findById("1250808601744904191")).willThrow(new ObjectNotFoundException("receipt", "1250808601744904191"));

        // Expect a 404 Not Found status.
        mockMvc.perform(get("/receipts/1250808601744904191/points"))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("No receipt found for that id 1250808601744904191 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAddReceiptSuccess() throws Exception{
        ReceiptItem item1 = new ReceiptItem();
        item1.setShortDescription("Item one");
        item1.setPrice("18.8");

        ReceiptItem item2 = new ReceiptItem();
        item2.setShortDescription("Item two");
        item2.setPrice("24.5");
        Receipt r = new Receipt(null, "TechStore Inc.",  "2023-10-25", "14:30", Arrays.asList(item1, item2),"1251.98" );

        String json = this.objectMapper.writeValueAsString(r);
        Receipt savedReceipt = new Receipt();
        savedReceipt.setId("1250808601744904197");
        savedReceipt.setRetailer("TechStore Inc.");
        savedReceipt.setPurchaseTime("2023-10-25");
        savedReceipt.setPurchaseDate("14:30");
        savedReceipt.setItems(Arrays.asList(item1, item2));
        savedReceipt.setTotal("1251.98");

        given(this.receiptService.save(Mockito.any(Receipt.class))).willReturn(savedReceipt);

        this.mockMvc.perform(post("/receipts/process").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty());
    }
}




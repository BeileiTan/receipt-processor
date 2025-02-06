package com.springdemo.receiptprocessoronline.reciept;

import com.springdemo.receiptprocessoronline.exception.ObjectNotFoundException;
import com.springdemo.receiptprocessoronline.reciept.utils.IdWorker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    @Mock
    ReceiptRepository receiptRepository;

    @Mock
    IdWorker idWorker;

    @InjectMocks
    ReceiptService receiptService;

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
    void testFindByIdSuccess(){
        //Given: Array inputs and targets. Define the behavior of Mock object receiptRepository
        r1.setId("adb6b560-0eef-42bc-9d16-df48f30e89b2");
        given(receiptRepository.findById("adb6b560-0eef-42bc-9d16-df48f30e89b2")).willReturn(Optional.of(r1));

        //When: Act on the target behavior. When steps should cover the method to be tested.
        Receipt returnedReceipt = receiptService.findById("adb6b560-0eef-42bc-9d16-df48f30e89b2");

        //Then: Assert expected outcomes.
        assertEquals("target", returnedReceipt.getRetailer());
        verify(receiptRepository,times(1)).findById("adb6b560-0eef-42bc-9d16-df48f30e89b2");
    }

    @Test
    void findByIdNotFound(){
        //given
        given(receiptRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() ->{
            Receipt returnedReceipt = receiptService.findById("adb6b560-0eef-42bc-9d16-df48f30e89b2");
        });

        //then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class).
                hasMessage("No receipt found for that id adb6b560-0eef-42bc-9d16-df48f30e89b2 :(");
        verify(receiptRepository, times(1)).findById("adb6b560-0eef-42bc-9d16-df48f30e89b2");
    }

    @Test
    void testSaveSuccess(){
        given(idWorker.nextId()).willReturn(123456L);
        // When repository.save is called, return the receipt with the id.
        given(receiptRepository.save(r1)).willReturn(r1);

        Receipt savedReceipt = receiptService.save(r1);

        assertThat(savedReceipt.getId()).isEqualTo("123456");
        assertThat(savedReceipt.getRetailer()).isEqualTo(r1.getRetailer());
        assertThat(savedReceipt.getPurchaseDate()).isEqualTo(r1.getPurchaseDate());
        assertThat(savedReceipt.getPurchaseTime()).isEqualTo(r1.getPurchaseTime());
        assertThat(savedReceipt.getTotal()).isEqualTo(r1.getTotal());
        verify(receiptRepository, times(1)).save(r1);
    }
}
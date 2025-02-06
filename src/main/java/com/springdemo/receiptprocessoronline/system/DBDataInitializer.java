package com.springdemo.receiptprocessoronline.system;

import com.springdemo.receiptprocessoronline.reciept.Receipt;
import com.springdemo.receiptprocessoronline.reciept.ReceiptItem;
import com.springdemo.receiptprocessoronline.reciept.ReceiptRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DBDataInitializer implements CommandLineRunner {
    private final ReceiptRepository receiptRepository;

    public DBDataInitializer(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Receipt receipt = new Receipt();
        receipt.setId("adb6b560-0eef-42bc-9d16-df48f30e89b2");
        receipt.setRetailer("Target");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");
        receipt.setTotal("35.00");

        ReceiptItem item1 = new ReceiptItem();
        item1.setShortDescription("Mountain Dew 12PK");
        item1.setPrice("6.49");

        ReceiptItem item2 = new ReceiptItem();
        item2.setShortDescription("Emils Cheese Pizza");
        item2.setPrice("12.25");

        // Set the items on the receipt. Note: if using a unidirectional mapping, the join column will be auto-populated.
        receipt.setItems(Arrays.asList(item1, item2));

        // Save the receipt (and cascade the items).
        receiptRepository.save(receipt);

        // Optionally, load more test data.
        System.out.println("Test data loaded.");
    }
}

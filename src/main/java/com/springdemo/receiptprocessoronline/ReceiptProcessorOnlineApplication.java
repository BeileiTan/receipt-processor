package com.springdemo.receiptprocessoronline;

import com.springdemo.receiptprocessoronline.reciept.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReceiptProcessorOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceiptProcessorOnlineApplication.class, args);
	}

	@Bean
	public IdWorker idWorker(){
		return new IdWorker(1, 1);
	}
}

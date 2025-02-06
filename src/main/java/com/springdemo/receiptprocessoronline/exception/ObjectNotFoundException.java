package com.springdemo.receiptprocessoronline.exception;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String objectName, String id) {
        super("No " + objectName + " found for that id " + id + " :(");
    }

    public ObjectNotFoundException(String objectName, Integer id) {
        super("No " + objectName + " found for that id " + id + " :(");
    }
}

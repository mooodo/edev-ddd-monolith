package com.edev.trade.inventory.exception;

public class InventoryException extends RuntimeException {
    public InventoryException(String message) {
        super(message);
    }
    public InventoryException(String message, Object...objects) {
        super(String.format(message, objects));
    }
}

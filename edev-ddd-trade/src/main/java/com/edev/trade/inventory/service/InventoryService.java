package com.edev.trade.inventory.service;

import com.edev.trade.inventory.entity.Inventory;

import java.util.Collection;
import java.util.List;

public interface InventoryService {
    Long stockIn(Long id, Long quantity);
    Long stockOut(Long id, Long quantity);
    void remove(Long id);
    Inventory checkInventory(Long id);
    Collection<Inventory> checkInventories(List<Long> ids);
}

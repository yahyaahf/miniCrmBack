package com.example.minicrm.controller;

import com.example.minicrm.model.PurchaseOrder;
import com.example.minicrm.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("/purchaseOrders/add/{id}")
    public PurchaseOrder createPurchaseOrder(@PathVariable("id")Long id) {
        return purchaseOrderService.savePurchaseOrder(id);
    }

    @GetMapping("/purchaseOrders/{id}")
    public PurchaseOrder getPurchaseOrder(@PathVariable("id")Long id) {
        Optional<PurchaseOrder> purchaseOrder =purchaseOrderService.getPurchaseOrderById(id);
        if(purchaseOrder.isPresent()) {
            return purchaseOrder.get();
        } else {
            return null;
        }
    }

    @GetMapping("/purchaseOrders")
    public Iterable<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrderService.getPurchaseOrders();
    }

    @DeleteMapping("/purchaseOrders/delete/{id}")
    public void deletePurchaseOrder(@PathVariable("id")Long id) {purchaseOrderService.deletePurchaseOrder(id);}

    @PutMapping("/purchaseOrders/update/{id}")
    public PurchaseOrder updatePurchaseOrder(@PathVariable("id") Long id, @RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder updated = purchaseOrderService.updatePurchaseOrder(id,purchaseOrder);
        return updated;
    }


}

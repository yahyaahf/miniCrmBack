package com.example.minicrm.repository;


import com.example.minicrm.model.PurchaseOrder;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder,Long> {
}

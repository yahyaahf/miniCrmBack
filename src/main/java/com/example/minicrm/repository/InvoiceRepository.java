package com.example.minicrm.repository;


import com.example.minicrm.model.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice,Long> {
}

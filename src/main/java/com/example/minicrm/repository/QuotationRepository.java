package com.example.minicrm.repository;


import com.example.minicrm.model.Quotation;
import org.springframework.data.repository.CrudRepository;

public interface QuotationRepository extends CrudRepository<Quotation,Long> {
}

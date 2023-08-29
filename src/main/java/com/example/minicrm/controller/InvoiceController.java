package com.example.minicrm.controller;

import com.example.minicrm.model.Invoice;
import com.example.minicrm.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/invoices/add/{idQuotation}")
    public Invoice createInvoice(@PathVariable("idQuotation")Long idQuotation) {
        return invoiceService.saveInvoice(idQuotation);
    }

    @GetMapping("/invoices/{id}")
    public Invoice getInvoice(@PathVariable("id")Long id) {
        Optional<Invoice> invoice =invoiceService.getInvoiceById(id);
        if(invoice.isPresent()) {
            return invoice.get();
        } else {
            return null;
        }
    }

    @GetMapping("/invoices")
    public Iterable<Invoice> getInvoicets() {
        return invoiceService.getInvoices();
    }

    @DeleteMapping("/invoices/delete/{id}")
    public void deleteInvoice(@PathVariable("id")Long id) {invoiceService.deleteInvoice(id);}

    @PutMapping("/invoices/update/{id}")
    public Invoice updateInvoice(@PathVariable("id") Long id, @RequestBody Invoice invoice) {
        Invoice updated = invoiceService.updateInvoice(id,invoice);
        return updated;
    }

    @PutMapping("/invoices/update/send/{id}")
    public Invoice sendInvoice(@PathVariable("id") Long id) {
        Invoice updated = invoiceService.sendInvoice(id);
        return updated;
    }


}

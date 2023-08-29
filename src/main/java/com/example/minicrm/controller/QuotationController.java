package com.example.minicrm.controller;


import com.example.minicrm.model.Quotation;
import com.example.minicrm.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api")
public class QuotationController {

    @Autowired
    private QuotationService quotationService;

    @PostMapping("/quotations/add/{idClient}")
    public Quotation createQuotation(@PathVariable("idClient")Long idClient,@RequestBody Quotation quotation) {
        return quotationService.saveQuotation(quotation,idClient);
    }

    @GetMapping("/quotations/{id}")
    public Quotation getQuotation(@PathVariable("id")Long id) {
        Optional<Quotation> quotation =quotationService.getQuotationById(id);
        if(quotation.isPresent()) {
            return quotation.get();
        } else {
            return null;
        }
    }

    @GetMapping("/quotations")
    public Iterable<Quotation> getQuotations() {
        return quotationService.getQuotations();
    }

    @DeleteMapping("/quotations/delete/{id}")
    public void deleteQuotation(@PathVariable("id")Long id) {quotationService.deleteQuotation(id);}

    @PutMapping("/quotations/update/{id}")
    public Quotation updateQuotation(@PathVariable("id") Long id, @RequestBody Quotation quotation) {
        Quotation updated = quotationService.updateQuotation(id,quotation);
        return updated;
    }
}

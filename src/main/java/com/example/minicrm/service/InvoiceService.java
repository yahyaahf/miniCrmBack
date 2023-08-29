package com.example.minicrm.service;

import com.example.minicrm.model.Invoice;
import com.example.minicrm.model.Product;
import com.example.minicrm.model.PurchaseOrder;
import com.example.minicrm.model.Quotation;
import com.example.minicrm.repository.InvoiceRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final QuotationService quotationService;


    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, QuotationService quotationService) {
        this.invoiceRepository = invoiceRepository;
        this.quotationService = quotationService;
    }

    public Optional<Invoice> getInvoiceById(Long id){ return invoiceRepository.findById(id);}

    public Iterable<Invoice> getInvoices(){ return invoiceRepository.findAll();}

    public void deleteInvoice(Long id){ invoiceRepository.deleteById(id);}

    public Invoice saveInvoice(Invoice invoice){
        return invoiceRepository.save(invoice);
    }

    public Invoice saveInvoice(Long idQuotation){
        Quotation quotation=quotationService.getQuotationById(idQuotation).get();
        Invoice savedInvoice= new Invoice();

        savedInvoice.setDevis(quotation);
        savedInvoice.setClient(quotation.getClient());
        savedInvoice.setPrixTotal(quotation.getPrixTotal());
        savedInvoice.setDescription(quotation.getDescription());
        savedInvoice.setPrixTotal(quotation.getPrixTotal());




        invoiceRepository.save(savedInvoice);

        return savedInvoice;
    }

    public Invoice updateInvoice(Long id,Invoice invoice) {
        Optional<Invoice> oldInvoice = invoiceRepository.findById(id);
        if(oldInvoice.isPresent()){
            if(oldInvoice.get().getDateEcheance() != invoice.getDateEcheance()) oldInvoice.get().setDateEcheance(invoice.getDateEcheance());
            if(oldInvoice.get().getDescription() != invoice.getDescription()) oldInvoice.get().setDescription(invoice.getDescription());
            if(oldInvoice.get().getDateEmission() != invoice.getDateEmission()) oldInvoice.get().setDateEmission(invoice.getDateEmission());
            if(oldInvoice.get().getNumFacture() != invoice.getNumFacture()) oldInvoice.get().setNumFacture(invoice.getNumFacture());
            if(oldInvoice.get().getPrixTotal() != invoice.getPrixTotal()) oldInvoice.get().setPrixTotal(invoice.getPrixTotal());
            if(oldInvoice.get().getStatut() != invoice.getStatut()) oldInvoice.get().setStatut(invoice.getStatut());
            if(oldInvoice.get().getMontantPayé() != invoice.getMontantPayé()) oldInvoice.get().setMontantPayé(invoice.getMontantPayé());
            invoiceRepository.save(oldInvoice.get());
            return oldInvoice.get();

        }
        return null;
    }

    public Invoice sendInvoice(Long id) {
        Date date=new Date();
        Optional<Invoice> oldInvoice = invoiceRepository.findById(id);
        if(oldInvoice.isPresent()){
            oldInvoice.get().setStatut("envoyé");
            oldInvoice.get().setDateEmission(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 1);
            Date dateEcheance = calendar.getTime();
            oldInvoice.get().setDateEcheance(dateEcheance);
            invoiceRepository.save(oldInvoice.get());
            return oldInvoice.get();

        }
        return null;
    }
}

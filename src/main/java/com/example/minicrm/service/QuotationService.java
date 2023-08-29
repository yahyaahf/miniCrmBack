package com.example.minicrm.service;

import com.example.minicrm.model.*;
import com.example.minicrm.repository.InvoiceRepository;
import com.example.minicrm.repository.ProductRepository;
import com.example.minicrm.repository.QuotationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class QuotationService {


    private final QuotationRepository quotationRepository;

    private final ClientService clientService;

    private final ProductRepository productService;

    private final InvoiceRepository invoiceService;

    @Autowired
    public QuotationService( ProductRepository productService,ClientService clientService,QuotationRepository quotationRepository,InvoiceRepository invoiceService){
        this.clientService=clientService;
        this.productService=productService;
        this.quotationRepository=quotationRepository;
        this.invoiceService=invoiceService;
    }

    public Optional<Quotation> getQuotationById(Long id){ return quotationRepository.findById(id);}

    public Iterable<Quotation> getQuotations(){ return quotationRepository.findAll();}

    public void deleteQuotation(Long id){
        Quotation quotation=quotationRepository.findById(id).get();
        for (Invoice invoice : invoiceService.findAll()){
            if(quotation.getId() ==invoice.getDevis().getId()){
                invoice.setDevis(null);
                invoiceService.save(invoice);
            }

        }
        for (Product product : quotation.getListProduit()){
            Collection<Quotation> quotations= product.getDevis();
            quotations.remove(quotation);
            product.setDevis(quotations);
            productService.save(product);
        }
        quotationRepository.deleteById(id);

    }

    public Quotation saveQuotation(Quotation quotation){

        Quotation savedQuotation = quotationRepository.save(quotation);
        return savedQuotation;
    }

    public Quotation saveQuotation(Quotation quotation,long idClient){
        Client client= clientService.getClientById(idClient).get();
        String productsString=quotation.getProducts();
        Collection<Product> listProduit=quotation.getListProduit();
        for (String productIdStr : productsString.split(",")) {
            Long productId = Long.parseLong(productIdStr.trim());
            Product product = productService.findById(productId).orElse(null);
            if (product != null) {
                listProduit.add(product);
                Collection<Quotation> quotations=product.getDevis();
                product.getDevis().add(quotation);
                product.setDevis(quotations);
            }
        }

        quotation.setClient(client);
        quotation.setListProduit(listProduit);
        quotation.setDateDebut(new Date());
        quotation.setPrixTotal(Quotation.calculatePrix(quotation));
        Quotation savedQuotation = quotationRepository.save(quotation);
        return savedQuotation;
    }


    public Quotation updateQuotation(Long id,Quotation quotation) {
        Optional<Quotation> oldQuotation = quotationRepository.findById(id);
        if(oldQuotation.isPresent()){
            if(oldQuotation.get().getDateDebut() != quotation.getDateDebut()) oldQuotation.get().setDateDebut(quotation.getDateDebut());
            if(oldQuotation.get().getDateExpiration() != quotation.getDateExpiration()) oldQuotation.get().setDateExpiration(quotation.getDateExpiration());
            if(oldQuotation.get().getPrixTotal() != quotation.getPrixTotal()) oldQuotation.get().setPrixTotal(quotation.getPrixTotal());
            if(oldQuotation.get().getNumDevis() != quotation.getNumDevis()) oldQuotation.get().setNumDevis(quotation.getNumDevis());
            if(oldQuotation.get().getDescription() != quotation.getDescription()) oldQuotation.get().setDescription(quotation.getDescription());
            if(oldQuotation.get().getListProduit() != quotation.getListProduit()) oldQuotation.get().setListProduit(quotation.getListProduit());
            if(oldQuotation.get().getProducts() != quotation.getProducts()) oldQuotation.get().setProducts(quotation.getProducts());
            if(oldQuotation.get().getQuantities() != quotation.getQuantities()) oldQuotation.get().setQuantities(quotation.getQuantities());
            if(oldQuotation.get().getStatut() != quotation.getStatut()) oldQuotation.get().setStatut(quotation.getStatut());
            quotationRepository.save(oldQuotation.get());
            return oldQuotation.get();

        }
        return null;
    }
}

package com.example.minicrm.service;

import com.example.minicrm.model.Client;
import com.example.minicrm.model.Product;
import com.example.minicrm.model.PurchaseOrder;
import com.example.minicrm.model.Quotation;
import com.example.minicrm.repository.ProductRepository;
import com.example.minicrm.repository.PurchaseOrderRepository;
import com.example.minicrm.repository.QuotationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Data
@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final QuotationRepository quotationService;

    private final ProductRepository productService;

    @Autowired
    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, QuotationRepository quotationService, ProductRepository productService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.quotationService = quotationService;
        this.productService = productService;
    }

    public Optional<PurchaseOrder> getPurchaseOrderById(Long id){ return purchaseOrderRepository.findById(id);}

    public Iterable<PurchaseOrder> getPurchaseOrders(){ return purchaseOrderRepository.findAll();}

    public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder){





        return purchaseOrderRepository.save(purchaseOrder);
    }

    public void deletePurchaseOrder(Long id) {
        Optional<PurchaseOrder> purchaseOrderOptional = purchaseOrderRepository.findById(id);

        if (purchaseOrderOptional.isPresent()) {
            PurchaseOrder purchaseOrder = purchaseOrderOptional.get();

            for (Product product : purchaseOrder.getListProduit()) {
                if (product.getBonCommande().contains(purchaseOrder)) {
                    product.getBonCommande().remove(purchaseOrder);
                    productService.save(product); // Save the product after modifying the collection
                }
            }

            purchaseOrderRepository.deleteById(id);
        }
    }

    public PurchaseOrder savePurchaseOrder(Long idQuotation){
        Quotation quotation=quotationService.findById(idQuotation).get();
        PurchaseOrder savedPurchaseOrder= new PurchaseOrder();

        savedPurchaseOrder.setQuantities(quotation.getQuantities());
        savedPurchaseOrder.setTVA(quotation.getTVA());
        savedPurchaseOrder.setListProduit(List.copyOf(quotation.getListProduit()));

        savedPurchaseOrder.setDescription(quotation.getDescription());
        savedPurchaseOrder.setPrixTotal(quotation.getPrixTotal());
        savedPurchaseOrder.setDateExpiration(quotation.getDateExpiration());
        savedPurchaseOrder.setDateDebut(quotation.getDateDebut());
        savedPurchaseOrder.setClient(quotation.getClient());

        for (Product product : quotation.getListProduit()){
            Collection<PurchaseOrder> purchaseOrders=product.getBonCommande();
            purchaseOrders.add(savedPurchaseOrder);
            product.setBonCommande(purchaseOrders);

        }


        purchaseOrderRepository.save(savedPurchaseOrder);

        return savedPurchaseOrder;
    }

    public PurchaseOrder updatePurchaseOrder(Long id,PurchaseOrder purchaseOrder) {
        Optional<PurchaseOrder> oldPurchaseOrder = purchaseOrderRepository.findById(id);
        if(oldPurchaseOrder.isPresent()){
            if(oldPurchaseOrder.get().getClient() != purchaseOrder.getClient()) oldPurchaseOrder.get().setClient(purchaseOrder.getClient());
            if(oldPurchaseOrder.get().getDateDebut() != purchaseOrder.getDateDebut()) oldPurchaseOrder.get().setDateDebut(purchaseOrder.getDateDebut());
            if(oldPurchaseOrder.get().getDescription() != purchaseOrder.getDescription()) oldPurchaseOrder.get().setDescription(purchaseOrder.getDescription());
            if(oldPurchaseOrder.get().getDateExpiration() != purchaseOrder.getDateExpiration()) oldPurchaseOrder.get().setDateExpiration(purchaseOrder.getDateExpiration());
            if(oldPurchaseOrder.get().getListProduit() != purchaseOrder.getListProduit()) oldPurchaseOrder.get().setListProduit(purchaseOrder.getListProduit());
            if(oldPurchaseOrder.get().getQuantities() != purchaseOrder.getQuantities()) oldPurchaseOrder.get().setQuantities(purchaseOrder.getQuantities());
            if(oldPurchaseOrder.get().getPrixTotal() != purchaseOrder.getPrixTotal()) oldPurchaseOrder.get().setPrixTotal(purchaseOrder.getPrixTotal());
            if(!(oldPurchaseOrder.get().getTVA() != purchaseOrder.getTVA())) oldPurchaseOrder.get().setTVA(purchaseOrder.getTVA());
            if(oldPurchaseOrder.get().getNumBonCommande() != purchaseOrder.getNumBonCommande()) oldPurchaseOrder.get().setNumBonCommande(purchaseOrder.getNumBonCommande());
            purchaseOrderRepository.save(oldPurchaseOrder.get());
            return oldPurchaseOrder.get();

        }
        return null;
    }
}

package com.example.minicrm.service;


import com.example.minicrm.model.Product;
import com.example.minicrm.model.PurchaseOrder;
import com.example.minicrm.model.Quotation;
import com.example.minicrm.repository.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Data
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final PurchaseOrderService purchaseOrderService;


    private final QuotationService quotationService;



    @Autowired
    public ProductService(ProductRepository productRepository, PurchaseOrderService purchaseOrderService, QuotationService quotationService) {
        this.productRepository = productRepository;
        this.purchaseOrderService = purchaseOrderService;
        this.quotationService = quotationService;
    }

    public Optional<Product> getProductById(Long id){ return productRepository.findById(id);}

    public Iterable<Product> getProducts(){ return productRepository.findAll();}

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).get();
        for(Quotation quotation : product.getDevis()){
            Collection<Product> products=quotation.getListProduit();
            products.remove(product);
            quotation.setListProduit(products);
            quotationService.saveQuotation(quotation);
        }
        for(PurchaseOrder purchaseOrder : product.getBonCommande()){
            Collection<Product> products=purchaseOrder.getListProduit();
            products.remove(product);
            purchaseOrder.setListProduit(products);
            purchaseOrderService.savePurchaseOrder(purchaseOrder);
        }

        productRepository.deleteById(id);}

    public Product saveProduct(Product product){
        Product savedProduct= productRepository.save(product);
        return savedProduct;
    }

    public Product updateProduct(Long id,Product product) {
        Optional<Product> oldProduct = productRepository.findById(id);
        if(oldProduct.isPresent()){
            if(!(oldProduct.get().getIntitule().equalsIgnoreCase(product.getIntitule()))) oldProduct.get().setIntitule(product.getIntitule());
            if(!(oldProduct.get().getCategorie().equalsIgnoreCase(product.getCategorie()))) oldProduct.get().setCategorie(product.getCategorie());
            if(!(oldProduct.get().getDescription().equalsIgnoreCase(product.getDescription()))) oldProduct.get().setDescription(product.getDescription());
            productRepository.save(oldProduct.get());
            return oldProduct.get();

        }
        return null;
    }


    public Collection<Product> getProductByQuotation(Long id) {
        Collection<Product> listProduct=new ArrayList<>();
        Iterable<Product> allProducts=  productRepository.findAll();
        for (Product product : allProducts){
            for(Quotation quotation : product.getDevis())
            if(quotation.getId() == id) listProduct.add(product);
        }
        return listProduct;
    }

    public Collection<Product> getProductByPurchaseOrder(Long id) {
        Collection<Product> listProduct=new ArrayList<>();
        Iterable<Product> allProducts=  productRepository.findAll();
        for (Product product : allProducts){
            for(PurchaseOrder purchaseOrder : product.getBonCommande()) {
                if (purchaseOrder.getId() == id) listProduct.add(product);
            }
        }
        return listProduct;
    }
}

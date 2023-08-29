package com.example.minicrm.controller;

import com.example.minicrm.model.Product;
import com.example.minicrm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/products/add")
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable("id")Long id) {
        Optional<Product> product =productService.getProductById(id);
        if(product.isPresent()) {
            return product.get();
        } else {
            return null;
        }
    }

    @GetMapping("/products")
    public Iterable<Product> getProducts() {
        return productService.getProducts();
    }

    @DeleteMapping("/products/delete/{id}")
    public void deleteProduct(@PathVariable("id")Long id) {productService.deleteProduct(id);}

    @PutMapping("/products/update/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        Product updated = productService.updateProduct(id,product);
        return updated;
    }

    @GetMapping("/products/quotation/{id}")
    public Collection<Product> getProductsByQuotation(@PathVariable("id")Long id) {
        Collection<Product> listProduct =productService.getProductByQuotation(id);

        return listProduct;

    }

    @GetMapping("/products/purchaseOrders/{id}")
    public Collection<Product> getProductsByPurchaseOrder(@PathVariable("id")Long id) {
        Collection<Product> listProduct =productService.getProductByPurchaseOrder(id);

        return listProduct;

    }



}



package services;

import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductsService {

    public List<Product> getProducts() {

        List<Product> products = new ArrayList<>();

        products.add(new Product("iPhone", 650));
        products.add(new Product("XPS 13", 1300));
        products.add(new Product("RTX 3090", 3500));
        
        return products;
    }
    
}

package service;

import model.Product;

import java.util.List;

public interface PService {
     List<Product> selectAllProduct();
    void insertProduct(Product product);
    Product selectProduct(int id);
    void  deleteProduct(int id);
    void updateProduct(Product product);

}

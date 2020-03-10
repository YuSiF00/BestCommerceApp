package application.services;


import application.entities.Merchant;
import application.entities.Product;
import application.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void addProduct(Merchant merchant, Product product) {
        product.setMerchantId(merchant.getId());
        if (product.getInventory() > 5) productRepo.save(product);
        else throw new IllegalArgumentException("Too few products");
    }


    public List<Product> getProductByMerchantId(int merchantId) {
        return productRepo.findAllByMerchantId(merchantId);
    }



    public void deleteById(Merchant merchant, int id) {
        productRepo.deleteById(id);
    }
}

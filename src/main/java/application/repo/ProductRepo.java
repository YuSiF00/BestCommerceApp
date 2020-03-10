package application.repo;


import application.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query
    List<Product> findAllByMerchantId(int merchantId);

    @Override
    void deleteById(Integer integer);
}

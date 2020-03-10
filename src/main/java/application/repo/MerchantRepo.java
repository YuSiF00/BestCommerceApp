package application.repo;

import application.entities.Merchant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MerchantRepo extends CrudRepository<Merchant,String> {

    @Query
    Optional<Merchant> findMerchantByEmail(String email);

    @Query
    Optional<Merchant> findByToken(String token);

    @Override
    <S extends Merchant> S save(S s);

    @Override
    Iterable<Merchant> findAll();


}

package application.security;

import application.entities.Merchant;
import application.repo.MerchantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private final MerchantRepo merchantRepo;

    public MyUserDetailService(MerchantRepo merchantRepo) {
        this.merchantRepo = merchantRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Merchant> merchant = merchantRepo.findMerchantByEmail(email);
        if (merchant.isPresent()) return merchant.map(MyUserDetails::new).get();
        merchant.orElseThrow(IllegalAccessError::new);
        return null;
    }
}

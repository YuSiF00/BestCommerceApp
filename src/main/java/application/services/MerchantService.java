package application.services;

import application.entities.Merchant;
import application.repo.MerchantRepo;
import application.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantService {

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private JavaMailSender javaMailSender;

    private final MerchantRepo merchantRepo;

    public MerchantService(MerchantRepo merchantRepo) {
        this.merchantRepo = merchantRepo;

    }

    public void addMerchant(Merchant merchant) {
        if (checkPassword(merchant)) {
            merchant.setPassword(bCryptPasswordEncoder.encode(merchant.getPassword()));
            sendMail(merchant);
            merchantRepo.save(merchant);
        } else {
            throw new IllegalArgumentException("Password length less than 6 characters");
        }
    }

    private boolean checkPassword(Merchant merchant) {
        return merchant.getPassword().toCharArray().length >= 6 && !merchant.getPassword().contains(" ");
    }

    private void sendMail(Merchant merchant){
        String confirm_token = jwtTokenUtil.generateToken(merchant.getEmail());

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("springjava2019@gmail.com");
        msg.setTo(merchant.getEmail());
        msg.setSubject("Confirmation");
        msg.setText("Merchant type : " + merchant.getType() +
                " \nMerchant name : " + merchant.getName() +
                " \nMerchant owner_name : " + merchant.getOwner_name() +
                " \nMerchant address  : " + merchant.getAddress() +
                " \nMerchant phone_number : " + merchant.getPhone_number() +
                " \nMerchant email" + merchant.getEmail() +
                " \nMerchant role" + merchant.getRole() +
                " \n Please click this link for confirmation"+
                " \nhttp://localhost:8888/confirm/"+ confirm_token
        );

        merchant.setToken(confirm_token);
        javaMailSender.send(msg);

    }

    private void updateMerchant(Merchant merchant){
        merchant.setActive(true);
        merchant.setToken("");
        merchantRepo.save(merchant);
    }


    public void confirm(String token){
        System.out.println(findByToken(token).getEmail());
         updateMerchant(findByToken(token));
    }




    public Merchant findByEmail(String email) {
       Optional<Merchant> merchant = merchantRepo.findMerchantByEmail(email);
        merchant.orElseThrow(IllegalAccessError::new);
        return merchant.get();
    }

    public void updatePassword(Merchant merchant, String password) {

        merchant.setPassword(bCryptPasswordEncoder.encode(password));
        merchantRepo.save(merchant);
    }

    public void delete(Merchant merchant) {
        merchantRepo.delete(merchant);
    }

    private Merchant findByToken(String token){
        Optional<Merchant> op_merchant = merchantRepo.findByToken(token);
        op_merchant.orElseThrow(IllegalAccessError::new);
        return op_merchant.get();
    }

}

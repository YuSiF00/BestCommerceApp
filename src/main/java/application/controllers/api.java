package application.controllers;

import application.entities.Merchant;
import application.entities.Product;
import application.models.AuthenticationRequest;
import application.security.MyUserDetailService;
import application.services.MerchantService;
import application.services.ProductService;
import application.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@RestController
public class api {

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    private MyUserDetailService userDetailsService;


    @Autowired
    private JwtUtil jwtTokenUtil;

    private final ProductService productService;
    private final MerchantService merchantService;

    public api(ProductService productService, MerchantService merchantService) {
        this.productService = productService;
        this.merchantService = merchantService;
    }


    @PostMapping("/registration")
    public String Registration(@RequestBody Merchant merchant){
        merchantService.addMerchant(merchant);
        return "Registration was successful";
    }


    @PutMapping("/update/password")
    public String changePass(Authentication authentication, @RequestBody String password){
        Merchant merchant = merchantService.findByEmail(authentication.getName());
        merchantService.updatePassword(merchant,password);
        return "Password changed successfully";
    }

    @DeleteMapping("/delete")
    public String delete(Authentication authentication){
        Merchant merchant = merchantService.findByEmail(authentication.getName());
        merchantService.delete(merchant);
        return "Deleted successful";
    }

    @GetMapping("/products")
    public Collection<Product> getProducts(Authentication authentication){
        Merchant merchant = merchantService.findByEmail(authentication.getName());
        return  productService.getProductByMerchantId(merchant.getId());
    }

    @PostMapping("/product/add")
    public String addProduct(Authentication authentication, @RequestBody Product product){
        Merchant merchant = merchantService.findByEmail(authentication.getName());
        productService.addProduct(merchant,product);
        return "Added was successfully";
    }

    @DeleteMapping(value = "/product/delete/{id}")
    public String deleteProduct(Authentication authentication, @PathVariable int id) {
        Merchant merchant = merchantService.findByEmail(authentication.getName());
        productService.deleteById(merchant,id);
        return "Deleted successful";
    }

    @GetMapping(value = "/confirm/{token}")
    public String confirm(@PathVariable String token){
        merchantService.confirm(token);
        return "Successful";
    }



    @RequestMapping(method = RequestMethod.POST, value = "/authenticate")
    public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }


        return jwtTokenUtil.generateToken(authenticationRequest.getUsername());




    }










}

package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRespository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRespository cartItemRespository;
    public boolean  addtoCart(String userId, CartItemRequest request) {
        //look for product
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if(productOpt.isEmpty()){
            return false;
        }
        Product product = productOpt.get();
        if (product.getStockQuantity() < request.getQuantity()) {
            return false;
        }
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()){
            return false;
        }
        User user = userOpt.get();

        CartItem exisitngCartItem = cartItemRespository.findByUserAndProduct(user,product);
        if(exisitngCartItem != null){
           //update the quantity
            exisitngCartItem.setQuantity((int) (exisitngCartItem.getQuantity() + request.getQuantity()));
            exisitngCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(exisitngCartItem.getQuantity())));
            cartItemRespository.save(exisitngCartItem);
        }else {
           //crete new cart item
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setUser(user);
            cartItem.setQuantity(Math.toIntExact(request.getQuantity()));
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRespository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, String productId) {
        Optional<Product> productOpt = productRepository.findById(Long.valueOf(productId));
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if (productOpt.isPresent() && userOpt.isPresent()) {
            cartItemRespository.deleteByUserAndProduct(userOpt.get(),productOpt.get());
            return true;
        }
        return false;
    }

    public  List<CartItem> getCardItemByUserId(String userId) {
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()){
            return new ArrayList<>();
        }
        List<CartItem> list= cartItemRespository.findByUser(userOpt.get());
        return list;
    }
}

package com.app.ecom.controller;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.service.CartService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartIController {

    private final CartService cartService;
    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest cartItemRequest){
        if(!cartService.addtoCart(userId,cartItemRequest)){
            return ResponseEntity.badRequest().body("Product out of stock or user not found or product not found ");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable String productId
    ){
        boolean deleted = cartService.deleteItemFromCart(userId,productId);
        System.out.println(deleted);
        return deleted ? ResponseEntity.ok("item delted") : ResponseEntity.notFound().build();

    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItemsByUserId(@RequestHeader("X-User-ID") String userId){
        return ResponseEntity.ok(cartService.getCardItemByUserId(userId));
    }
}

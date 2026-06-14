package com.app.ecom.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
}

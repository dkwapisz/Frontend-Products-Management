package org.pk.lab3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.pk.lab3.utils.UpdatePair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class Product {

    private String id;
    private String name;
    private String description;
    private Integer quantity;
    private Float price;
    private Float weight;
    private Boolean available;
    private ProductCategory productCategory;
    private LocalDateTime dateAdded;
    private LocalDateTime dateLastUpdate;
    private List<ProductHistory> productHistory;

    public Product(String id, String name, Integer quantity, Float price, Boolean available) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.available = available;
    }

    @Data
    public static class ProductHistory {
        private LocalDateTime updateTimestamp;
        private Map<String, UpdatePair<Object>> changedFieldsMap;
    }

}

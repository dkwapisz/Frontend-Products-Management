package org.pk.lab3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

}

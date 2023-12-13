package org.pk.lab3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductSummary {

    private String id;
    private String name;
    private Integer quantity;
    private Float price;
    private Boolean available;

}

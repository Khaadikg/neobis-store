package com.neobis.onlinestore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name = "products")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable=false, updatable=false)
    private Long id;
    private String name;
    private String description;
    @Column(unique = true)
    private Integer barcode;
    private Double price;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, mappedBy = "product")
    private List<OrderDetails> orderDetails;
}

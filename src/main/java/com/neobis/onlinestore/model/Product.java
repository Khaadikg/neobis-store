package com.neobis.onlinestore.model;

import lombok.*;

import javax.persistence.*;

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
    private Integer barcode;
    private Double price;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
    private OrderDetails orderDetails;
}

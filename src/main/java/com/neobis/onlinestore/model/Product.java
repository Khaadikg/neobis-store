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
    private Integer price;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "order_id")
    private Order order;

}

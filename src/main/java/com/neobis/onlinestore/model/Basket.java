package com.neobis.onlinestore.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity @Table(name = "baskets")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable=false, updatable=false, unique = true)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private Customer customer;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "basket")
    private Set<Order> orders;
}

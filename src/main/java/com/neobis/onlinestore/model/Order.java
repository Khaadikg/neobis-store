package com.neobis.onlinestore.model;

import com.neobis.onlinestore.model.enums.OrderType;
import lombok.*;

import javax.persistence.*;
import java.util.List;
@Entity @Table(name = "orders")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable=false, updatable=false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    private String address;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "order")
    private List<OrderDetails> orderDetails;

}

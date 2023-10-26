package com.neobis.onlinestore.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "customers")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable=false, updatable=false, unique = true)
    private Long id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDate createdDate;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private Basket basket;
}

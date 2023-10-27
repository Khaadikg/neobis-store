package com.neobis.onlinestore.model;

import com.neobis.onlinestore.model.enums.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity @Table(name = "users")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable=false, updatable=false)
    private Long id;
    @Embedded
    private UserInfo info;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDate createdDate;
    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDate updatedDate;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "user")
    private Set<Order> orders;
}

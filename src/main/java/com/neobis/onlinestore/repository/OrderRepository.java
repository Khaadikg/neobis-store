package com.neobis.onlinestore.repository;

import com.neobis.onlinestore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.user.id = :key and o.orderDeclined = false")
    List<Order> findAllPersonalOrders(@Param("key") Long id);

    @Query("select o from Order o where o.user.id = :id and lower(o.user.username) like lower(:name)")
    Optional<Order> findPersonalOrderById(@Param("id") Long id, @Param("name") String name);
}

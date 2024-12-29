package com.foundationProject.bookstore.repository;

import com.foundationProject.bookstore.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("from Order o where o.user.userId=:userId")
    Optional<Order> findByUserId(Long userId);
}

package com.quitq.repository;

import com.quitq.dto.Statdto;
import com.quitq.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
SELECT o FROM Order o WHERE o.user.id = ?1
""")
    List<Order> findByUser_Id(Long userId);

    @Query("""
SELECT o FROM Order o JOIN o.orderItems oi WHERE oi.product.seller.id = ?1
""")
    List<Order> findBySellerId(Long sellerId);

@Query("""
select o from Order o
""")
    Page<Order> getAllOrderV2(Pageable pageable);
}
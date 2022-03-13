package com.inventorymanager.repository;

import com.inventorymanager.payload.enums.OrderStatus;
import com.inventorymanager.entity.ProductOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>, JpaSpecificationExecutor<ProductOrder> {

    Page<ProductOrder> findByProduct_id(Long productId, Pageable pageable);

    Page<ProductOrder> findByProduct_id(Long userId, Long productId, Pageable pageable);

    Page<ProductOrder> findBy(Long userid, Pageable pageable);

    List<ProductOrder> findByStatus(OrderStatus status);

    Optional<ProductOrder> findById(Long id);

    List<ProductOrder> findAllByProductIdAndStatus(Long productId, OrderStatus orderStatus);

}

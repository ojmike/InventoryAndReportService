package com.inventorymanager.service;

import com.inventorymanager.payload.request.CreateOrderRequest;
import com.inventorymanager.payload.response.ProductOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductOrderService {

    List<ProductOrderResponse> create(List<CreateOrderRequest> order);

    ProductOrderResponse get(Long id);

//    /*Page<ProductOrderModel> changeStatus(String id, OrderStatus status, Customer customer, Pageable pageable) throws InventoryManagerException;*/

    Page<ProductOrderResponse> get(Pageable pageable);

    Page<ProductOrderResponse> getByProductId(Long productId,Pageable pageable);


}

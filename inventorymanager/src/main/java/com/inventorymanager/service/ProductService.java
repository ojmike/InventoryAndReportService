package com.inventorymanager.service;

import com.inventorymanager.payload.request.CreateProductRequest;
import com.inventorymanager.payload.request.UpdateProductRequest;
import com.inventorymanager.payload.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse create(CreateProductRequest createProductRequest);

    ProductResponse update(Long id, UpdateProductRequest updateProductRequest);

    ProductResponse disable(Long id) ;

    ProductResponse enable(Long id) ;


    boolean delete(Long id);

    ProductResponse get(Long id);

    Page<ProductResponse> get(Pageable pageable);

}

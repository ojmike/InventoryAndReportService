package com.inventorymanager.controller;


import com.inventorymanager.payload.request.CreateOrderRequest;
import com.inventorymanager.payload.response.ProductOrderResponse;
import com.inventorymanager.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "v1/product-orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductOrderResponse> create(@Valid @RequestBody List<CreateOrderRequest> orders) {
        return productOrderService.create(orders);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductOrderResponse get(@PathVariable("id") Long id) {
        return productOrderService.get(id);
    }

    @GetMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductOrderResponse> getByProductId(@PathVariable("id") Long productId,
                                                  @PageableDefault(sort = "id", direction = DESC) Pageable pageable) {
        return productOrderService.getByProductId(productId,pageable);
    }



    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductOrderResponse> getAll(@PageableDefault(sort = "id", direction = DESC) Pageable pageable) {
        return productOrderService.get(pageable);
    }
}

package com.inventorymanager.controller;

import com.inventorymanager.payload.request.CreateProductRequest;
import com.inventorymanager.payload.request.UpdateProductRequest;
import com.inventorymanager.payload.response.ProductResponse;
import com.inventorymanager.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse create(@Valid @RequestBody CreateProductRequest createProductRequest) {
        return productService.create(createProductRequest);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse update(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateProductRequest updateProductRequest){
        return productService.update(id, updateProductRequest);
    }


    @PostMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse disable(@PathVariable("id") Long id)  {
        return productService.disable(id);
    }


    @PostMapping("/{id}/enable")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse enable(@PathVariable("id") Long id)  {
        return productService.enable(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse get(@PathVariable("id") Long id) {
        return productService.get(id);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponse> getAll(@PageableDefault(sort = "id", direction = DESC) Pageable pageable){
        return productService.get(pageable);
    }

}

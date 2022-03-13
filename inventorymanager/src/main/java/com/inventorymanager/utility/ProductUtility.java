package com.inventorymanager.utility;

import com.inventorymanager.entity.Product;
import com.inventorymanager.payload.request.CreateProductRequest;
import com.inventorymanager.payload.request.UpdateProductRequest;
import com.inventorymanager.payload.response.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ProductUtility {

    public ProductResponse toProductResponse(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setDisabled(product.getDisabled());
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setProductQuantity(product.getQuantity());

        return productResponse;
    }

    public Product toProduct(CreateProductRequest createProductRequest){
        Product product = new Product();
        product.setPrice(createProductRequest.getProductPrice());
        product.setName(createProductRequest.getProductName());
        product.setDescription(createProductRequest.getProductDescription());
        product.setQuantity(createProductRequest.getProductQuantity());

        return product;
    }

    public Product upDateProduct(Product product,UpdateProductRequest updateProductRequest) {

        if(!ObjectUtils.isEmpty(updateProductRequest.getProductDescription())){
            product.setDescription(updateProductRequest.getProductDescription());
        }
        if(!ObjectUtils.isEmpty(updateProductRequest.getProductName())){
            product.setName(updateProductRequest.getProductName());
        }
        if(!ObjectUtils.isEmpty(updateProductRequest.getProductPrice())){
            product.setPrice(updateProductRequest.getProductPrice());
        }
        if(!ObjectUtils.isEmpty(updateProductRequest.getProductQuantity())){
            product.setQuantity(product.getQuantity() + updateProductRequest.getProductQuantity());
        }
        return product;
    }
}

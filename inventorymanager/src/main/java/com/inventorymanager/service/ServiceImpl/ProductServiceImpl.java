package com.inventorymanager.service.ServiceImpl;


import com.inventorymanager.exception.ApiBadRequestException;
import com.inventorymanager.exception.ApiResourceNotFoundException;
import com.inventorymanager.entity.Product;
import com.inventorymanager.payload.request.CreateProductRequest;
import com.inventorymanager.payload.request.UpdateProductRequest;
import com.inventorymanager.payload.response.ProductResponse;
import com.inventorymanager.repository.ProductRepository;
import com.inventorymanager.service.ProductService;
import com.inventorymanager.utility.ProductUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;



@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductUtility productUtility;

    @Override
    public ProductResponse create(CreateProductRequest createProductRequest) {
        if (productRepository.findByName(createProductRequest.getProductName()).isPresent()) {
            throw new ApiBadRequestException("Product with name exists");
        }
        Product product = productUtility.toProduct(createProductRequest);
         product = productRepository.save(product);

        return productUtility.toProductResponse(product);
    }

    @Override
    public ProductResponse update(Long id, UpdateProductRequest updateProductRequest)  {
        if(!ObjectUtils.isEmpty(updateProductRequest)){
            if (!id.equals(updateProductRequest.getId())) {
                throw new ApiBadRequestException("Id not matching with Product id");
            }

            Product product = getProduct(updateProductRequest.getId());

            Optional<Product> productWithSameName = productRepository.findByNameAndIdIsNot(
                    product.getName(),
                    product.getId());

            if (productWithSameName.isPresent()) {
                throw new ApiBadRequestException("Product name exists");
            }

            productUtility.upDateProduct(product,updateProductRequest);
            product = productRepository.save(product);
            return productUtility.toProductResponse(product);
        }else {
            throw new ApiBadRequestException("Empty payload sent");
        }

    }

    @Override
    public ProductResponse disable(Long id)  {
        Product product = getProduct(id);

        product.setDisabled(true);
        product = productRepository.save(product);
        return productUtility.toProductResponse(product);

    }

    @Override
    public ProductResponse enable(Long id) {
        Product product = getProduct(id);

        product.setDisabled(false);

        product = productRepository.save(product);
        return productUtility.toProductResponse(product);
    }

    @Override
    public boolean delete(Long id) {
        Product product = getProduct(id);

        product.setDeleted(true);

        product = productRepository.save(product);
        return true;
    }

    @Override
    public ProductResponse get(Long id){
        return productUtility.toProductResponse(getProduct(id));
    }

    @Override
    public Page<ProductResponse> get(Pageable pageable){
        try {
            Page<Product> products = productRepository.findProductsByDisabledFalse(pageable);

            return products.map(productUtility::toProductResponse);
        } catch (Exception e) {
            throw new ApiResourceNotFoundException("Error getting all products");
        }
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ApiResourceNotFoundException("Product does not exist"));
    }

}

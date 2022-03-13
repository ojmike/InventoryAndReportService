package com.inventorymanager.utility;

import com.inventorymanager.entity.Product;
import com.inventorymanager.entity.ProductOrder;
import com.inventorymanager.payload.request.CreateOrderRequest;
import com.inventorymanager.payload.response.ProductOrderResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductOrderUtility {

    public ProductOrder toProductOrder(CreateOrderRequest order, Product product){
        ProductOrder productOrder = new ProductOrder();
        productOrder.setAddress(order.getAddress());
        productOrder.setCustomerName(order.getCustomerName());
        productOrder.setProductPrice(product.getPrice());
        productOrder.setProductName(product.getName());
        productOrder.setProductDescription(product.getDescription());
        productOrder.setProduct(product);
        productOrder.setQuantity(order.getQuantity());
        productOrder.setSellingPrice(product.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
        productOrder.setPhoneNumber(order.getPhoneNumber());
        return productOrder;
    }

    public ProductOrderResponse toProductOrderResponse(ProductOrder productOrder){
        ProductOrderResponse productOrderResponse = new ProductOrderResponse();
        productOrderResponse.setId(productOrder.getId());
        productOrderResponse.setProductName(productOrder.getProductName());
        productOrderResponse.setProductDescription(productOrder.getProductDescription());
        productOrderResponse.setProductPrice(productOrder.getProductPrice());
        productOrderResponse.setQuantity(productOrder.getQuantity());
        productOrderResponse.setCustomerName(productOrder.getCustomerName());
        productOrderResponse.setCustomerAddress(productOrder.getAddress());
        productOrderResponse.setPhoneNumber(productOrder.getPhoneNumber());
        productOrderResponse.setProductSellingPrice(productOrder.getSellingPrice());
        return productOrderResponse;
    }

}

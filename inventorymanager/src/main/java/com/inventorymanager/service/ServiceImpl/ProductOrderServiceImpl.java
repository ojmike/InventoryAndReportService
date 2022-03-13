package com.inventorymanager.service.ServiceImpl;

import com.inventorymanager.exception.ApiBadRequestException;
import com.inventorymanager.exception.ApiResourceNotFoundException;
import com.inventorymanager.payload.enums.OrderStatus;
import com.inventorymanager.config.kafka.SendNotification;
import com.inventorymanager.entity.Product;
import com.inventorymanager.entity.ProductOrder;
import com.inventorymanager.payload.request.CreateOrderRequest;
import com.inventorymanager.payload.response.OrderDetailsResponse;
import com.inventorymanager.payload.response.ProductOrderResponse;
import com.inventorymanager.repository.ProductOrderRepository;
import com.inventorymanager.repository.ProductRepository;
import com.inventorymanager.service.ProductOrderService;

import com.inventorymanager.utility.ProductOrderUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductOrderServiceImpl implements ProductOrderService {

    private final ProductOrderRepository repository;
    private final ProductRepository productRepository;
    private final ProductOrderUtility productOrderUtility;
    private final SendNotification sendNotification;


    @Override
    @Transactional
    public List<ProductOrderResponse> create(List<CreateOrderRequest> orders)  {

        List<Long> products = extractProductIds(orders);

        Map<Long, Product> productMap = convertToMap(productRepository.findByIdIn(products));

        ensureAllProductExists(products, productMap);

        for (CreateOrderRequest order : orders) {
            Product product = productRepository.findById(order.getProductId()).get();
            if (product.getQuantity() < order.getQuantity())
                throw new ApiBadRequestException("Product with " + order.getQuantity() + " has only " + product.getQuantity() + " remaining quantity");
        }


        List<ProductOrder> productOrderList = convertToEntities(orders, productMap);

        var savedProductOrders = repository.saveAll(productOrderList);

        setProductQuantity(orders);

        BigDecimal totalAmount = productOrderList.stream()
                .map(ProductOrder::getSellingPrice)
                .reduce(new BigDecimal(0), BigDecimal::add);



        List<ProductOrderResponse> productOrderResponses = savedProductOrders.stream()
                .map(pOrder -> {
                    return productOrderUtility.toProductOrderResponse(pOrder);
                })
                .collect(toList());

        OrderDetailsResponse orderDetailsResponse = getOrderDetailsResponse(totalAmount, productOrderResponses);

        sendNotification.sendEmailMessages(orderDetailsResponse);

        return productOrderResponses;
    }

    private void setProductQuantity(List<CreateOrderRequest> orders) {
        for (CreateOrderRequest order : orders) {
            Product product = productRepository.findById(order.getProductId()).get();
            product.setQuantity(product.getQuantity() - order.getQuantity());
        }
    }

    private OrderDetailsResponse getOrderDetailsResponse(BigDecimal totalAmount, List<ProductOrderResponse> productOrderResponses) {
        OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
        orderDetailsResponse.setProductOrderResponseList(productOrderResponses);
        orderDetailsResponse.setStatus(OrderStatus.PENDING.name());
        orderDetailsResponse.setTotalPrice(totalAmount);
        return orderDetailsResponse;
    }

    @Override
    public ProductOrderResponse get(Long id) {
        ProductOrder productOder;
        productOder = getOrder(id);

        return productOrderUtility.toProductOrderResponse(productOder);
    }


    @Override
    public Page<ProductOrderResponse> get(Pageable pageable) {
        try {
            Page<ProductOrder> productOrders;
            productOrders = repository.findAll(pageable);
            return productOrders.map(productOrder -> productOrderUtility.toProductOrderResponse(productOrder));
        } catch (Exception e) {
            throw new ApiResourceNotFoundException("Error getting all orders");
        }
    }

    @Override
    public Page<ProductOrderResponse> getByProductId(Long productId, Pageable pageable) {
        try {
            Page<ProductOrder> productOrders;

                productOrders = repository.findByProduct_id(productId, pageable);

            return productOrders.map(productOrder -> productOrderUtility.toProductOrderResponse(productOrder));
        } catch (Exception e) {
            throw new ApiResourceNotFoundException(format("Error getting orders by productId %d", productId));
        }
    }



    private List<ProductOrder> convertToEntities(List<CreateOrderRequest> orders, Map<Long, Product> productMap) {
        List<ProductOrder> productOrderList = new ArrayList<>();
        for (CreateOrderRequest order : orders) {
            var product = productMap.get(order.getProductId());

            productOrderList.add(productOrderUtility.toProductOrder(order, product));

        }
        return productOrderList;
    }


    private List<Long> extractProductIds(List<CreateOrderRequest> orders) {
        return orders.parallelStream()
                .map(CreateOrderRequest::getProductId)
                .distinct()
                .collect(toList());
    }

    private void ensureAllProductExists(List<Long> products, Map<Long, Product> productMap) {
        for (Long pid : products) {
            if (!productMap.containsKey(pid)) {
                throw new ApiResourceNotFoundException(format("Product %d does not exist", pid));
            }
            if(productMap.containsKey(pid)){

            }
        }
    }

    private Map<Long, Product> convertToMap(Set<Product> products) {
        return products.stream()
                .collect(toMap(Product::getId, Function.identity()));
    }


    public ProductOrder getOrder(Long id)  {
        return repository.findById(id)
                .orElseThrow(() -> new ApiBadRequestException("Order does not exist"));
    }

}

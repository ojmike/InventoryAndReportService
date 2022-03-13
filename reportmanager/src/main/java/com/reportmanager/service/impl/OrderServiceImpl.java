package com.reportmanager.service.impl;

import com.reportmanager.entity.OrderDetails;
import com.reportmanager.entity.OrderSummary;
import com.reportmanager.entity.ReportProjection;
import com.reportmanager.payload.request.OrderDetailsRequest;
import com.reportmanager.payload.request.OrderFilter;
import com.reportmanager.repository.OrderDetailsRepository;
import com.reportmanager.repository.OrderSummaryRepository;
import com.reportmanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderSummaryRepository orderSummaryRepository;

    @Override
    public void saveOrder(OrderDetailsRequest orderDetailsRequest){
        try{
            OrderSummary orderSummary = new OrderSummary();
            orderSummary.setStatus(orderDetailsRequest.getStatus());
            orderSummary.setTotalPrice(orderDetailsRequest.getTotalPrice());
            orderSummary.setTotalOrder(orderDetailsRequest.getProductOrderResponseList().size());
            orderSummary = orderSummaryRepository.save(orderSummary);

            OrderSummary finalOrderSummary = orderSummary;
            orderDetailsRequest.getProductOrderResponseList().forEach(productOrderRequest -> {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setCustomerName(productOrderRequest.getCustomerName());
                orderDetails.setCustomerAddress(productOrderRequest.getCustomerAddress());
                orderDetails.setQuantity(productOrderRequest.getQuantity());
                orderDetails.setPhoneNumber(productOrderRequest.getPhoneNumber());
                orderDetails.setProductPrice(productOrderRequest.getProductPrice());
                orderDetails.setProductDescription(productOrderRequest.getProductDescription());
                orderDetails.setProductName(productOrderRequest.getProductName());
                orderDetails.setProductSellingPrice(productOrderRequest.getProductSellingPrice());
                orderDetails.setOrderSummary(finalOrderSummary);
                orderDetails.setCreatedAt(productOrderRequest.getCreatedAt());
                orderDetailsRepository.save(orderDetails);
            });
        }catch (Exception err){

        }
    }


    public List<ReportProjection> fetchReport(OrderFilter orderFilter){
        if(orderFilter.getFilterDate() != null){
            return orderSummaryRepository.getReportDetailsFilter(orderFilter.getFilterDate());
        }else{
            return  orderSummaryRepository.getReportDetails();
        }
    }




}

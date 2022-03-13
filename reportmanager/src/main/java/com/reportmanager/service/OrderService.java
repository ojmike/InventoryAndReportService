package com.reportmanager.service;

import com.reportmanager.entity.ReportProjection;
import com.reportmanager.payload.request.OrderDetailsRequest;
import com.reportmanager.payload.request.OrderFilter;

import java.util.List;

public interface OrderService {

    public void saveOrder(OrderDetailsRequest orderDetailsRequest);

    public List<ReportProjection> fetchReport(OrderFilter orderFilter);
}

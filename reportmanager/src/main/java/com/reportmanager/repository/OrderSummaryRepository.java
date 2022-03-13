package com.reportmanager.repository;

import com.reportmanager.entity.OrderSummary;
import com.reportmanager.entity.ReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderSummaryRepository extends JpaRepository<OrderSummary, Long> {

    @Query(value = "SELECT o.created_at AS createdAt, SUM(o.total_order) AS totalOrder, SUM(o.total_price) AS totalPrice FROM order_summary AS o where (o.created_at = :channelId  OR COALESCE ( :channelId,null) IS NULL) GROUP BY o.created_at ORDER BY o.created_at ASC",nativeQuery = true)
    List<ReportProjection> getReportDetailsFilter(@Param("channelId")LocalDateTime orderDate);

    @Query(value = "SELECT o.created_at AS createdAt, SUM(o.total_order) AS totalOrder, SUM(o.total_price) AS totalPrice FROM order_summary AS o GROUP BY o.created_at ORDER BY o.created_at ASC",nativeQuery = true)
    List<ReportProjection> getReportDetails();

}

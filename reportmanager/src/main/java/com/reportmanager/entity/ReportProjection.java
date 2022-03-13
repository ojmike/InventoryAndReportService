package com.reportmanager.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public interface ReportProjection {

    LocalDateTime getCreatedAt();

    BigInteger getTotalOrder();

    BigDecimal getTotalPrice();
}

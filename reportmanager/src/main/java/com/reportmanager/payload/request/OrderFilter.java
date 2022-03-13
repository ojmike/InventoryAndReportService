package com.reportmanager.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderFilter {
    private LocalDateTime filterDate;
}

package com.errandGo.backend.dto;

import java.math.BigDecimal;

public record NearbyTaskView(Long id, String title, BigDecimal amount, double distanceKm,
                             double approxLatitude, double approxLongitude) {}
package com.acme.parkshare.platform.reportanalytics.domain.model.valueobjects;

import java.math.BigDecimal;

/** Income earned on one day of the current week ({@code day} is mon..sun). */
public record DayIncome(String day, BigDecimal amount) {}

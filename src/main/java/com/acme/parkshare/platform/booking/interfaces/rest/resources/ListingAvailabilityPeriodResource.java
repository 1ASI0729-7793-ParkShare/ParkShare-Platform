package com.acme.parkshare.platform.booking.interfaces.rest.resources;

/** A weekly availability window of a parking listing. */
public record ListingAvailabilityPeriodResource(String dayOfWeek, String startTime, String endTime) {}

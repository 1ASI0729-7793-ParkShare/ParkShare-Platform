package com.acme.parkshare.platform.payments.domain.model.aggregates;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Money summary of a parking owner: what can be withdrawn, what is on its way and what was earned. */
public class OwnerPayout {

    public static final BigDecimal DEFAULT_COMMISSION_RATE = new BigDecimal("10");

    private final Long id;
    private final Long ownerId;
    private BigDecimal availableBalance;
    private BigDecimal completedAmount;
    private BigDecimal inTransitAmount;
    private String linkedAccount;
    private BigDecimal commissionRate;

    public OwnerPayout(Long id, Long ownerId, BigDecimal availableBalance, BigDecimal completedAmount,
                       BigDecimal inTransitAmount, String linkedAccount, BigDecimal commissionRate) {
        this.id = id;
        this.ownerId = ownerId;
        this.availableBalance = availableBalance;
        this.completedAmount = completedAmount;
        this.inTransitAmount = inTransitAmount;
        this.linkedAccount = linkedAccount;
        this.commissionRate = commissionRate;
    }

    public static OwnerPayout openFor(Long ownerId) {
        return new OwnerPayout(null, ownerId, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                null, DEFAULT_COMMISSION_RATE);
    }

    /** Amount the owner receives for a gross amount, after the platform commission. */
    public BigDecimal netOf(BigDecimal gross) {
        var factor = BigDecimal.valueOf(100).subtract(commissionRate).divide(BigDecimal.valueOf(100));
        return gross.multiply(factor).setScale(2, RoundingMode.HALF_UP);
    }

    public void credit(BigDecimal netAmount) {
        availableBalance = availableBalance.add(netAmount);
        completedAmount = completedAmount.add(netAmount);
    }

    /** Moves the whole available balance to the bank transfer in transit. */
    public void requestTransfer() {
        if (availableBalance.signum() <= 0) {
            throw new IllegalStateException("There is no available balance to transfer");
        }
        inTransitAmount = inTransitAmount.add(availableBalance);
        availableBalance = BigDecimal.ZERO;
    }

    public Long getId() { return id; }
    public Long getOwnerId() { return ownerId; }
    public BigDecimal getAvailableBalance() { return availableBalance; }
    public BigDecimal getCompletedAmount() { return completedAmount; }
    public BigDecimal getInTransitAmount() { return inTransitAmount; }
    public String getLinkedAccount() { return linkedAccount; }
    public BigDecimal getCommissionRate() { return commissionRate; }
}

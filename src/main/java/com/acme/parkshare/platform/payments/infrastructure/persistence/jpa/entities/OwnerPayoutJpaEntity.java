package com.acme.parkshare.platform.payments.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "owner_payout")
public class OwnerPayoutJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false, unique = true)
    private Long ownerId;

    @Column(name = "available_balance", nullable = false, precision = 12, scale = 2)
    private BigDecimal availableBalance;

    @Column(name = "completed_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal completedAmount;

    @Column(name = "in_transit_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal inTransitAmount;

    @Column(name = "linked_account", length = 100)
    private String linkedAccount;

    @Column(name = "commission_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal commissionRate;

    protected OwnerPayoutJpaEntity() {}

    public OwnerPayoutJpaEntity(Long id, Long ownerId, BigDecimal availableBalance, BigDecimal completedAmount,
                                BigDecimal inTransitAmount, String linkedAccount, BigDecimal commissionRate) {
        this.id = id;
        this.ownerId = ownerId;
        this.availableBalance = availableBalance;
        this.completedAmount = completedAmount;
        this.inTransitAmount = inTransitAmount;
        this.linkedAccount = linkedAccount;
        this.commissionRate = commissionRate;
    }

    public Long getId() { return id; }
    public Long getOwnerId() { return ownerId; }
    public BigDecimal getAvailableBalance() { return availableBalance; }
    public BigDecimal getCompletedAmount() { return completedAmount; }
    public BigDecimal getInTransitAmount() { return inTransitAmount; }
    public String getLinkedAccount() { return linkedAccount; }
    public BigDecimal getCommissionRate() { return commissionRate; }
}

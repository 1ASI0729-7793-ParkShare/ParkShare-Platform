package com.acme.parkshare.platform.payments.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payment_transaction")
public class PaymentTransactionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "reservation_id", nullable = false, unique = true)
    private Long reservationId;

    @Column(name = "driver_name", nullable = false, length = 200)
    private String driverName;

    @Column(name = "garage_name", nullable = false, length = 200)
    private String garageName;

    @Column(name = "transaction_date", nullable = false)
    private Instant date;

    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;

    @Column(name = "amount_received", nullable = false, precision = 12, scale = 2)
    private BigDecimal amountReceived;

    protected PaymentTransactionJpaEntity() {}

    public PaymentTransactionJpaEntity(Long id, Long ownerId, Long reservationId, String driverName,
                                       String garageName, Instant date, int durationMinutes,
                                       BigDecimal amountReceived) {
        this.id = id;
        this.ownerId = ownerId;
        this.reservationId = reservationId;
        this.driverName = driverName;
        this.garageName = garageName;
        this.date = date;
        this.durationMinutes = durationMinutes;
        this.amountReceived = amountReceived;
    }

    public Long getId() { return id; }
    public Long getOwnerId() { return ownerId; }
    public Long getReservationId() { return reservationId; }
    public String getDriverName() { return driverName; }
    public String getGarageName() { return garageName; }
    public Instant getDate() { return date; }
    public int getDurationMinutes() { return durationMinutes; }
    public BigDecimal getAmountReceived() { return amountReceived; }
}

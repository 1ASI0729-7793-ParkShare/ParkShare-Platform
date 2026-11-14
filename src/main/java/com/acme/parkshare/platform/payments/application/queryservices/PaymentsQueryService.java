package com.acme.parkshare.platform.payments.application.queryservices;

import com.acme.parkshare.platform.payments.application.queries.GetOwnerPayoutByOwnerIdQuery;
import com.acme.parkshare.platform.payments.application.queries.GetTransactionsByOwnerIdQuery;
import com.acme.parkshare.platform.payments.domain.model.aggregates.OwnerPayout;
import com.acme.parkshare.platform.payments.domain.model.aggregates.PaymentTransaction;

import java.util.List;

public interface PaymentsQueryService {
    OwnerPayout handle(GetOwnerPayoutByOwnerIdQuery query);

    List<PaymentTransaction> handle(GetTransactionsByOwnerIdQuery query);
}

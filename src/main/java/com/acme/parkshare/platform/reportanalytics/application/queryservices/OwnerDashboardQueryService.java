package com.acme.parkshare.platform.reportanalytics.application.queryservices;

import com.acme.parkshare.platform.reportanalytics.application.queries.GetOwnerDashboardQuery;
import com.acme.parkshare.platform.reportanalytics.domain.model.valueobjects.OwnerDashboard;

public interface OwnerDashboardQueryService {
    OwnerDashboard handle(GetOwnerDashboardQuery query);
}

package com.acme.parkshare.platform.incidentreporting.interfaces.rest.transform;
import com.acme.parkshare.platform.incidentreporting.domain.model.aggregates.Incident;
import com.acme.parkshare.platform.incidentreporting.interfaces.rest.resources.IncidentResource;
import com.acme.parkshare.platform.shared.interfaces.rest.transform.JsonText;

public class IncidentResourceMapper {
    private IncidentResourceMapper() {}
    public static IncidentResource toResource(Incident i) {
        return new IncidentResource(i.getId(), i.getReporterId(), i.getReporterName(),
                i.getParkingListingId(), i.getOwnerId(), i.getSpaceName(), i.getSpaceAddress(),
                JsonText.toStrings(i.getIncidentTypes()), i.getDescription(),
                JsonText.toStrings(i.getPhotos()), i.getStatus(),
                i.getCreatedAt(), i.getResolvedAt(), i.getUpdatedAt());
    }
}

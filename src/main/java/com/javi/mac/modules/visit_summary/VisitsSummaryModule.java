package com.javi.mac.modules.visit_summary;

import com.javi.mac.MatomoClient;
import com.javi.mac.MatomoRequest;
import com.javi.mac.enums.PeriodEnum;
import com.javi.mac.generics.Result;
import tools.jackson.core.type.TypeReference;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Módulo de servicio para la API Matomo VisitsSummary,
 * que encapsula la lógica de llamada a los endpoints.
 */
public final class VisitsSummaryModule {

    private final MatomoClient client;
    private static final String BASE_METHOD = "VisitsSummary.";

    public VisitsSummaryModule(MatomoClient client) {
        this.client = client;
    }

    public Result<VisitSummary.Get, String> get(Integer siteId, PeriodEnum period, List<LocalDate> date, Map<String, String> extraParams) {
        MatomoRequest request = new MatomoRequest(BASE_METHOD + "get", siteId, period, date, extraParams);
        return client.execute(request, new TypeReference<>() {
        });
    }

    public Result<VisitSummary.GetVisits, String> getVisits(Integer siteId, PeriodEnum period, List<LocalDate> date, Map<String, String> extraParams) {
        MatomoRequest request = new MatomoRequest(BASE_METHOD + "getVisits", siteId, period, date, extraParams);
        return client.execute(request, new TypeReference<>() {
        });
    }
}
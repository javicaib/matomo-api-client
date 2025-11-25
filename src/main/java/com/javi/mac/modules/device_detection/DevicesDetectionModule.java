package com.javi.mac.modules.device_detection;

import com.javi.mac.MatomoClient;
import com.javi.mac.MatomoRequest;
import com.javi.mac.enums.PeriodEnum;
import com.javi.mac.generics.Result;
import tools.jackson.core.type.TypeReference;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DevicesDetectionModule {
    private final MatomoClient client;
    private static final String BASE_METHOD = "DevicesDetection.";

    public DevicesDetectionModule(MatomoClient client) {
        this.client = client;
    }

    public Result<List<DevicesDetection.GetBrowsers>, String> getBrowsers(Integer siteId, PeriodEnum period, List<LocalDate> date, Map<String, String> extraParams) {
        MatomoRequest request = new MatomoRequest(BASE_METHOD + "getBrowsers", siteId, period, date, extraParams);
        return client.execute(request, new TypeReference<>() {
        });
    }
}

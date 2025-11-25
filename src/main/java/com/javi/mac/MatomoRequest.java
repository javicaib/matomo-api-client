package com.javi.mac;

import com.javi.mac.enums.PeriodEnum;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MatomoRequest {

    private static final DateTimeFormatter MATOMO_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final String method;
    private final Integer idSite;
    private final String period;
    private final String date;
    private final Map<String, String> extraParams;

    public MatomoRequest(
            String method,
            Integer idSite,
            PeriodEnum period,
            List<LocalDate> dates,
            Map<String, String> extraParams
    ) {
        if (dates == null || dates.isEmpty()) {
            throw new IllegalArgumentException("Dates list cannot be empty");
        }
        if (dates.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Dates list cannot contain null");
        }
        if (period == PeriodEnum.RANGE && dates.size() < 2) {
            throw new IllegalArgumentException("Range period requires at least two dates");
        }

        String dateString;
        var sortedDates = new ArrayList<>(dates);
        sortedDates.sort(Comparator.naturalOrder());

        if (period == PeriodEnum.RANGE && sortedDates.size() >= 2) {
            // Caso RANGE: Necesita al menos dos fechas (inicio y fin) y que sea un rango
            LocalDate startDate = sortedDates.get(0);
            LocalDate endDate = sortedDates.get(dates.size() - 1);
            // Formato: YYYY-MM-DD,YYYY-MM-DD
            dateString = startDate.format(MATOMO_DATE_FORMATTER) + "," + endDate.format(MATOMO_DATE_FORMATTER);

        } else {
            // Caso No-RANGE (DAY, WEEK, MONTH, YEAR): Necesita toma la primera una fecha
            LocalDate firstDate = sortedDates.get(0);
            dateString = firstDate.format(MATOMO_DATE_FORMATTER);
        }

        this.method = method;
        this.idSite = idSite;
        this.period = period.getApiValue();
        this.date = dateString;
        this.extraParams = extraParams != null ? extraParams : Collections.emptyMap();
    }

    public Map<String, String> toQueryParams() {
        Map<String, String> map = new HashMap<>();
        map.put("method", method);
        map.put("idSite", idSite.toString());
        map.put("period", period);
        map.put("date", date);
        map.putAll(extraParams);

        return map;
    }

    public String getPeriod() {
        return period;
    }

    public String getDate() {
        return date;
    }

    public Map<String, String> getExtraParams() {
        return extraParams;
    }
}
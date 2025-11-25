package com.javi.mac.enums;


public enum PeriodEnum {

    DAY("day", "Returns data for a given day."),

    WEEK("week", "Returns data for the week that contains the specified 'date'."),

    MONTH("month", "Returns data for the month that contains the specified 'date'."),

    YEAR("year", "Returns data for the year that contains the specified 'date'."),

    RANGE("range", "Returns data for the specified 'date' range (e.g., &date=start,end).");

    private final String apiValue;
    private final String description;


    PeriodEnum(String apiValue, String description) {
        this.apiValue = apiValue;
        this.description = description;
    }

    public String getApiValue() {
        return apiValue;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return apiValue;
    }
}
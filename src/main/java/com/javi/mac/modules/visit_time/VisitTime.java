package com.javi.mac.modules.visit_time;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javi.mac.modules.common.Goals;

import java.util.Map;

public sealed interface VisitTime {

    record HourlyVisitData(
            @JsonProperty("label")
            String hourLabel, // La hora del servidor (0-23)

            @JsonProperty("nb_uniq_visitors")
            Integer nbUniqueVisitors,

            @JsonProperty("nb_visits")
            Integer nbVisits,

            @JsonProperty("nb_actions")
            Integer nbActions,

            @JsonProperty("nb_users")
            Integer nbUsers,

            @JsonProperty("max_actions")
            Integer maxActions,

            @JsonProperty("sum_visit_length")
            Long sumVisitLength,

            @JsonProperty("bounce_count")
            Float bounceCount,

            @JsonProperty("nb_visits_converted")
            Integer nbVisitsConverted,

            @JsonProperty("goals")
            Map<String, Goals.GoalData> goals,

            @JsonProperty("nb_conversions")
            Integer nbConversions,

            @JsonProperty("revenue")
            Float revenue,

            @JsonProperty("segment")
            String segment
    ) implements VisitTime {
    }

}
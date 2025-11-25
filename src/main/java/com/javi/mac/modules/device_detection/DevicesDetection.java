package com.javi.mac.modules.device_detection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javi.mac.modules.common.Goals;

import java.util.Map;

public sealed interface DevicesDetection {
    record GetBrowsers(
            @JsonProperty("label")
            String label, // Ej: "Chrome", "Mobile Safari"

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
            Integer sumVisitLength,

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

            @JsonProperty("logo")
            String logoPath,

            @JsonProperty("segment")
            String segment
    ) implements DevicesDetection {
    }

    record GetModel(
            @JsonProperty("label")
            String label,

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
            Integer sumVisitLength,

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

            @JsonProperty("logo")
            String logoPath,

            @JsonProperty("segment")
            String segment
    ) implements DevicesDetection {
    }

    record GetOsFamilies(
            @JsonProperty("label")
            String label,

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
            Integer sumVisitLength,

            @JsonProperty("bounce_count")
            Float bounceCount,

            @JsonProperty("nb_visits_converted")
            Integer nbVisitsConverted,

            @JsonProperty("nb_conversions")
            Integer nbConversions,

            @JsonProperty("revenue")
            Float revenue,

            @JsonProperty("logo")
            String logoPath,

            @JsonProperty("segment")
            String segment
    ) implements DevicesDetection {
    }

    record GetType(
            @JsonProperty("label")
            String label, // Ej: "Chrome", "Mobile Safari"

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
            Integer sumVisitLength,

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

            @JsonProperty("logo")
            String logoPath,

            @JsonProperty("segment")
            String segment
    ) implements DevicesDetection {
    }

}

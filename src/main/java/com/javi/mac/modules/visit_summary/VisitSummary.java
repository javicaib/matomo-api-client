package com.javi.mac.modules.visit_summary;

import com.fasterxml.jackson.annotation.JsonProperty;

public sealed interface VisitSummary {

    record Get(
            @JsonProperty("nb_uniq_visitors")
            Integer nbUniqueVisitors,

            @JsonProperty("nb_users")
            Integer nbUsers,

            @JsonProperty("nb_visits")
            Integer nbVisits,

            @JsonProperty("nb_actions")
            Integer nbActions,

            @JsonProperty("nb_visits_converted")
            Integer nbVisitsConverted,

            @JsonProperty("bounce_count")
            Integer bounceCount,

            @JsonProperty("sum_visit_length")
            Long sumVisitLength,

            @JsonProperty("max_actions")
            Integer maxActions,

            @JsonProperty("bounce_rate")
            Float bounceRate,

            @JsonProperty("nb_actions_per_visit")
            Float nbActionsPerVisit,

            @JsonProperty("avg_time_on_site")
            Integer avgTimeOnSite
    ) implements VisitSummary {
    }
    record GetVisits(
            @JsonProperty("value"
            ) Integer visits) implements VisitSummary {
    }
}

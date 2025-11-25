package com.javi.mac.modules.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public sealed interface Goals {
    record GoalData(
            @JsonProperty("nb_conversions")
            Integer nbConversions,

            @JsonProperty("nb_visits_converted")
            Integer nbVisitsConverted,

            @JsonProperty("revenue")
            Float revenue,


            @JsonProperty("items")
            Integer items,

            @JsonProperty("revenue_subtotal")
            Float revenueSubtotal,

            @JsonProperty("revenue_tax")
            Float revenueTax,

            @JsonProperty("revenue_shipping")
            Float revenueShipping,

            @JsonProperty("revenue_discount")
            Float revenueDiscount
    ) implements Goals {
    }
}

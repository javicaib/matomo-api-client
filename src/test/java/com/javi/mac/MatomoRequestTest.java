package com.javi.mac;

import com.javi.mac.enums.PeriodEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatomoRequestTest {
    @Test
    void testConstructor_DayPeriod() {
        List<LocalDate> dates = Collections.singletonList(LocalDate.of(2023, 10, 15));
        MatomoRequest request = new MatomoRequest(
                "VisitsSummary.get",
                1,
                PeriodEnum.DAY,
                dates,
                null
        );

        assertEquals("day", request.getPeriod());
        assertEquals("2023-10-15", request.getDate());
    }

    @Test
    void testConstructor_WeekPeriod() {
        List<LocalDate> dates = Collections.singletonList(LocalDate.of(2023, 10, 15));
        MatomoRequest request = new MatomoRequest(
                "VisitsSummary.get",
                1,
                PeriodEnum.WEEK,
                dates,
                Map.of("filter_limit", "10")
        );

        assertEquals("week", request.getPeriod());
        assertEquals("2023-10-15", request.getDate());
        assertEquals("10", request.getExtraParams().get("filter_limit"));
    }

    @Test
    void testConstructor_MonthPeriod() {
        List<LocalDate> dates = Collections.singletonList(LocalDate.of(2023, 10, 1));
        MatomoRequest request = new MatomoRequest(
                "VisitsSummary.get",
                1,
                PeriodEnum.MONTH,
                dates,
                Collections.emptyMap()
        );

        assertEquals("month", request.getPeriod());
        assertEquals("2023-10-01", request.getDate());
    }

    @Test
    void testConstructor_RangePeriod_OrderedDates() {
        List<LocalDate> dates = Arrays.asList(
                LocalDate.of(2023, 10, 1),
                LocalDate.of(2023, 10, 15)
        );

        MatomoRequest request = new MatomoRequest(
                "VisitsSummary.get",
                1,
                PeriodEnum.RANGE,
                dates,
                null
        );

        assertEquals("range", request.getPeriod());
        assertEquals("2023-10-01,2023-10-15", request.getDate());
    }

    @Test
    void testConstructor_RangePeriod_UnorderedDates() {
        // Las fechas deben ordenarse automáticamente
        List<LocalDate> dates = Arrays.asList(
                LocalDate.of(2023, 10, 15),
                LocalDate.of(2023, 10, 1)
        );

        MatomoRequest request = new MatomoRequest(
                "VisitsSummary.get",
                1,
                PeriodEnum.RANGE,
                dates,
                null
        );

        assertEquals("2023-10-01,2023-10-15", request.getDate());
    }

    @Test
    void testConstructor_RangePeriod_MoreThanTwoDates() {
        // Debería tomar solo las dos primeras después de ordenar
        List<LocalDate> dates = Arrays.asList(
                LocalDate.of(2023, 10, 20),
                LocalDate.of(2023, 10, 5),
                LocalDate.of(2023, 10, 15)
        );

        MatomoRequest request = new MatomoRequest(
                "VisitsSummary.get",
                1,
                PeriodEnum.RANGE,
                dates,
                null
        );

        assertEquals("2023-10-05,2023-10-20", request.getDate());
    }

    @Test
    void testConstructor_NonRangePeriodWithMultipleDatesTakesFirstDate() {
        List<LocalDate> dates = Arrays.asList(
                LocalDate.of(2023, 10, 1),
                LocalDate.of(2023, 10, 2)
        );

        var request = new MatomoRequest("VisitsSummary.get", 1, PeriodEnum.DAY, dates, null);
        assertEquals("2023-10-01", request.getDate());
    }

    @Test
    void testFailure_DatesListIsNull() {
        // Debe fallar al inicio del constructor (Objects.requireNonNull)
        assertThrows(IllegalArgumentException.class, () -> {
            new MatomoRequest("method", 1, PeriodEnum.DAY, null, null);
        }, "Debe fallar si la lista de fechas es null.");
    }

    @Test
    void testFailure_DatesListIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                        new MatomoRequest("method", 1, PeriodEnum.DAY, Collections.emptyList(), null),
                "Debe fallar si la lista de fechas está vacía."
        );
    }

    @Test
    void testFailure_DatesListContainsNull() {
        // Debe fallar si la lista contiene null (antes de dates.sort)
        List<LocalDate> datesWithNull = Arrays.asList(LocalDate.now(), null);
        assertThrows(IllegalArgumentException.class, () -> new MatomoRequest("method", 1, PeriodEnum.DAY, datesWithNull, null), "Debe fallar si la lista de fechas contiene una entrada null.");
    }

    @Test
    void testFailure_RangePeriod_LessThanTwoDates() {
        // ARRANGE: Usar RANGE, pero solo pasar 1 fecha
        List<LocalDate> dates = Collections.singletonList(LocalDate.of(2023, 10, 15));

        // ACT / ASSERT: Debe fallar explícitamente
        assertThrows(IllegalArgumentException.class, () ->
                        new MatomoRequest("method", 1, PeriodEnum.RANGE, dates, null),
                "Debe fallar si RANGE se usa con menos de dos fechas."
        );
    }
}
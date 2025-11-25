package com.javi.mac;

import com.javi.mac.generics.Result;
import com.javi.mac.internal.HttpExecutor;
import com.javi.mac.modules.device_detection.DevicesDetectionModule;
import com.javi.mac.modules.visit_summary.VisitsSummaryModule;
import com.javi.mac.modules.visit_time.VisitTimeModule;
import tools.jackson.core.type.TypeReference;

public class MatomoClient {
    private final String baseUrl;
    private final String tokenAuth;
    private final HttpExecutor http;
    private final Integer matomoVersion;

    // Constructor privado para ser usado por el Builder
    private MatomoClient(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.tokenAuth = builder.tokenAuth;
        this.matomoVersion = builder.matomoVersion;
        this.http = new HttpExecutor();
    }

    // ----------------------------------------------------------------------
    // Métodos de Módulos
    // ----------------------------------------------------------------------

    public VisitsSummaryModule visitsSummaryModule() {
        return new VisitsSummaryModule(this);
    }

    public VisitTimeModule visitTimeModule() {
        return new VisitTimeModule(this);
    }

    public DevicesDetectionModule devicesDetectionModule() {
        return new DevicesDetectionModule(this);
    }

    /**
     * Ejecuta una solicitud a la API de Matomo para tipos GENÉRICOS (ej: List<T>, Map<K, V>).
     *
     * @param request      La solicitud MatomoRequest con los parámetros.
     * @param responseType Un TypeReference que define el tipo genérico de respuesta esperado.
     * @return El resultado de la operación.
     */
    public <T> Result<T, String> execute(MatomoRequest request, TypeReference<T> responseType) {

        // Llama al HttpExecutor.execute() que ahora debe devolver T y manejar la deserialización.
        // Esta es la forma más limpia, pasando la responsabilidad de deserialización al executor.
        return http.get(
                baseUrl,
                request.toQueryParams(),
                tokenAuth,
                matomoVersion,
                responseType // Pasa directamente el TypeReference<T>
        );
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String baseUrl;
        private String tokenAuth;
        private Integer matomoVersion = 4; // Valor por defecto

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder tokenAuth(String tokenAuth) {
            this.tokenAuth = tokenAuth;
            return this;
        }

        public Builder matomoVersion(Integer version) {
            this.matomoVersion = version;
            return this;
        }

        public MatomoClient build() {
            if (baseUrl == null) throw new IllegalArgumentException("baseUrl is required");
            if (tokenAuth == null) throw new IllegalArgumentException("tokenAuth is required");

            return new MatomoClient(this);
        }
    }
}
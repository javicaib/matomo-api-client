package com.javi.mac.internal;

import com.javi.mac.generics.Result;
import okhttp3.*;
import tools.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class HttpExecutor {

    private final Logger log = Logger.getLogger(HttpExecutor.class.getName());
    private final OkHttpClient client = new OkHttpClient();

    private static final Map<String, String> defaultQueryParams = Map.of(
            "format", "JSON",
            "module", "API",
            "format_metrics", "0"
    );

    /**
     * Ejecuta una solicitud HTTP a la API de Matomo y deserializa la respuesta JSON
     * usando el JsonMapper para manejar tipos genéricos (Listas).
     * * @param <T> El tipo de objeto esperado como respuesta (puede ser un tipo genérico).
     *
     * @param baseUrl       La URL base del servidor Matomo.
     * @param params        Los parámetros específicos del método de la API.
     * @param tokenAuth     El token de autenticación.
     * @param matomoVersion La versión de Matomo (>= 5 usa POST, < 5 usa GET).
     * @param typeReference Un TypeReference de Jackson para manejar tipos genéricos (Listas).
     * @return Un objeto Result<T, String> que contiene el objeto T deserializado o un mensaje de error.
     */
    public <T> Result<T, String> get(
            String baseUrl,
            Map<String, String> params,
            String tokenAuth,
            Integer matomoVersion,
            TypeReference<T> typeReference // <--- Argumento clave
    ) {

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(baseUrl + "/index.php")).newBuilder();

        Map<String, String> allParams = new HashMap<>(params);
        allParams.putAll(defaultQueryParams);

        Request request;

        // Lógica de construcción de Request (GET vs POST)
        if (matomoVersion > 4) {
            // POST para Matomo v5+
            FormBody.Builder formBuilder = new FormBody.Builder();
            allParams.forEach(formBuilder::add);
            formBuilder.add("token_auth", tokenAuth);

            RequestBody body = formBuilder.build();

            request = new Request.Builder()
                    .post(body)
                    .url(urlBuilder.build())
                    .build();
        } else {
            // GET para Matomo v4-
            allParams.put("token_auth", tokenAuth);
            allParams.forEach(urlBuilder::addQueryParameter);

            request = new Request.Builder()
                    .url(urlBuilder.build())
                    .build();
        }

        log.info("Executing request: " + request.url());

        try (Response response = client.newCall(request).execute()) {

            String jsonBody = response.body().string();

            // 1. Manejo de Errores HTTP
            if (!response.isSuccessful()) {
                return new Result.Failure<>("HTTP error " + response.code() + ": " + jsonBody);
            }

            // 2. Manejo de Errores de la API de Matomo (JSON con {"result": "error"})
            if (jsonBody.contains("\"result\":\"error\"")) {
                // Se podría deserializar el mensaje exacto si la estructura de error fuera conocida,
                // pero devolver el cuerpo es suficiente para el diagnóstico.
                return new Result.Failure<>("Matomo API Error: " + jsonBody);
            }

            // 3. Deserialización usando el JsonMapper y el TypeReference
            // Esto resuelve el error de deserialización de arrays JSON.
            Result<T, String> parseResult = JsonMapper.fromJson(jsonBody, typeReference);

            if (parseResult.isSuccess()) {
                return new Result.Success<>(parseResult.getSuccessValue());
            } else {
                // Error de JSON parsing (e.g., el JSON no concuerda con la estructura T)
                return new Result.Failure<>(parseResult.getError());
            }

        } catch (IOException e) {

            return new Result.Failure<>("Network I/O error: " + e.getMessage());
        }
    }
}
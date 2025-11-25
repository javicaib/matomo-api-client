package com.javi.mac.internal;

import com.javi.mac.generics.Result;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
/**
 * Clase de utilidad para la serialización y deserialización de JSON.
 */
public final class JsonMapper {

    private JsonMapper() {
        // Constructor privado para prevenir la instanciación de la clase de utilidad.
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    // ----------------------------------------------------------------------
    // 1. Método Original: Deserializa tipos NO genéricos (usando Class<T>)
    // ----------------------------------------------------------------------

    /**
     * Intenta analizar una cadena JSON en un objeto de tipo T (no genérico).
     *
     * @param json El JSON string a analizar.
     * @param cls El objeto Class que representa el tipo T.
     * @param <T> El tipo de destino para el análisis JSON.
     * @return Un objeto Result (Success o Failure).
     */
    public static <T> Result<T, String> fromJson(String json, Class<T> cls) {
        try {
            T value = mapper.readValue(json, cls);
            return new Result.Success<>(value);
        } catch (Exception e) {
            return new Result.Failure<>("JSON parse error for type " + cls.getSimpleName() + ": " + e.getMessage());
        }
    }

    // ----------------------------------------------------------------------
    // 2. Método Sobrecargado: Deserializa tipos GENÉRICOS (usando TypeReference)
    // ----------------------------------------------------------------------

    /**
     * Intenta analizar una cadena JSON en un tipo genérico T (ej: List<MiClase>).
     *
     * @param json El JSON string a analizar.
     * @param typeReference Un TypeReference de Jackson que especifica el tipo genérico de destino.
     * @param <T> El tipo de destino genérico.
     * @return Un objeto Result (Success o Failure).
     */
    public static <T> Result<T, String> fromJson(String json, TypeReference<T> typeReference) {
        try {
            T value = mapper.readValue(json, typeReference);
            return new Result.Success<>(value);
        } catch (Exception e) {
            // Añadir el tipo genérico al error para mejor depuración
            return new Result.Failure<>("JSON parse error for generic type: " + e.getMessage());
        }
    }
}
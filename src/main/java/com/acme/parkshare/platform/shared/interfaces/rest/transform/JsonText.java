package com.acme.parkshare.platform.shared.interfaces.rest.transform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Converts between the JSON text stored in a column and the arrays exposed in the REST resources.
 * Keeps aggregates simple (plain text) while the API speaks arrays, as the Angular client does.
 */
public final class JsonText {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private JsonText() {}

    /** Serializes a list; a null list yields null so callers can tell "not provided" apart from "empty". */
    public static String toJson(List<?> values) {
        if (values == null) return null;
        try {
            return MAPPER.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot serialize list", e);
        }
    }

    public static <T> List<T> toList(String json, TypeReference<List<T>> type) {
        if (json == null || json.isBlank()) return List.of();
        try {
            return MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            return List.of();
        }
    }

    public static List<String> toStrings(String json) {
        return toList(json, new TypeReference<List<String>>() {});
    }

    public static <T> T convert(Object value, Class<T> type) {
        return MAPPER.convertValue(value, type);
    }
}

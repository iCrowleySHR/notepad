package com.gualda.sachetto.notepad.service;

import java.util.HashMap;
import java.util.Map;

public class ApiConfig {
    public static final String BASE_URL = "http://192.168.1.29/api_notepad/public/api/v1";

    public static Map<String, String> configHeaders(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        headers.put("Content-Type", "application/json");
        return headers;
    }
}

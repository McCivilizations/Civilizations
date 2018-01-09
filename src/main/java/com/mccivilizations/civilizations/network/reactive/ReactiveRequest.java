package com.mccivilizations.civilizations.network.reactive;

import java.util.Map;

public class ReactiveRequest {
    private final String type;
    private final String method;
    private final Map<String, String> parameters;

    public ReactiveRequest(String type, String method, Map<String, String> parameters) {
        this.method = method;
        this.parameters = parameters;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}

package com.murali.users.controllers;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "customEndpoint")
public class CustomEndpoint {

    private Map<String, String> data = new HashMap<>();

    public CustomEndpoint() {
        data.put("status", "running");
        data.put("version", "1.0.0");
    }

    @ReadOperation
    public Map<String, String> getInfo() {
        return data;
    }

    @WriteOperation
    public void updateStatus(String status) {
        data.put("status", status);
    }
}

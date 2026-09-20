package com.schedulerApi.service;

import tools.jackson.databind.JsonNode;

public interface JobPayloadValidator {
    String supportedJobType();
    boolean validatePayload(JsonNode payload);
}

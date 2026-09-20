package com.schedulerApi.service;

import com.common.enums.ScheduleJobType;
import tools.jackson.databind.JsonNode;

public class WebhookPayloadValidator implements JobPayloadValidator{
    @Override
    public String supportedJobType() {
        return ScheduleJobType.WEBHOOK.name();
    }

    @Override
    public boolean validatePayload(JsonNode payload) {
        return false;
    }
}

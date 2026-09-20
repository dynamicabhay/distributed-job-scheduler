package com.schedulerApi.service;

import com.common.enums.ScheduleJobType;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.util.regex.Pattern;

@Component
public class EmailPayloadValidator implements JobPayloadValidator{

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    public String supportedJobType() {
        return ScheduleJobType.EMAIL.name();
    }

    @Override
    public boolean validatePayload(JsonNode payload) {

        if (payload == null || !payload.isObject()) {
            return false;
        }

        JsonNode to = payload.get("to");
        JsonNode subject = payload.get("subject");
        JsonNode body = payload.get("body");

        if (to == null || !to.isTextual() || to.asText().isBlank()) {
            return false;
        }

        if (!EMAIL_PATTERN.matcher(to.asText()).matches()) {
            return false;
        }

        if (subject == null || !subject.isTextual() || subject.asText().isBlank()) {
            return false;
        }

        if (body == null || !body.isTextual() || body.asText().isBlank()) {
            return false;
        }

        return true;
    }
}

package com.schedulerApi.service;

import com.schedulerApi.dto.ScheduleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
@Slf4j
public class RequestHashService {
    ObjectMapper objectMapper;

    public RequestHashService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public String generateHash(ScheduleRequest request){
        // serialize the request to json
        try {
            String json = objectMapper.writeValueAsString(request);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    json.getBytes(StandardCharsets.UTF_8)
            );

            String hexHash =  HexFormat.of().formatHex(hash);
            log.info("hex format hash: {}",hexHash);
            return hexHash;

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Failed to generate request hash", e);

        }
    }
}

package com.fitiz.challenge.kafka;

import com.fitiz.challenge.model.StepCountUpdateData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class StepCountSimulator {

    @Value("${prop.config.broker-properties.step-count-topic}")
    public String STEP_COUNT_TOPIC;

    public static final List<String> PARTICIPANTS = Arrays.asList("burakemreozer", "evtim", "yunus");
    public static final List<UUID> PARTICIPANTS_UUID = Arrays.asList(
            UUID.fromString("d52d4fa6-8d40-4c78-a978-7b1f55ac3aeb"),
            UUID.fromString("70f1998f-08ee-4ab9-bcc0-d407289ab3ed"),
            UUID.fromString("809d7e11-4ffd-4dcf-82a4-4186e639be1b")
            );

    public static final UUID CHALLENGE_ID = UUID.fromString("3802d452-2b3b-49a7-96df-5816653bb5e1");

    private final KafkaTemplate<String, StepCountUpdateData> kafkaStepCountTemplate;

    @Scheduled(fixedRate = 1000)
    public void randomStepCounts() {
        int numOfParticipants = 1;
        for (int i = 0; i < numOfParticipants; i++) {
            publishStepCount();
        }
    }

    public void publishStepCount() {
        StepCountUpdateData stepCountUpdateData = buildStepCountUpdateData();
        kafkaStepCountTemplate.send(STEP_COUNT_TOPIC, stepCountUpdateData.username(), stepCountUpdateData);
        log.info("Step count update for [user: {}] was published to Kafka", stepCountUpdateData.username());
    }

    private StepCountUpdateData buildStepCountUpdateData() {
        SecureRandom random = new SecureRandom();
        int participantIdx = random.nextInt(PARTICIPANTS.size());
        return new StepCountUpdateData(
                PARTICIPANTS_UUID.get(participantIdx),
                PARTICIPANTS.get(participantIdx),
                CHALLENGE_ID,
                29,
                LocalDateTime.now());
    }

}
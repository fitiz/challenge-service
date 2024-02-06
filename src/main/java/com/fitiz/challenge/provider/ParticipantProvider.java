package com.fitiz.challenge.provider;

import com.fitiz.challenge.tables.pojos.Participant;
import com.fitiz.challenge.tables.records.ParticipantRecord;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.fitiz.challenge.tables.Participant.PARTICIPANT;
import static com.fitiz.challenge.utils.DbUtils.filterRecordFields;

@Repository
public class ParticipantProvider {

    private static final List<Field> PARTICIPANT_CREATE_EXCLUDED_FIELDS = List.of(PARTICIPANT.CREATED_AT);

    private final DSLContext dslContext;

    public ParticipantProvider(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public Participant getParticipantByUserId(UUID userId) {
        Optional<Record> userInfoRecord = dslContext.select()
                .from(PARTICIPANT)
                .where(PARTICIPANT.USER_ID.eq(userId))
                .fetchOptional();

        return userInfoRecord.map(participant -> participant.into(Participant.class)).orElse(null);
    }

    public List<Participant> getParticipantsBatch(List<UUID> userIds) {
        return dslContext.select()
                .from(PARTICIPANT)
                .where(PARTICIPANT.USER_ID.in(userIds))
                .fetchStreamInto(Participant.class)
                .collect(Collectors.toList());
    }

    public Participant createParticipant(Participant participant) {
        ParticipantRecord participantRecord = dslContext.newRecord(PARTICIPANT, participant);

        Record record = filterRecordFields(participantRecord, PARTICIPANT_CREATE_EXCLUDED_FIELDS);

        return dslContext.insertInto(PARTICIPANT)
                .set(record)
                .returning()
                .fetchOne()
                .into(Participant.class);
    }

    public Boolean participateInChallenge(UUID userId, UUID challengeId) {
        int res = dslContext.update(PARTICIPANT)
                .set(PARTICIPANT.CHALLENGE_ID, challengeId)
                .where(PARTICIPANT.USER_ID.eq(userId))
                .execute();
        return res != 0;
    }

    public boolean claimReward(UUID userId, Integer reward) {
        int res = dslContext.update(PARTICIPANT)
                .set(PARTICIPANT.POINTS, PARTICIPANT.POINTS.plus(reward))
                .where(PARTICIPANT.USER_ID.eq(userId))
                .execute();
        return res != 0;
    }
}

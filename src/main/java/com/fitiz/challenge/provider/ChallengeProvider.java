package com.fitiz.challenge.provider;

import com.fitiz.challenge.tables.pojos.Challenge;
import com.fitiz.challenge.tables.records.ChallengeRecord;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.fitiz.challenge.tables.Challenge.CHALLENGE;
import static com.fitiz.challenge.utils.DbUtils.filterRecordFields;

@Repository
public class ChallengeProvider {
    private static final List<Field> CHALLENGE_CREATE_EXCLUDED_FIELDS =
            List.of(CHALLENGE.ID, CHALLENGE.CREATED_AT);

    private final DSLContext dslContext;

    public ChallengeProvider(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public Optional<Challenge> getChallengeById(UUID challengeId) {
        return dslContext.select()
                .from(CHALLENGE)
                .where(CHALLENGE.ID.eq(challengeId))
                .limit(1)
                .fetchOptionalInto(Challenge.class);
    }

    public Optional<Challenge> getChallengeByLocationId(Integer locationId) {
        return dslContext.select()
                .from(CHALLENGE)
                .where(CHALLENGE.LOCATION_ID.eq(locationId))
                .orderBy(CHALLENGE.CREATED_AT.desc())
                .limit(1)
                .fetchOptionalInto(Challenge.class);
    }

    public void createChallenge(Challenge challenge) {
        ChallengeRecord challengeRecord = dslContext.newRecord(CHALLENGE, challenge);
        Record record = filterRecordFields(challengeRecord, CHALLENGE_CREATE_EXCLUDED_FIELDS);
        dslContext.insertInto(CHALLENGE)
                .set(record)
                .execute();
    }
}

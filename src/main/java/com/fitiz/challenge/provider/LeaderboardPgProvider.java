package com.fitiz.challenge.provider;

import com.fitiz.challenge.tables.pojos.Leaderboard;
import com.fitiz.challenge.tables.records.LeaderboardRecord;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

import static com.fitiz.challenge.tables.Leaderboard.LEADERBOARD;
import static com.fitiz.challenge.utils.DbUtils.filterRecordFields;

@Repository
public class LeaderboardPgProvider {
    private static final List<Field> LEADERBOARD_CREATE_EXCLUDED_FIELDS =
            List.of(LEADERBOARD.ID, LEADERBOARD.CREATED_AT);

    private final DSLContext dslContext;

    public LeaderboardPgProvider(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void addToLeaderboard(Leaderboard leaderboard) {
        LeaderboardRecord leaderboardRecord = dslContext.newRecord(LEADERBOARD, leaderboard);
        Record record = filterRecordFields(leaderboardRecord, LEADERBOARD_CREATE_EXCLUDED_FIELDS);
        dslContext.insertInto(LEADERBOARD)
                .set(record)
                .execute();
    }

    public boolean updateStepCount(UUID userId, Integer steps) {
        int res = dslContext.update(LEADERBOARD)
                .set(LEADERBOARD.STEPS, LEADERBOARD.STEPS.plus(steps))
                .where(LEADERBOARD.PARTICIPANT_ID.eq(userId))
                .execute();
        return res != 0;
    }
}

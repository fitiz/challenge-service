package com.fitiz.challenge.utils;

import org.jooq.Field;
import org.jooq.Record;

import java.util.Arrays;
import java.util.List;

public class DbUtils {

  public static Record filterRecordFields(Record record, List<Field> fieldsToFilter) {
    List<Field<?>> filteredFields = Arrays.stream(record.fields())
                                          .filter(field -> !fieldsToFilter.contains(field))
                                          .toList();
    return record.into(filteredFields.toArray(new Field[0]));
  }

}

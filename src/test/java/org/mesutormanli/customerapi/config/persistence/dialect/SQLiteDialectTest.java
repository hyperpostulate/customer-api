package org.mesutormanli.customerapi.config.persistence.dialect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLiteDialectTest {
    private static final String CONSTRAINT_NAME = "constraint";
    private static final String[] FOREIGN_KEY = {"FK"};
    private static final String REFERANCED_TABLE = "rreferancedTable";
    private static final String[] PRIMARY_KEY = {"PK"};
    private static final boolean REFERANCES_PRIMARY_KEY = true;


    private SQLiteDialect sqLiteDialect;

    @BeforeEach
    void setUp() {
        sqLiteDialect = new SQLiteDialect();
    }

    @Test
    void getIdentityColumnSupport() {
        assertNotNull(sqLiteDialect.getIdentityColumnSupport());
    }

    @Test
    void hasAlterTable() {
        assertFalse(sqLiteDialect.hasAlterTable());
    }

    @Test
    void dropConstraints() {
        assertFalse(sqLiteDialect.dropConstraints());
    }

    @Test
    void getDropForeignKeyString() {
        assertEquals("", sqLiteDialect.getDropForeignKeyString());
    }

    @Test
    void getAddForeignKeyConstraintString() {
        assertEquals("", sqLiteDialect.getAddForeignKeyConstraintString(CONSTRAINT_NAME, FOREIGN_KEY, REFERANCED_TABLE, PRIMARY_KEY, REFERANCES_PRIMARY_KEY));
    }

    @Test
    void getAddPrimaryKeyConstraintString() {
        assertEquals("", sqLiteDialect.getAddPrimaryKeyConstraintString(CONSTRAINT_NAME));
    }

    @Test
    void getForUpdateString() {
        assertEquals("", sqLiteDialect.getForUpdateString());
    }

    @Test
    void getAddColumnString() {
        assertEquals("add column", sqLiteDialect.getAddColumnString());
    }

    @Test
    void supportsOuterJoinForUpdate() {
        assertFalse(sqLiteDialect.supportsOuterJoinForUpdate());
    }

    @Test
    void supportsIfExistsBeforeTableName() {
        assertTrue(sqLiteDialect.supportsIfExistsBeforeTableName());
    }

    @Test
    void supportsCascadeDelete() {
        assertFalse(sqLiteDialect.supportsCascadeDelete());
    }
}
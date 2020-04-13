package org.mesutormanli.customerapi.config.persistence.dialect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SQLiteIdentityColumnSupportTest {
    private static final String TABLE = "CUSTOMER";
    private static final String COLUMN = "id";
    private static final int TYPE = Types.INTEGER;

    private SQLiteDialect.SQLiteIdentityColumnSupport sqLiteIdentityColumnSupport;

    @BeforeEach
    void setUp() {
        sqLiteIdentityColumnSupport = new SQLiteDialect.SQLiteIdentityColumnSupport();
    }

    @Test
    void supportsIdentityColumns() {
        assertTrue(sqLiteIdentityColumnSupport.supportsIdentityColumns());
    }

    @Test
    void getIdentitySelectString() {
        assertEquals("select last_insert_rowid()", sqLiteIdentityColumnSupport.getIdentitySelectString(TABLE, COLUMN, TYPE));
    }

    @Test
    void getIdentityColumnString() {
        assertEquals("integer", sqLiteIdentityColumnSupport.getIdentityColumnString(TYPE));
    }
}
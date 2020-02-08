package org.mesutormanli.customerapi.config.database.dialect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SQLiteIdentityColumnSupportTest {
    private static String TABLE = "CUSTOMER";
    private static String COLUMN = "id";
    private static int TYPE = Types.INTEGER;

    private SQLiteIdentityColumnSupport sqLiteIdentityColumnSupport;

    @BeforeEach
    void setUp() {
        sqLiteIdentityColumnSupport = new SQLiteIdentityColumnSupport();
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
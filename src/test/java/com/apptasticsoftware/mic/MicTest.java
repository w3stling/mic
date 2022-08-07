package com.apptasticsoftware.mic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class MicTest {

    @Test
    void testEquals() {
        Mic mic1 = new Mic("SWEDEN", "SE", "XSTO", "XSTO", "O","NASDAQ STOCKHOLM AB",
                null, "STOCKHOLM", "WWW.NASDAQOMXNORDIC.COM", "DECEMBER 2015", "ACTIVE", "JULY 2010", "");

        Mic mic2 = new Mic("SWEDEN", "SE", "XSTO", "XSTO", "O", "NASDAQ STOCKHOLM AB",
                null, "STOCKHOLM", "WWW.NASDAQOMXNORDIC.COM", "DECEMBER 2015", "ACTIVE", "JULY 2010", "");

        Mic mic3 = new Mic("SWEDEN", "SE", "XSTO", "XSTO", "S", "NASDAQ STOCKHOLM AB",
                "S3", "STOCKHOLM", "WWW.NASDAQOMXNORDIC.COM", "DECEMBER 2015", "ACTIVE", "JULY 2010", "");

        Mic mic4 = new Mic("a", "a", "a", "a", "s", "a", "a", "a", "a", "a", "a", "a", "a");
        Mic mic5 = new Mic(null, null, null, null, null,null, null, null, null, null, null, null, null);


        assertEquals(mic1, mic1);
        assertEquals(mic1, mic2);
        assertNotEquals(mic1, mic3);
        assertNotEquals(mic3, mic4);
        assertNotEquals(mic3, mic5);
        assertNotEquals(mic3, "");
    }


    @Test
    void testHashCode() {
        Mic mic1 = new Mic("SWEDEN", "SE", "XSTO", "XSTO", "O","NASDAQ STOCKHOLM AB",
                null, "STOCKHOLM", "WWW.NASDAQOMXNORDIC.COM", "DECEMBER 2015", "ACTIVE", "JULY 2010", "");

        assertEquals(745030446, mic1.hashCode());
    }

}

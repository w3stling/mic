package com.apptasticsoftware.mic;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class MicLookupTest {

    @Test
    void testDownloadOrOffline() {
        MicLookup lookup = MicLookup.getInstance();
        assertTrue(lookup.size() > 1800);
    }

    @Test
    void testDownload() throws IOException {
        MicLookup lookup = MicLookup.getInstance(true);
        assertTrue(lookup.isDownloaded());
        assertTrue(lookup.size() > 1800);
    }

    @Test
    void testDownloadGetByMic() throws IOException {
        MicLookup lookup = MicLookup.getInstance(true);
        assertTrue(lookup.isDownloaded());

        Optional<Mic> mic = lookup.getMic("XSTO");
        assertTrue(mic.isPresent());

        assertEquals("SWEDEN", mic.get().getCountry());
        assertEquals("SE", mic.get().getCountryCode());
        assertEquals("XSTO", mic.get().getMic());
        assertEquals("XSTO", mic.get().getOperatingMic());
        assertEquals("O", mic.get().getMarketType());
        assertEquals("NASDAQ STOCKHOLM AB", mic.get().getNameInstitutionDescription());
        assertFalse(mic.get().getAcronym().isPresent());
        assertEquals("STOCKHOLM", mic.get().getCity());
        assertEquals("WWW.NASDAQOMXNORDIC.COM", mic.get().getWebSite().orElse(null));
        assertEquals("DECEMBER 2015", mic.get().getStatusDate());
        assertEquals(YearMonth.of(2015, Month.DECEMBER), mic.get().getStatusDateYearMonth());
        assertEquals("ACTIVE", mic.get().getStatus());
        assertTrue(mic.get().isActive());
        assertFalse(mic.get().isDeleted());
        assertFalse(mic.get().isModified());
        assertEquals("JULY 2010", mic.get().getCreationDate());
        assertEquals(YearMonth.of(2010, Month.JULY), mic.get().getCreationDateYearMonth());
        assertFalse(mic.get().getComments().isPresent());

        assertTrue(lookup.getMic("AAAA").isEmpty());
    }

    @Test
    void testDownloadGetByOperationalMic() throws IOException {
        MicLookup lookup = MicLookup.getInstance(true);
        assertTrue(lookup.isDownloaded());

        assertTrue(lookup.getMicByOperationalMic("XSTO").count() > 10);
        assertEquals(0, lookup.getMicByOperationalMic("AAAA").count());
    }

    @Test
    void testDownloadGetByCountryCode() throws IOException {
        MicLookup lookup = MicLookup.getInstance(true);
        assertTrue(lookup.isDownloaded());
        assertTrue(lookup.getMicByCountryCode("SE").count() > 30);
        assertEquals(0, lookup.getMicByCountryCode("AA").count());
    }

    @Test
    void testFromFile() throws IOException {
        MicLookup lookup = MicLookup.getInstance(false);
        assertFalse(lookup.isDownloaded());
        assertTrue(lookup.size() > 1800);
        assertEquals(lookup.size(), lookup.getAll().count());

        List<Mic> list = lookup.getAll()
                               .peek(x -> assertNotNull(x.getCreationDateYearMonth()))
                               .peek(x -> assertNotNull(x.getStatusDateYearMonth()))
                               .filter(m -> "XSTO".equals(m.getMic()))
                               .collect(Collectors.toList());

        assertFalse(list.isEmpty());
    }

    @Test
    void testFromFileGetByMic() throws IOException {
        MicLookup lookup = MicLookup.getInstance(false);
        assertFalse(lookup.isDownloaded());

        Optional<Mic> mic = lookup.getMic("XSTO");
        assertTrue(mic.isPresent());

        assertEquals("SWEDEN", mic.get().getCountry());
        assertEquals("SE", mic.get().getCountryCode());
        assertEquals("XSTO", mic.get().getMic());
        assertEquals("XSTO", mic.get().getOperatingMic());
        assertEquals("O", mic.get().getMarketType());
        assertEquals("NASDAQ STOCKHOLM AB", mic.get().getNameInstitutionDescription());
        assertFalse(mic.get().getAcronym().isPresent());
        assertEquals("STOCKHOLM", mic.get().getCity());
        assertEquals("WWW.NASDAQOMXNORDIC.COM", mic.get().getWebSite().orElse(null));
        assertEquals("DECEMBER 2015", mic.get().getStatusDate());
        assertEquals("ACTIVE", mic.get().getStatus());
        assertTrue(mic.get().isActive());
        assertFalse(mic.get().isDeleted());
        assertFalse(mic.get().isModified());
        assertEquals("JULY 2010", mic.get().getCreationDate());
        assertFalse(mic.get().getComments().isPresent());
    }

    @Test
    void testFromFileGetByOperationalMic() throws IOException {
        MicLookup lookup = MicLookup.getInstance(false);
        assertFalse(lookup.isDownloaded());

        Stream<Mic> mic = lookup.getMicByOperationalMic("XSTO");
        assertEquals(24, mic.count());
    }

    @Test
    void testFromFileGetByCountryCode() throws IOException {
        MicLookup lookup = MicLookup.getInstance(false);
        assertFalse(lookup.isDownloaded());

        Stream<Mic> mic = lookup.getMicByCountryCode("SE");
        assertEquals(43, mic.count());
    }

    @Test
    void testActiveMic() throws IOException {
        MicLookup lookup = MicLookup.getInstance(false);
        List<Mic> mics = lookup.getAll()
                .filter(Mic::isActive)
                .collect(Collectors.toList());

        assertFalse(mics.isEmpty());
    }

}

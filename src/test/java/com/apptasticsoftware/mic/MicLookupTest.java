package com.apptasticsoftware.mic;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        assertEquals("SE", mic.get().getCountryCode());
        assertEquals("XSTO", mic.get().getMic());
        assertEquals("XSTO", mic.get().getOperatingMic());
        assertEquals("OPRT", mic.get().getMarketType());
        assertEquals("NASDAQ STOCKHOLM AB", mic.get().getNameInstitutionDescription());
        assertFalse(mic.get().getAcronym().isPresent());
        assertEquals("STOCKHOLM", mic.get().getCity());
        assertEquals("WWW.NASDAQOMXNORDIC.COM", mic.get().getWebsite().orElse(null));
        assertEquals("ACTIVE", mic.get().getStatus());
        assertTrue(mic.get().isActive());
        assertFalse(mic.get().isExpired());
        assertFalse(mic.get().isUpdated());
        assertEquals("20100726", mic.get().getCreationDate());
        assertEquals(LocalDate.of(2010,7,26), mic.get().getCreationLocalDate());
        assertFalse(mic.get().getComments().isPresent());

        assertTrue(lookup.getMic("AAAA").isEmpty());
    }

    @Test
    void testDownloadGetByOperationalMic() throws IOException {
        MicLookup lookup = MicLookup.getInstance(true);
        assertTrue(lookup.isDownloaded());

        assertTrue(lookup.getMicByOperationalMic("XSTO").size() > 10);
        assertEquals(0, lookup.getMicByOperationalMic("AAAA").size());
    }

    @Test
    void testDownloadGetByCountryCode() throws IOException {
        MicLookup lookup = MicLookup.getInstance(true);
        assertTrue(lookup.isDownloaded());
        assertTrue(lookup.getMicByCountryCode("SE").size() > 30);
        assertEquals(0, lookup.getMicByCountryCode("AA").size());
    }

    @Test
    void testFromFile() throws IOException {
        MicLookup lookup = MicLookup.getInstance(false);
        assertFalse(lookup.isDownloaded());
        assertTrue(lookup.size() > 1800);
        assertEquals(lookup.size(), lookup.getAll().size());

        List<Mic> list = lookup.getAll().stream()
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

        assertEquals("SE", mic.get().getCountryCode());
        assertEquals("XSTO", mic.get().getMic());
        assertEquals("XSTO", mic.get().getOperatingMic());
        assertEquals("OPRT", mic.get().getMarketType());
        assertEquals("NASDAQ STOCKHOLM AB", mic.get().getNameInstitutionDescription());
        assertFalse(mic.get().getAcronym().isPresent());
        assertEquals("STOCKHOLM", mic.get().getCity());
        assertEquals("WWW.NASDAQOMXNORDIC.COM", mic.get().getWebsite().orElse(null));
        assertEquals("ACTIVE", mic.get().getStatus());
        assertTrue(mic.get().isActive());
        assertFalse(mic.get().isExpired());
        assertFalse(mic.get().isUpdated());
        assertEquals("20100726", mic.get().getCreationDate());
        assertFalse(mic.get().getComments().isPresent());
    }

    @Test
    void testFromFileGetByOperationalMic() throws IOException {
        MicLookup lookup = MicLookup.getInstance(false);
        assertFalse(lookup.isDownloaded());

        List<Mic> mic = lookup.getMicByOperationalMic("XSTO");
        assertTrue(mic.size() > 15);
    }

    @Test
    void testFromFileGetByCountryCode() throws IOException {
        MicLookup lookup = MicLookup.getInstance(false);
        assertFalse(lookup.isDownloaded());

        List<Mic> mic = lookup.getMicByCountryCode("SE");
        assertTrue(mic.size() > 20);
    }

    @Test
    void testActiveMic() throws IOException {
        MicLookup lookup = MicLookup.getInstance(false);
        List<Mic> mics = lookup.getAll().stream()
                .filter(Mic::isActive)
                .collect(Collectors.toList());

        assertFalse(mics.isEmpty());
    }

}

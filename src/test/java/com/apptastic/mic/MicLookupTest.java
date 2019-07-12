package com.apptastic.mic;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


public class MicLookupTest {

    @Test
    public void testDownloadOrOffline() throws IOException {
        MicLookup lookup = MicLookup.getInstance();
        assertTrue(lookup.isDownloaded());
        assertTrue(lookup.size() > 1800);
    }

    @Test
    public void testDownload() throws IOException {
        MicLookup lookup = MicLookup.getInstance(true);
        assertTrue(lookup.isDownloaded());
        assertTrue(lookup.size() > 1800);
    }

    @Test
    public void testDownloadGetByMic() throws IOException {
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
        assertEquals("ACTIVE", mic.get().getStatus());
        assertTrue(mic.get().isActive());
        assertFalse(mic.get().isDeleted());
        assertFalse(mic.get().isModified());
        assertEquals("JULY 2010", mic.get().getCreationDate());
        assertFalse(mic.get().getComments().isPresent());

        assertTrue(lookup.getMic("AAAA").isEmpty());
    }

    @Test
    public void testDownloadGetByOperationalMic() throws IOException {
        MicLookup lookup = MicLookup.getInstance(true);
        assertTrue(lookup.isDownloaded());

        assertEquals(25, lookup.getMicByOperationalMic("XSTO").count());
        assertEquals(0, lookup.getMicByOperationalMic("AAAA").count());
    }

    @Test
    public void testDownloadGetByCountryCode() throws IOException {
        MicLookup lookup = MicLookup.getInstance(true);
        assertTrue(lookup.isDownloaded());
        assertEquals(41, lookup.getMicByCountryCode("SE").count());
        assertEquals(0, lookup.getMicByCountryCode("AA").count());
    }

    @Test
    public void testFromFile() throws IOException {
        MicLookup lookup = MicLookup.getInstance(false);
        assertFalse(lookup.isDownloaded());
        assertTrue(lookup.size() > 1800);
        assertEquals(lookup.size(), lookup.getAll().count());

        List<Mic> list = lookup.getAll()
                               .filter(m -> "XSTO".equals(m.getMic()))
                               .collect(Collectors.toList());

    }

    @Test
    public void testFromFileGetByMic() throws IOException {
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
    public void testFromFileGetByOperationalMic() throws IOException {
        MicLookup lookup = MicLookup.getInstance(false);
        assertFalse(lookup.isDownloaded());

        Stream<Mic> mic = lookup.getMicByOperationalMic("XSTO");
        assertEquals(25, mic.count());
    }

    @Test
    public void testFromFileGetByCountryCode() throws IOException {
        MicLookup lookup = MicLookup.getInstance(false);
        assertFalse(lookup.isDownloaded());

        Stream<Mic> mic = lookup.getMicByCountryCode("SE");
        assertEquals(41, mic.count());
    }

    @Test
    public void testActiveMic() throws IOException {
        MicLookup lookup = MicLookup.getInstance(false);
        List<Mic> mics = lookup.getAll()
                .filter(Mic::isActive)
                .collect(Collectors.toList());

        assertFalse(mics.isEmpty());
    }

}

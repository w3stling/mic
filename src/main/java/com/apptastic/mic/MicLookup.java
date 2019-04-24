
/*
 * MIT License
 *
 * Copyright (c) 2018, Apptastic Software
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.apptastic.mic;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;


/**
 * Class for doing Market Identifier Codes (MIC) lookup.
 */
public class MicLookup {
    private static MicLookup instance;
    private static boolean instanceDownloaded;
    private final boolean isDownloaded;
    private final Map<String, Mic> byMic;
    private final Map<String, List<Mic>> byOperatingMic;
    private final Map<String, List<Mic>> byCountryCode;
    private final List<Mic> micList;


    private MicLookup(List<Mic> micList, boolean isDownloaded) {
        this.isDownloaded = isDownloaded;
        this.micList = micList;
        byMic = new HashMap<>();
        byOperatingMic = new HashMap<>();
        byCountryCode = new HashMap<>();

        for (Mic mic : micList) {
            byMic.put(mic.getMic(), mic);
            byOperatingMic.computeIfAbsent(mic.getOperatingMic(), key -> new ArrayList<>()).add(mic);
            byCountryCode.computeIfAbsent(mic.getCountryCode(), key -> new ArrayList<>()).add(mic);

        }
    }

    /**
     * Check if a list of the latest mic was downloaded.
     * @return true if download the latest MICs otherwise false
     */
    public boolean isDownloaded() {
        return isDownloaded;
    }

    /**
     * Number of MICs.
     * @return size
     */
    public int size() {
        return micList.size();
    }

    /**
     * Get MIC entry by mic code.
     * @param mic - MIC code
     * @return mic
     */
    public Optional<Mic> getMic(String mic) {
        return Optional.ofNullable(byMic.get(mic));
    }

    /**
     * Get all MIC entries for a give operational MIC.
     * @param operationalMic operational mic
     * @return stream of MIC entries
     */
    public Stream<Mic> getMicByOperationalMic(String operationalMic) {
        var list = byOperatingMic.get(operationalMic);
        return list == null ? Stream.empty() : list.stream();
    }

    /**
     * Get all MIC entries for a give country code.
     * @param countryCode country code
     * @return stream of MIC entries
     */
    public Stream<Mic> getMicByCountryCode(String countryCode) {
        var list = byCountryCode.get(countryCode);
        return list == null ? Stream.empty() : list.stream();
    }

    /**
     * Get all known MIC entries.
     * @return stream of MIC entries
     */
    public Stream<Mic> getAll() {
        return micList.stream();
    }

    /**
     * Get instance for doing MIC lookups.
     * @param download - if true the latest list of mic will be downloaded. Otherwise a off line list is used
     * @return instance
     */
    public static MicLookup getInstance(boolean download) {
        if (instance != null && instanceDownloaded == download)
            return instance;

        var logger = Logger.getLogger("com.apptastic.mic");
        List<Mic> micList = Collections.emptyList();

        if (download) {
            try {
                final var url = new URL("https://www.iso20022.org/sites/default/files/ISO10383_MIC/ISO10383_MIC.csv");
                micList = read(url.openStream());
                instance = new MicLookup(micList, true);
                instanceDownloaded = true;
            }
            catch (IOException e) {
                logger.severe(e.getMessage());
            }
        }

        if (micList.isEmpty()) {
            ClassLoader classLoader = MicLookup.class.getClassLoader();

            try (InputStream inputstream = new FileInputStream(classLoader.getResource("ISO10383_MIC.csv").getFile())) {
                micList = read(new BufferedInputStream(inputstream));
                instance = new MicLookup(micList, false);
                instanceDownloaded = false;
            }
            catch (IOException e) {
                logger.severe(e.getMessage());
            }
        }

        return instance;
    }

    private static List<Mic> read(InputStream is) {
        List<Mic> micList = new ArrayList<>();

        try (
            final var reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            final var parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader())
        ) {
            for (final var record : parser) {
                Mic mic = parse(record);
                micList.add(mic);
            }
        }
        catch (Exception e) {
            var logger = Logger.getLogger("com.apptastic.mic");
            logger.severe(e.getMessage());
        }

        return micList;
    }

    private static Mic parse(CSVRecord record) {
        return new Mic(record.get("COUNTRY"),
                       record.get("ISO COUNTRY CODE (ISO 3166)"),
                       record.get("MIC"),
                       record.get("OPERATING MIC"),
                       record.get("O/S"),
                       record.get("NAME-INSTITUTION DESCRIPTION"),
                       record.get("ACRONYM"),
                       record.get("CITY"),
                       record.get("WEBSITE"),
                       record.get("STATUS DATE"),
                       record.get("STATUS"),
                       record.get("CREATION DATE"),
                       record.get("COMMENTS"));
    }

}

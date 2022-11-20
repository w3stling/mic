/*
 * MIT License
 *
 * Copyright (c) 2022, Apptastic Software
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
package com.apptasticsoftware.mic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;


/**
 * Class for doing Market Identifier Codes (MIC) lookup.
 */
public class MicLookup {
    private static final String URL = "https://www.iso20022.org/sites/default/files/ISO10383_MIC/ISO10383_MIC.csv";
    @SuppressWarnings("java:S5998")
    private static final Pattern COLUMN_PATTERN = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    private static MicLookup instance;
    private final boolean isDownloaded;
    private Map<String, Mic> byMic;
    private Map<String, List<Mic>> byOperatingMic;
    private Map<String, List<Mic>> byCountryCode;
    private List<Mic> micList;

    /**
     * Get instance for doing MIC lookups. It will try and download a list of the latest MICs.
     * If download fails an offline list of MICs will be used.
     * @return instance
     */
    public static MicLookup getInstance() {
        if (instance != null)
            return instance;

        try {
            instance = new MicLookup(true);
        }
        catch (Exception e) {
            try {
                instance = new MicLookup(false);
            } catch (Exception e2) {
                var logger = Logger.getLogger("com.apptastic.mic");
                logger.severe(e2.getMessage());
            }
        }

        return instance;
    }

    /**
     * Get instance for doing MIC lookups.
     * @param download - if true the latest list of MICs will be downloaded. Otherwise, an offline list is used
     * @return instance
     * @throws IOException exception
     */
    public static MicLookup getInstance(boolean download) throws IOException {
        if (instance != null && instance.isDownloaded() == download)
            return instance;

        instance = new MicLookup(download);
        return instance;
    }

    private MicLookup(boolean isDownloaded) throws IOException {
        this.isDownloaded = isDownloaded;

        micList = null;
        byMic = null;
        byOperatingMic = null;
        byCountryCode = null;

        InputStream inputStream;

        if (isDownloaded) {
            inputStream = getMicSourceFromWeb();
            micList = read(inputStream);
        }
        else {
            inputStream = getMicSourceFromFile();
            micList = read(new BufferedInputStream(inputStream));
        }

        byMic = new HashMap<>();
        byOperatingMic = new HashMap<>();
        byCountryCode = new HashMap<>();

        for (Mic mic : micList) {
            byMic.put(mic.getMic(), mic);
            byOperatingMic.computeIfAbsent(mic.getOperatingMic(), key -> new ArrayList<>()).add(mic);
            byCountryCode.computeIfAbsent(mic.getCountryCode(), key -> new ArrayList<>()).add(mic);

        }

        if (inputStream != null) {
            inputStream.close();
        }
    }

    private InputStream getMicSourceFromWeb() throws IOException {
        var url = new URL(URL);
        return url.openStream();
    }

    private InputStream getMicSourceFromFile() {
        return getClass().getResourceAsStream("/ISO10383_MIC.csv");
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
    public List<Mic> getMicByOperationalMic(String operationalMic) {
        var list = byOperatingMic.getOrDefault(operationalMic, Collections.emptyList());
        return Collections.unmodifiableList(list);
    }

    /**
     * Get all MIC entries for a give country code.
     * @param countryCode country code
     * @return stream of MIC entries
     */
    public List<Mic> getMicByCountryCode(String countryCode) {
        var list = byCountryCode.getOrDefault(countryCode, Collections.emptyList());
        return Collections.unmodifiableList(list);
    }

    /**
     * Get all known MIC entries.
     * @return stream of MIC entries
     */
    public List<Mic> getAll() {
        return Collections.unmodifiableList(micList);
    }

    private List<Mic> read(InputStream is) {
        List<Mic> mics = new ArrayList<>();

        try (
            final var reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
        ) {
            var firstLine = reader.readLine();
            String[] headers = toColumns(firstLine);
            if (headers.length != 17) {
                throw new IllegalArgumentException("Expected 17 columns but was " + headers.length + ".");
            }

            var line = reader.readLine();
            while (line != null) {
                Mic mic = parse(line);
                mics.add(mic);
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            var logger = Logger.getLogger("com.apptastic.mic");
            logger.severe(e.getMessage());
        }

        return mics;
    }

    private String[] toColumns(String text) {
        return COLUMN_PATTERN.split(text, -1);
    }

    private Mic parse(String line) {
        String[] columns = toColumns(line);
        return new Mic(prettyText(columns[0]),
                prettyText(columns[1]),
                prettyText(columns[2]),
                prettyText(columns[3]),
                prettyText(columns[4]),
                prettyText(columns[5]),
                prettyText(columns[6]),
                prettyText(columns[7]),
                prettyText(columns[8]),
                prettyText(columns[9]),
                prettyText(columns[10]),
                prettyText(columns[11]),
                prettyText(columns[12]),
                prettyText(columns[13]),
                prettyText(columns[14]),
                prettyText(columns[15]),
                prettyText(columns[16]));
    }

    private static String prettyText(String text) {
        if (text != null && text.length() > 0 && text.charAt(0) == '"' && text.charAt(text.length() - 1) == '"') {
            text = text.substring(1, text.length() - 1);
        }

        return text;
    }

}


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
import org.apache.commons.io.input.BOMInputStream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


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

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public int size() {
        return micList.size();
    }

    public Optional<Mic> getMic(String mic) {
        return Optional.ofNullable(byMic.get(mic));
    }

    public Stream<Mic> getMicByOperationalMic(String operationalMic) {
        List<Mic> list = byOperatingMic.get(operationalMic);
        return list == null ? Stream.empty() : list.stream();
    }

    public Stream<Mic> getMicByContryCode(String operationalMic) {
        List<Mic> list = byCountryCode.get(operationalMic);
        return list == null ? Stream.empty() : list.stream();
    }

    public Stream<Mic> getAll() {
        return micList.stream();
    }

    public static MicLookup getInstance(boolean download) {
        if (instance != null && instanceDownloaded == download)
            return instance;

        List<Mic> micList = Collections.emptyList();

        if (download) {
            try {
                final URL url = new URL("https://www.iso20022.org/sites/default/files/ISO10383_MIC/ISO10383_MIC.csv");
                micList = read(url.openStream());
                instance = new MicLookup(micList, true);
                instanceDownloaded = true;
            }
            catch (IOException e) {
                e.printStackTrace();
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
                e.printStackTrace();
            }
        }

        return instance;
    }

    private static List<Mic> read(InputStream is) {
        List<Mic> micList = new ArrayList<>();

        try (
                final Reader reader = new InputStreamReader(new BOMInputStream(is), StandardCharsets.UTF_8);
                final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader())
        ) {
            for (final CSVRecord record : parser) {
                Mic mic = parse(record);
                micList.add(mic);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return micList;
    }

    private static Mic parse(CSVRecord record) {
        return new Mic(record.get("COUNTRY"),
                       record.get("ISO COUNTRY CODE (ISO 3166)"),
                       record.get("MIC"),
                       record.get("OPERATING MIC"),
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

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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;
import java.util.Optional;


/**
 * ISO 10383 - Market Identifier Codes (MIC).
 */
public final class Mic {
    private static final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("yyyyMMdd").toFormatter();

    private final String micCode;
    private final String operatingMic;
    private final String marketType;
    private final String nameInstitutionDescription;
    private final String legalEntityName;
    private final String leiCode;
    private final String marketCategoryCode;
    private final String acronym;
    private final String countryCode;
    private final String city;
    private final String webSite;
    private final String status;
    private final String creationDate;
    private final String lastUpdateDate;
    private final String lastValidationDate;
    private final String expiryDate;
    private final String comments;


    @java.lang.SuppressWarnings("squid:S00107")
    public Mic(String micCode, String operatingMic, String marketType, String nameInstitutionDescription, String legalEntityName, String leiCode,
               String marketCategoryCode, String acronym, String countryCode, String city, String webSite, String status, String creationDate, String lastUpdateDate, String lastValidationDate, String expiryDate, String comments) {

        this.micCode = micCode;
        this.operatingMic = operatingMic;
        this.marketType = marketType;
        this.nameInstitutionDescription = nameInstitutionDescription;
        this.legalEntityName = noEmptyString(legalEntityName);
        this.leiCode = noEmptyString(leiCode);
        this.marketCategoryCode = noEmptyString(marketCategoryCode);
        this.acronym = noEmptyString(acronym);
        this.countryCode = countryCode;
        this.city = city;
        this.webSite = noEmptyString(webSite);
        this.status = status;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.lastValidationDate = noEmptyString(lastValidationDate);
        this.expiryDate = noEmptyString(expiryDate);
        this.comments = noEmptyString(comments);
    }

    private static String noEmptyString(String obj) {
        return (obj != null && !obj.isBlank()) ? obj : null;
    }

    /**
     * Market Identifier Code allocated to the market.
     * @return MIC
     */
    public String getMic() {
        return micCode;
    }

    /**
     * MIC representing the market at operating level.
     * @return operating MIC
     */
    public String getOperatingMic() {
        return operatingMic;
    }

    /**
     * OPRT (Operating) or SGMT (Segment) indicating whether the MIC is an operating MIC or a market segment MIC.
     * @return market type
     */
    public String getMarketType() {
        return marketType;
    }

    /**
     * Institution description of the corresponding market.
     * @return institution description
     */
    public String getNameInstitutionDescription() {
        return nameInstitutionDescription;
    }

    /**
     * Legal Entity Name.
     * @return Legal Entity Name
     */
    public Optional<String> getLegalEntityName() {
        return Optional.ofNullable(legalEntityName);
    }
    /**
     * LEI code.
     * @return LEI
     */
    public Optional<String> getLeiCode() {
        return Optional.ofNullable(leiCode);
    }

    /**
     * Market Category Code.
     * @return Market Category Code
     */
    public String getMarketCategoryCode() {
        return marketCategoryCode;
    }

    /**
     * Known acronym of the market.
     * @return acronym
     */
    public Optional<String> getAcronym() {
        return Optional.ofNullable(acronym);
    }

    /**
     * ISO country code (ISO 3166-1 alpha-2) of the country where the market is registered.
     * @return country code
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * City where the market is located.
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Website of the market.
     * @return URL to website
     */
    public Optional<String> getWebSite() {
        return Optional.ofNullable(webSite);
    }

    /**
     * Status of the MIC entry (ACTIVE, UPDATED, EXPIRED).
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Check if the status of the MIC is active.
     * @return true if active otherwise false
     */
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }

    /**
     * Check if the status of the MIC is updated.
     * @return true if active otherwise false
     */
    public boolean isUpdated() {
        return "UPDATED".equals(status);
    }

    /**
     * Check if the status of the MIC is expired.
     * @return true if active otherwise false
     */
    public boolean isExpired() {
        return "EXPIRED".equals(status);
    }

    /**
     * The creation date of corresponding MIC entry.
     * @return creation date
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * The creation date of corresponding MIC entry.
     * @return creation date
     */
    public LocalDate getCreationLocalDate() {
        return LocalDate.parse(creationDate, DATE_FORMAT);
    }

    /**
     * The last update date of corresponding MIC entry.
     * @return last update date
     */
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * The last update date of corresponding MIC entry.
     * @return last update date
     */
    public LocalDate getLastUpdateLocalDate() {
        return LocalDate.parse(lastUpdateDate, DATE_FORMAT);
    }

    /**
     * The last validation date of corresponding MIC entry.
     * @return last validation date
     */
    public Optional<String> getLastValidationDate() {
        return Optional.ofNullable(lastValidationDate);
    }

    /**
     * The last validation date of corresponding MIC entry.
     * @return last validation date
     */
    public Optional<LocalDate> getLastValidationLocalDate() {
        if (lastValidationDate != null) {
            return Optional.of(LocalDate.parse(lastUpdateDate, DATE_FORMAT));
        }
        return Optional.empty();
    }

    /**
     * The expiry date of corresponding MIC entry.
     * @return last validation date
     */
    public Optional<String> getExpiryDate() {
        return Optional.ofNullable(expiryDate);
    }

    /**
     * The expiry date of corresponding MIC entry.
     * @return last validation date
     */
    public Optional<LocalDate> getExpiryLocalDate() {
        if (expiryDate != null) {
            return Optional.of(LocalDate.parse(expiryDate, DATE_FORMAT));
        }
        return Optional.empty();
    }

    /**
     * Comments attached to corresponding MIC entry during its lifetime.
     * @return comments
     */
    public Optional<String> getComments() {
        return Optional.ofNullable(comments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mic mic = (Mic) o;
        return micCode.equals(mic.micCode) &&
               getOperatingMic().equals(mic.getOperatingMic()) &&
               getMarketType().equals(mic.getMarketType()) &&
               getNameInstitutionDescription().equals(mic.getNameInstitutionDescription()) &&
               Objects.equals(getLegalEntityName(), mic.getLegalEntityName()) &&
               Objects.equals(getLeiCode(), mic.getLeiCode()) &&
               Objects.equals(getMarketCategoryCode(), mic.getMarketCategoryCode()) &&
               Objects.equals(getAcronym(), mic.getAcronym()) &&
               getCountryCode().equals(mic.getCountryCode()) &&
               getCity().equals(mic.getCity()) &&
               Objects.equals(getWebSite(), mic.getWebSite()) &&
               getStatus().equals(mic.getStatus()) &&
               getCreationDate().equals(mic.getCreationDate()) &&
               getLastUpdateDate().equals(mic.getLastUpdateDate()) &&
               Objects.equals(getLastValidationDate(), mic.getLastValidationDate()) &&
               Objects.equals(getExpiryDate(), mic.getExpiryDate()) &&
               Objects.equals(getComments(), mic.getComments());

    }

    @Override
    public int hashCode() {
        return Objects.hash(micCode, getOperatingMic(), getMarketType(), getNameInstitutionDescription(),
                getLegalEntityName(), getLeiCode(), getMarketCategoryCode(), getAcronym(), getCountryCode(),
                getCity(), getWebSite(), getStatus(), getCreationDate(), getLastUpdateDate(),
                getLastValidationDate(), getExpiryDate(), getComments());
    }

}

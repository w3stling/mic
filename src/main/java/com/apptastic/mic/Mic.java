
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

import java.util.Objects;
import java.util.Optional;


public final class Mic {
    private final String country;
    private final String countryCode;
    private final String micCode;
    private final String operatingMic;
    private final String marketType;
    private final String nameInstitutionDescription;
    private final String acronym;
    private final String city;
    private final String webSite;
    private final String statusDate;
    private final String status;
    private final String creationDate;
    private final String comments;

    @java.lang.SuppressWarnings("squid:S00107")
    public Mic(String country, String countryCode, String mic, String operatingMic, String marketType, String nameInstitutionDescription,
               String acronym, String city, String webSite, String statusDate, String status, String creationDate, String comments) {

        this.country = country;
        this.countryCode = countryCode;
        this.micCode = mic;
        this.operatingMic = operatingMic;
        this.marketType = marketType;
        this.nameInstitutionDescription = nameInstitutionDescription;
        this.acronym = noEmptyString(acronym);
        this.city = city;
        this.webSite = noEmptyString(webSite);
        this.statusDate = statusDate;
        this.status = status;
        this.creationDate = creationDate;
        this.comments = noEmptyString(comments);
    }

    private static String noEmptyString(String obj) {
        return (obj != null && !obj.isEmpty()) ? obj : null;
    }

    /**
     * Country where the market is registered
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * ISO country code (ISO 3166-1 alpha-2) of the country where the market is registered
     * @return country code
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Market Identifier Code allocated to the market
     * @return MIC
     */
    public String getMic() {
        return micCode;
    }

    /**
     * MIC representing the market at operating level
     * @return operating MIC
     */
    public String getOperatingMic() {
        return operatingMic;
    }

    /**
     * O (Operating) or S (Segment) indicating whether the MIC is an operating MIC or a market segment MIC
     * @return market type
     */
    public String getMarketType() {
        return marketType;
    }

    public String getNameInstitutionDescription() {
        return nameInstitutionDescription;
    }

    /**
     * Known acronym of the market
     * @return acronym
     */
    public Optional<String> getAcronym() {
        return Optional.ofNullable(acronym);
    }

    /**
     * City where the market is located
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Website of the market
     * @return URL to website
     */
    public Optional<String> getWebSite() {
        return Optional.ofNullable(webSite);
    }

    public String getStatusDate() {
        return statusDate;
    }

    public String getStatus() {
        return status;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Optional<String> getComments() {
        return Optional.ofNullable(comments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Mic)) return false;

        Mic that = (Mic) o;
        return Objects.equals(getCountry(), that.getCountry()) &&
                Objects.equals(getCountryCode(), that.getCountryCode()) &&
                Objects.equals(getMic(), that.getMic()) &&
                Objects.equals(getOperatingMic(), that.getOperatingMic()) &&
                Objects.equals(getMarketType(), that.getMarketType()) &&
                Objects.equals(getNameInstitutionDescription(), that.getNameInstitutionDescription()) &&
                Objects.equals(getAcronym(), that.getAcronym()) &&
                Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getWebSite(), that.getWebSite()) &&
                Objects.equals(getStatusDate(), that.getStatusDate()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getCreationDate(), that.getCreationDate()) &&
                Objects.equals(getComments(), that.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountry(), getCountryCode(), getMic(), getOperatingMic(), getMarketType(), getNameInstitutionDescription(),
                getAcronym(), getCity(), getWebSite(), getStatusDate(), getStatus(), getCreationDate(), getComments());
    }

}

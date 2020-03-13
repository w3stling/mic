Market Identifier Codes (MIC)
=============================

[![Build Status](https://travis-ci.org/w3stling/mic.svg?branch=master)](https://travis-ci.org/w3stling/mic)
[![Download](https://api.bintray.com/packages/apptastic/maven-repo/mic/images/download.svg)](https://bintray.com/apptastic/maven-repo/mic/_latestVersion)
[![Javadoc](https://img.shields.io/badge/javadoc-1.2.11-blue.svg)](https://w3stling.github.io/mic/javadoc/1.2.11)
[![License](http://img.shields.io/:license-MIT-blue.svg?style=flat-round)](http://apptastic-software.mit-license.org)   
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.apptastic%3Amic&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.apptastic%3Amic)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.apptastic%3Amic&metric=coverage)](https://sonarcloud.io/component_measures?id=com.apptastic%3Amic&metric=Coverage)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=com.apptastic%3Amic&metric=bugs)](https://sonarcloud.io/component_measures?id=com.apptastic%3Amic&metric=bugs)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=com.apptastic%3Amic&metric=vulnerabilities)](https://sonarcloud.io/component_measures?id=com.apptastic%3Amic&metric=vulnerabilities)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.apptastic%3Amic&metric=code_smells)](https://sonarcloud.io/component_measures?id=com.apptastic%3Amic&metric=code_smells)

The ISO 10383 specifies a universal method of identifying exchanges, trading platforms, regulated or non-regulated markets and trade reporting facilities as sources of prices and related information in order to facilitate automated processing.

This Java library makes it easy to lookup MIC information.

Examples
--------
Download list of last MICs and get MIC with name XSTO. If the download fails an offline list of MICs is used.
```java
MicLookup lookup = MicLookup.getInstance();
Optional<Mic> mic = lookup.getMic("XSTO");
```

Use an offline list of MICs and get all active MICs
```java
MicLookup lookup = MicLookup.getInstance(false);
List<Mic> mics = lookup.getAll()
                       .filter(Mic::isActive)
                       .collect(Collectors.toList());
```


Download
--------

Download [the latest JAR][1] or grab via [Maven][2] or [Gradle][3].

### Maven setup
Add JCenter repository for resolving artifact:
```xml
<project>
    ...
    <repositories>
        <repository>
            <id>jcenter</id>
            <url>https://jcenter.bintray.com</url>
        </repository>
    </repositories>
    ...
</project>
```

Add dependency declaration:
```xml
<project>
    ...
    <dependencies>
        <dependency>
            <groupId>com.apptastic</groupId>
            <artifactId>mic</artifactId>
            <version>1.2.11</version>
        </dependency>
    </dependencies>
    ...
</project>
```

### Gradle setup
Add JCenter repository for resolving artifact:
```groovy
repositories {
    jcenter()
}
```

Add dependency declaration:
```groovy
dependencies {
    implementation 'com.apptastic:mic:1.2.11'
}
```

Mic library requires at minimum Java 11.

License
-------

    MIT License
    
    Copyright (c) 2019, Apptastic Software
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.


[1]: https://bintray.com/apptastic/maven-repo/mic/_latestVersion
[2]: https://maven.apache.org
[3]: https://gradle.org
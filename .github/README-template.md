Market Identifier Codes (MIC)
=============================

[![Build](https://github.com/w3stling/mic/actions/workflows/build.yml/badge.svg)](https://github.com/w3stling/mic/actions/workflows/build.yml)
[![Download](https://img.shields.io/badge/download-2.0.0-brightgreen.svg)](https://search.maven.org/artifact/com.apptasticsoftware/mic/2.0.0/jar)
[![Javadoc](https://img.shields.io/badge/javadoc-2.0.0-blue.svg)](https://w3stling.github.io/mic/javadoc/2.0.0)
[![License](http://img.shields.io/:license-MIT-blue.svg?style=flat-round)](http://apptastic-software.mit-license.org)   
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=w3stling_mic&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=w3stling_mic)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=w3stling_mic&metric=coverage)](https://sonarcloud.io/summary/new_code?id=w3stling_mic)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=w3stling_mic&metric=bugs)](https://sonarcloud.io/summary/new_code?id=w3stling_mic)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=w3stling_mic&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=w3stling_mic)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=w3stling_mic&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=w3stling_mic)

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
            <version>1.3.5</version>
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
    implementation 'com.apptastic:mic:1.3.5'
}
```

Mic library requires at minimum Java 11.

License
-------

    MIT License
    
    Copyright (c) 2020, Apptastic Software
    
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
package com.apptasticsoftware.mic;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class MicTest {

    @Test
    void simpleEqualsContract() {
        EqualsVerifier.simple().forClass(Mic.class).verify();
    }

}

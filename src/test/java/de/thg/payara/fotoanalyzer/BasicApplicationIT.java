package de.thg.payara.fotoanalyzer;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.testcontainers.shaded.org.apache.commons.lang3.BooleanUtils.isTrue;

@Testcontainers
public class BasicApplicationIT{

    @Container
     GenericContainer microContainer = new GenericContainer("payara/micro:6.2024.5-jdk21")
            .withExposedPorts(8080);

    @Test
    public void checkContainerIsRunning(){
        assert(isTrue(microContainer.isRunning()));
    }
}
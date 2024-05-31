package de.thg.payara.fotoanalyzer;

import de.thg.payara.fotoanalyzer.model.Image;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static jakarta.ws.rs.client.ClientBuilder.newClient;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class BasicApplicationIT{

    MountableFile warFile = MountableFile.forHostPath(Paths.get("build/libs/FotoAnalyzer-Payara-Micro-0.1-SNAPSHOT.war").toAbsolutePath(), 0777);

    @Container
     GenericContainer microContainer = new GenericContainer("payara/micro:6.2024.5-jdk21")
            .withExposedPorts(8080)
            .withCopyFileToContainer(warFile, "/opt/payara/deployments/app.war")
            .waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1))
            .withCommand("--deploy /opt/payara/deployments/app.war --contextRoot /");

    @Test
    void checkContainerIsRunning(){
        assertThat(microContainer.isRunning()).isTrue();
    }

    @Test
    void someTest() {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("testdata/PHOTO0021.JPG");
        MultiPart multiPart = new MultiPart();
        BodyPart bodyPart = new StreamDataBodyPart("file", inputStream, "PHOTO0021.JPG");
        multiPart.bodyPart(bodyPart);
        Image result = newClient()
                .target(String.format("http://%s:%d/", microContainer.getHost(), microContainer.getMappedPort(8080)))
                .path("analyze_image")
                .request()
                .post(Entity.entity(multiPart, MediaType.MULTIPART_FORM_DATA_TYPE), Image.class);
        Image expected = new Image();
        expected.setCreationDate(LocalDateTime.of(2015, 11, 22, 11, 04, 37));
        expected.setLatitude(48.15315222222222);
        expected.setLongitude(11.591775555555555);
        expected.setFilename("PHOTO0021.JPG");
        assertThat(result).isEqualTo(expected);

    }
}
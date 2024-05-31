package de.thg.payara.fotoanalyzer.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class LocalDateTimeConverter {

    String extension;

    @Inject
    public LocalDateTimeConverter(@ConfigProperty(name = "app.extension", defaultValue = "jpg") String extension) {
        this.extension = extension;
    }

    private static final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
    private static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public LocalDateTime toLocalDateTime(String stringRepresentation) {
        return LocalDateTime.parse(stringRepresentation, formatter1);
    }

    public String toFilename(LocalDateTime ldt) {
        return ldt.format(formatter2) + "." + extension;
    }

    public String toFilename(String stringRepresentation) {
        return LocalDateTime.parse(stringRepresentation, formatter1).format(formatter2) + "." + extension;
    }
}

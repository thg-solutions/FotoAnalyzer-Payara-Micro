package de.thg.payara.fotoanalyzer.services;

import de.thg.payara.fotoanalyzer.model.Image;
import de.thg.payara.fotoanalyzer.util.LocalDateTimeConverter;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@RequestScoped
public class FotoAnalyzerService {

    @Inject
    private ImageMetadataReader imageMetadataReader;

    @Inject
    private LocalDateTimeConverter localDateTimeConverter;

    public Optional<Image> analyseImage(InputStream inputStream, String originalName) {
        return Optional.ofNullable(imageMetadataReader.readImageMetadata(inputStream, originalName));
    }

    public Optional<Map<String, String>> createNameByCreationDate(InputStream inputStream, String originalName) {
        Optional<Image> image = analyseImage(inputStream, originalName);
        return image.map(value -> Map.of(value.getFilename(), localDateTimeConverter.toFilename(value.getCreationDate())));
    }

}

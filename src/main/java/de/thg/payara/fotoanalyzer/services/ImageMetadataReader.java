package de.thg.payara.fotoanalyzer.services;

import de.thg.payara.fotoanalyzer.model.Image;

import java.io.InputStream;

public interface ImageMetadataReader {

    Image readImageMetadata(InputStream inputStream, String originalName);

}

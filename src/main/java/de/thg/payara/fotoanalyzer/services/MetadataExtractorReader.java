package de.thg.payara.fotoanalyzer.services;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifReader;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import de.thg.payara.fotoanalyzer.model.Image;
import de.thg.payara.fotoanalyzer.util.LocalDateTimeConverter;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
public class MetadataExtractorReader implements ImageMetadataReader {

    private static final Logger LOGGER = Logger.getLogger(MetadataExtractorReader.class.getName());

    @Inject
    private LocalDateTimeConverter localDateTimeConverter;

    @Override
    public Image readImageMetadata(InputStream inputStream, String originalName) {
        Image image = new Image();
        image.setFilename(originalName);
        try (inputStream) {
            Metadata metadata = JpegMetadataReader.readMetadata(inputStream, List.of(new ExifReader()));
            if(metadata.getDirectoryCount() == 0) {
                return null;
            }
            Directory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            image.setCreationDate(localDateTimeConverter.toLocalDateTime(directory.getString(ExifSubIFDDirectory.TAG_DATETIME_DIGITIZED)));
            GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            if (gpsDirectory != null) {
                GeoLocation geoLocation = gpsDirectory.getGeoLocation();
                if (geoLocation != null) {
                    image.setLatitude(geoLocation.getLatitude());
                    image.setLongitude(geoLocation.getLongitude());
                }
            }
        } catch (JpegProcessingException | IOException e) {
            LOGGER.log(Level.SEVERE, "error handling image", e);
            return null;
        }
        return image;
    }
}

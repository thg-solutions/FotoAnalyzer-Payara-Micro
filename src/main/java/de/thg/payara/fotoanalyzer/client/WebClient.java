package de.thg.payara.fotoanalyzer.client;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import java.io.File;
import java.util.Map;
import java.util.Objects;

public class WebClient {

    public static void main(String[] args) {
        File parent = new File(args[0]);
        if (!parent.isDirectory()) {
            return;
        }

        MultiPart multiPart = new MultiPart();
        for (File fileEntry : Objects.requireNonNull(parent.listFiles(f -> f.getName().endsWith("jpg") || f.getName().endsWith("JPG")))) {
            BodyPart bodyPart = new FileDataBodyPart("files", fileEntry);
            multiPart.bodyPart(bodyPart);
        }

        try (Client client = ClientBuilder.newClient()) {
            Map<String, String> result;
            try (Response webTarget = client
                    .target("http://localhost:8080")
                    .path("rename_images").request()
                    .post(Entity.entity(multiPart, MediaType.MULTIPART_FORM_DATA_TYPE))) {
                result = webTarget.readEntity(Map.class);
            }
            for (Map.Entry<String, String> entry : result.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                new File(parent, entry.getKey()).renameTo(new File(parent, entry.getValue()));
            }
        }
    }
}


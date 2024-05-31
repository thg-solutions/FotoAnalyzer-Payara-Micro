/*
 * FastAPI
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 0.1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package de.thg.payara.fotoanalyzer.api;

import de.thg.payara.fotoanalyzer.model.Image;
import de.thg.payara.fotoanalyzer.services.FotoAnalyzerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaHelidonServerCodegen", date = "2024-05-24T05:07:51.083708Z[Etc/UTC]")
public class FotoAnalyzerController {

    private final FotoAnalyzerService service;

    @Inject
    public FotoAnalyzerController(FotoAnalyzerService service) {
        this.service = service;
    }

    @POST
    @Path("/analyze_image")
    @Consumes({ "multipart/form-data" })
    @Produces({ "application/json" })
    public Response analyzeImage(@FormDataParam(value = "file") FormDataBodyPart formDataBodyPart) throws IOException {
        try(InputStream stream = formDataBodyPart.getContent()) {
            Optional<Image> result = service.analyseImage(stream, formDataBodyPart.getFileName().get());
            if(result.isPresent()) {
                return Response.ok(result, MediaType.APPLICATION_JSON_TYPE).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
    }

    @POST
    @Path("/rename_images")
    @Consumes( MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> renameImages(@FormDataParam("files") List<FormDataBodyPart> files) {
        Map<String, String> result = new HashMap<>();
        for (FormDataBodyPart file : files) {
            Optional<Map<String, String>> mapEntry = service.createNameByCreationDate(file.getContent(), file.getFileName().get());
            mapEntry.ifPresent(result::putAll);
        }
        return result;
    }
}

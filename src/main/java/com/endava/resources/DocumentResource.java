package com.endava.resources;

import com.endava.config.ServiceConfiguration;
import com.endava.model.Document;
import com.endava.services.DocumentService;
import com.endava.config.PATCH;
import com.endava.dto.DocumentDTO;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.multipart.file.MediaTypePredictor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.io.*;
import java.util.List;

@Path(value = "/documents")
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class DocumentResource {

    @Autowired
    private ServiceConfiguration conf;
    @Autowired
    private DocumentService service;
    @Autowired
    private Environment env;

    @Autowired
    public DocumentResource(ServiceConfiguration conf, DocumentService service) {
        this.conf = conf;
        this.service = service;
    }

    // GET ALL
    @GET
    public Response readAll() {
        List<Document> response = service.readAll();
        return Response.ok(response, MediaType.APPLICATION_JSON).build();
    }

    // GET ONE - by id
    @GET
    @Path(value = "/{_id}")
    public Response readOne(@PathParam("_id") String id) {
        Document response = service.read(id);
        return Response.ok(response, MediaType.APPLICATION_JSON).build();
    }

    // CREATE ONE - with request body
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDoc(@Valid DocumentDTO dto) {
        Response response = null;
        try {
            response = Response.status(201)
                    .header(
                            "Location",
                            String.format(
                                    "%s",
                                    service.create(dto.getName())
                            )
                    )
                    .entity(service.create(dto.getName()))
                    .build();
        } catch (Exception e) {
            response = Response.status(500).entity(e.getMessage()).build();
        }
        return response;
    }

    // UPDATE ONE - by ID - with request body
    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Path(value = "/{_id}")
    public Response updateDoc(@PathParam("_id") String id,
                              @Valid DocumentDTO dto) {
        service.update(id, dto.getName());
        String response = "Document with 'id': " + id + ", was updated succesfully!";
        return Response.ok(response, MediaType.APPLICATION_XML).build();
    }

    // DELETE ONE - by id
    @DELETE
    @Path(value = "/{_id}")
    public Response deleteDoc(@PathParam("_id") String id) {
        service.delete(id);
        String response = "Document with '_id': " + id
                + ", was deleted succesfully!";
        return Response.ok(response, MediaType.APPLICATION_XML).build();
    }


    // UPLOAD FILE
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    public Response uploadFile(
            @FormDataParam("document") String document,
            @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {

        String uploadedFileLocation = env.getProperty("fileLocation")
                + fileDetail.getFileName();
        String fileName = fileDetail.getFileName();

        String output = "File uploaded to: " + uploadedFileLocation;
        if (service.writeToFile(uploadedFileLocation, document, fileName) == true) {
            return Response.status(200).entity(output).build();
        } else return Response.status(400).entity("Bad request: document must contain a name!").build();
    }

    // GET file from location
    @GET
    @Path("/download/{fileName}")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response getFile(@PathParam("fileName") String fileName) {

        File file = new File(env.getProperty("fileLocation") + fileName);
        ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment; filename="
                + fileName);
        return response.build();
    }
}

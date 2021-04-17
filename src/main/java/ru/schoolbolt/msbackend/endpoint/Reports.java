package ru.schoolbolt.msbackend.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.schoolbolt.msbackend.data.Database;

import java.sql.SQLException;

@Path("/reports")
public class Reports {
    @Inject
    public Database database;

    @GET
    @Path("/full")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFullProductListReport(
            @QueryParam("name") final String name
    ) throws SQLException, JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return Response
                .ok(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                        database.getProductRepository().getProducts(name))
                ).header(
                        "Content-Disposition",
                        "attachment; filename=\"full.json\""
                ).build();
    }

    @GET
    @Path("/storage")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getProductListInStorageReport(
            @QueryParam("name") final String name
    ) throws SQLException, JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return Response
                .ok(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                        database.getProductRepository().getProductsInStorage(name))
                ).header(
                        "Content-Disposition",
                        "attachment; filename=\"storage.json\""
                ).build();
    }
}

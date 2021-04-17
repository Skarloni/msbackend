package ru.schoolbolt.msbackend.endpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.schoolbolt.msbackend.data.Database;
import ru.schoolbolt.msbackend.data.document.EDocumentType;

import java.io.File;
import java.sql.SQLException;

@Path("/documents")
public class Documents {
    @Inject
    public Database database;

    @GET
    @Path("/receipt")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReceiptNumbers() throws SQLException {
        return Response.ok(database.getUploadRepository().getNumbers(EDocumentType.RECEIPT)).build();
    }

    @GET
    @Path("/sale")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSaleNumbers() throws SQLException {
        return Response.ok(database.getUploadRepository().getNumbers(EDocumentType.SALE)).build();
    }

    @GET
    @Path("/transfer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransferNumbers() throws SQLException {
        return Response.ok(database.getUploadRepository().getNumbers(EDocumentType.TRANSFER)).build();
    }

    @GET
    @Path("/receipt/{number}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getReceipt(@PathParam("number") final int number) throws SQLException {
        return Response
                .ok(new File("resources/" + database.getUploadRepository().get(number)))
                .header(
                        "Content-Disposition",
                        "attachment; filename=\"receipt.json\""
                )
                .build();
    }

    @GET
    @Path("/sale/{number}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getSale(@PathParam("number") final int number) throws SQLException {
        return Response
                .ok(new File("resources/" + database.getUploadRepository().get(number)))
                .header(
                        "Content-Disposition",
                        "attachment; filename=\"sale.json\""
                )
                .build();
    }

    @GET
    @Path("/transfer/{number}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getTransfer(@PathParam("number") final int number) throws SQLException {
        return Response
                .ok(new File("resources/" + database.getUploadRepository().get(number)))
                .header(
                        "Content-Disposition",
                        "attachment; filename=\"transfer.json\""
                )
                .build();
    }
}

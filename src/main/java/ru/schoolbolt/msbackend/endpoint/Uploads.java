package ru.schoolbolt.msbackend.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import ru.schoolbolt.msbackend.data.Database;
import ru.schoolbolt.msbackend.data.document.*;
import ru.schoolbolt.msbackend.data.model.Product;

import java.io.*;
import java.sql.SQLException;
import java.util.UUID;

@Path("/uploads")
public class Uploads {
    @Inject
    public Database database;

    @POST
    @Path("/receipt")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadReceipt(
            @FormDataParam("file") final InputStream input,
            @FormDataParam("file") final FormDataContentDisposition meta
    ) throws SQLException, IOException {
        try (final Reader reader = new InputStreamReader(input)) {
            final ObjectMapper mapper = new ObjectMapper();

            final Receipt receipt = mapper.readValue(
                    reader,
                    Receipt.class
            );

            if (receipt.number == 0) {
                return Response.status(
                        Response.Status.BAD_REQUEST
                ).entity("Number is undefined").build();
            }

            if (receipt.storage == null || receipt.storage.length() == 0) {
                return Response.status(
                        Response.Status.BAD_REQUEST
                ).entity("Storage is undefined").build();
            }

            for (final Product product : receipt.products) {
                if (
                        product.code == 0 || product.name == null
                                || product.price == 0 || product.count == 0
                ) {
                    return Response.status(
                            Response.Status.BAD_REQUEST
                    ).entity("Product is undefined").build();
                }
                product.sellingPrice = 0;
            }

            uploadDocument(
                    receipt,
                    EDocumentType.RECEIPT
            );

            for (final Product product : receipt.products) {
                database.getProductRepository().postProduct(product);
                database.getProductRepository().postProductInStorage(
                        receipt.storage,
                        product.code,
                        +product.count
                );
            }

            return Response.ok().build();
        }
    }

    @POST
    @Path("/sale")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSale(
            @FormDataParam("file") final InputStream input,
            @FormDataParam("file") final FormDataContentDisposition meta
    ) throws SQLException, IOException {
        try (final Reader reader = new InputStreamReader(input)) {
            final ObjectMapper mapper = new ObjectMapper();

            final Sale sale = mapper.readValue(
                    reader,
                    Sale.class
            );

            if (sale.number == 0) {
                return Response.status(
                        Response.Status.BAD_REQUEST
                ).entity("Number is undefined").build();
            }

            if (sale.storage == null || sale.storage.length() == 0) {
                return Response.status(
                        Response.Status.BAD_REQUEST
                ).entity("Storage is undefined").build();
            }

            for (final Product product : sale.products) {
                if (
                        product.code == 0 || product.name == null
                                || product.sellingPrice == 0 || product.count == 0
                ) {
                    return Response.status(
                            Response.Status.BAD_REQUEST
                    ).entity("Product is undefined").build();
                }
                product.price = 0;
            }

            uploadDocument(
                    sale,
                    EDocumentType.SALE
            );

            for (final Product product : sale.products) {
                database.getProductRepository().patchProduct(product);
                database.getProductRepository().patchProductCount(
                        sale.storage,
                        product.code,
                        -product.count
                );
            }

            return Response.ok().build();
        }
    }

    @POST
    @Path("/transfer")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadTransfer(
            @FormDataParam("file") final InputStream input,
            @FormDataParam("file") final FormDataContentDisposition meta
    ) throws SQLException, IOException {
        try (final Reader reader = new InputStreamReader(input)) {
            final ObjectMapper mapper = new ObjectMapper();

            final Transfer transfer = mapper.readValue(
                    reader,
                    Transfer.class
            );

            if (transfer.number == 0) {
                return Response.status(
                        Response.Status.BAD_REQUEST
                ).entity("Number is undefined").build();
            }

            if (
                    transfer.from == null || transfer.from.length() == 0
                            || transfer.to == null || transfer.to.length() == 0
            ) {
                return Response.status(
                        Response.Status.BAD_REQUEST
                ).entity("Storage is undefined").build();
            }

            for (final Product product : transfer.products) {
                if (
                        product.code == 0 || product.name == null || product.count == 0
                ) {
                    return Response.status(
                            Response.Status.BAD_REQUEST
                    ).entity("Product is undefined").build();
                }
                product.price = 0;
                product.sellingPrice = 0;
            }

            uploadDocument(
                    transfer,
                    EDocumentType.TRANSFER
            );

            for (final Product product : transfer.products) {
                database.getProductRepository().patchProductCount(
                        transfer.from,
                        product.code,
                        -product.count
                );
                database.getProductRepository().postProductInStorage(
                        transfer.to,
                        product.code,
                        +product.count
                );
            }

            return Response.ok().build();
        }
    }

    private <T extends IDocument> void uploadDocument(
            final T document,
            final EDocumentType type
    ) throws IOException, SQLException {
        final UUID uuid = UUID.randomUUID();

        database.getUploadRepository().upload(
                document.getNumber(),
                uuid,
                type
        );

        try (final Writer writer = new FileWriter("./resources/" + uuid)) {
            final ObjectMapper mapper = new ObjectMapper();

            mapper.writerWithDefaultPrettyPrinter().writeValue(
                    writer,
                    document
            );
        }
    }
}

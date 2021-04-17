package ru.schoolbolt.msbackend.endpoint;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.schoolbolt.msbackend.data.Database;
import ru.schoolbolt.msbackend.data.model.Product;

import java.sql.SQLException;

@Path("/storages/{storageName}/products")
public class Products {
    @Inject
    public Database database;

    @NotNull
    @PathParam("storageName")
    public String storageName;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() throws SQLException {
        return Response.ok(database.getProductRepository().getProductsInStorage(storageName)).build();
    }

    @DELETE
    @Path("/{productCode}")
    public Response deleteProduct(@PathParam("productCode") final int productCode) throws SQLException {
        database.getProductRepository().deleteProduct(productCode);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postProduct(
            @NotNull final Product product
    ) throws SQLException {
        database.getProductRepository().postProduct(product);
        database.getProductRepository().postProductInStorage(
                storageName,
                product.code,
                1
        );
        return Response.ok().build();
    }

    @PATCH
    @Path("/{productCode}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response patchProduct(
            @PathParam("productCode") final int productCode,
            @NotNull final Product product
    ) throws SQLException {
        product.code = productCode;
        database.getProductRepository().patchProduct(product);
        return Response.ok().build();
    }
}

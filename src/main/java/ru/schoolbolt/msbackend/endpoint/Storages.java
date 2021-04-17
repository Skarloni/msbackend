package ru.schoolbolt.msbackend.endpoint;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.schoolbolt.msbackend.data.Database;
import ru.schoolbolt.msbackend.data.model.Storage;

import java.sql.SQLException;

@Path("/storages")
public class Storages {
    @Inject
    public Database database;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStorages() throws SQLException {
        return Response.ok(database.getStorageRepository().getStorages()).build();
    }

    @DELETE
    @Path("/{storageName}")
    public Response deleteStorage(@PathParam("storageName") final String storageName) throws SQLException {
        database.getStorageRepository().deleteStorage(storageName);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postStorage(@NotNull final Storage storage) throws SQLException {
        database.getStorageRepository().postStorage(storage);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{storageName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response patchStorage(
            @PathParam("storageName") final String storageName,
            @NotNull final Storage storage
    ) throws SQLException {
        database.getStorageRepository().patchStorage(
                storageName,
                storage
        );
        return Response.ok().build();
    }
}

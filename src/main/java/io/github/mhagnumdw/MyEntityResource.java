package io.github.mhagnumdw;

import java.util.List;
import java.util.Optional;

import com.github.f4b6a3.ulid.Ulid;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/my-entity")
public class MyEntityResource {

    // curl -i -XPOST http://localhost:8080/my-entity | jq -R '. as $raw | try fromjson catch $raw'
    @Transactional
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public MyEntity create() {
        MyEntity myEntity = new MyEntity();
        myEntity.persist();
        return myEntity;
    }

    // curl -i http://localhost:8080/my-entity | jq -R '. as $raw | try fromjson catch $raw'
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MyEntity> list() {
        return MyEntity.findAll().list();
    }

    // curl -i http://localhost:8080/my-entity/external-id/01JTXZ23HE3K9KK5VYZC7XJZVE | jq -R '. as $raw | try fromjson catch $raw'
    @GET
    @Path("/external-id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByExternalId(Ulid id) {
        Optional<MyEntity> feriado = MyEntity.find("externalIdUlid", id).singleResultOptional();
        if (feriado.isEmpty()) {
            return Response.status(404).build();
        }
        return Response.ok(feriado.get()).build();
    }
}

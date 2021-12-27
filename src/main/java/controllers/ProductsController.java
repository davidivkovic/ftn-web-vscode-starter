package controllers;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import services.ProductsService;

@Path("/products")
public class ProductsController {

    @Inject
    ProductsService productsService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response products() {
        return Response.ok(productsService.getProducts()).build();
    }

}

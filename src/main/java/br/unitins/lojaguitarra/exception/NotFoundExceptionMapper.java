package br.unitins.lojaguitarra.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    private static final String TYPE = "/problems/resource-not-found";

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(NotFoundException exception) {
        return ProblemDetailFactory.build(
                Response.Status.NOT_FOUND,
                TYPE,
                "Recurso nao encontrado",
                exception.getMessage(),
                uriInfo);
    }
}

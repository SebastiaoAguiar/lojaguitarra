package br.unitins.lojaguitarra.exception;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    private static final String TYPE = "/problems/bad-request";

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(BadRequestException exception) {
        return ProblemDetailFactory.build(
                Response.Status.BAD_REQUEST,
                TYPE,
                "Requisicao invalida",
                exception.getMessage(),
                uriInfo);
    }
}

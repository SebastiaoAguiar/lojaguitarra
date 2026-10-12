package br.unitins.lojaguitarra.exception;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BusinessValidationExceptionMapper implements ExceptionMapper<BusinessValidationException> {

    private static final String TYPE = "/problems/validation-error";

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(BusinessValidationException exception) {
        return ProblemDetailFactory.build(
                exception.getStatus(),
                TYPE,
                "Erro de validacao",
                exception.getMessage(),
                uriInfo,
                exception.getViolations());
    }
}

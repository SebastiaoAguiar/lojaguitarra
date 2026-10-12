package br.unitins.lojaguitarra.exception;

import java.util.Comparator;
import java.util.List;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final String TYPE = "/problems/validation-error";

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<ViolationDetail> violations = exception.getConstraintViolations().stream()
                .sorted(Comparator.comparing(violation -> violation.getPropertyPath().toString()))
                .map(this::toViolationDetail)
                .toList();

        return ProblemDetailFactory.build(
                Response.Status.BAD_REQUEST,
                TYPE,
                "Erro de validacao",
                "Um ou mais campos da requisicao sao invalidos.",
                uriInfo,
                violations);
    }

    private ViolationDetail toViolationDetail(ConstraintViolation<?> violation) {
        return new ViolationDetail(extractField(violation), violation.getMessage());
    }

    private String extractField(ConstraintViolation<?> violation) {
        String path = violation.getPropertyPath().toString();
        int lastSeparator = path.lastIndexOf('.');
        if (lastSeparator >= 0 && lastSeparator + 1 < path.length()) {
            return path.substring(lastSeparator + 1);
        }
        return path;
    }
}

package br.unitins.lojaguitarra.exception;

import java.util.List;

import jakarta.ws.rs.core.Response;

public class BusinessValidationException extends RuntimeException {

    private final Response.Status status;
    private final List<ViolationDetail> violations;

    public BusinessValidationException(Response.Status status, String detail, List<ViolationDetail> violations) {
        super(detail);
        this.status = status;
        this.violations = violations;
    }

    public Response.Status getStatus() {
        return status;
    }

    public List<ViolationDetail> getViolations() {
        return violations;
    }
}

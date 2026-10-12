package br.unitins.lojaguitarra.exception;

import java.util.List;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

public final class ProblemDetailFactory {

    public static final String PROBLEM_JSON = "application/problem+json";

    private ProblemDetailFactory() {
    }

    public static Response build(Response.StatusType status, String type, String title, String detail, UriInfo uriInfo) {
        return build(status, type, title, detail, uriInfo, null);
    }

    public static Response build(Response.StatusType status, String type, String title, String detail, UriInfo uriInfo,
            List<ViolationDetail> violations) {
        ProblemDetail problemDetail = new ProblemDetail(
                type,
                title,
                status.getStatusCode(),
                detail,
                uriInfo == null ? null : uriInfo.getRequestUri().getPath(),
                violations == null || violations.isEmpty() ? null : violations);

        return Response.status(status)
                .type(PROBLEM_JSON)
                .entity(problemDetail)
                .build();
    }
}

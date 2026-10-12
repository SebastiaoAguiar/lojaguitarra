package br.unitins.lojaguitarra.exception;

public record ViolationDetail(
        String field,
        String message) {
}

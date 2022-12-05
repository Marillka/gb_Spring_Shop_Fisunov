package ru.rayumov.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class StringResponse {

    @Schema(description = "Строка с ответом", required = true, example = "Строка")
    private String value;

    public StringResponse() {
    }

    public StringResponse(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

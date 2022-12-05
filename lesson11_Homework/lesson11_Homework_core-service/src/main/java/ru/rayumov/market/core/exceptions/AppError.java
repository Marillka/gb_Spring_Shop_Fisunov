package ru.rayumov.market.core.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppError {
    @Schema(description = "Код ошибки", required = true, example = "404")
    private String code;
    @Schema(description = "Сообщение ошибки", required = true, example = "Not found")
    private String message;
}

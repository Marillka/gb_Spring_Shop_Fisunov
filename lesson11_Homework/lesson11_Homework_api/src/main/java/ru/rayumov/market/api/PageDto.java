package ru.rayumov.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class PageDto {
    @Schema(description = "List продуктов", required = true)
    private List<ProductDto> content;
    @Schema(description = "Количество страниц", required = true, example = "5")
    private int totalPages;
    @Schema(description = "Номер страницы", required = true, example = "1")
    private int number;

    public PageDto() {
    }

    public PageDto(List<ProductDto> content, int totalPages, int number) {
        this.content = content;
        this.totalPages = totalPages;
        this.number = number;
    }

    public List<ProductDto> getContent() {
        return content;
    }

    public void setContent(List<ProductDto> content) {
        this.content = content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

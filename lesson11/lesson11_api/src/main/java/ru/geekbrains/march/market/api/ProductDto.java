package ru.geekbrains.march.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель продукта")
public class ProductDto {
    @Schema(description = "ID продукта", required = true, example = "1")
    private Long id;
    @Schema(description = "Название продкута", required = true, maxLength = 255, minLength = 3, example = "Коробка конфет")
    private String title;
    @Schema(description = "Цена продукта", required = true, example = "120.00")
    private BigDecimal price;
    @Schema(description = "Категория продукта", required = false, example = "Еда")
    private String categoryTitle;

    @Schema(description = "Категория качества продукта", required = false, example = "A")
    private DataEnum abcProduct;

    public DataEnum getAbcProduct() {
        return abcProduct;
    }

    public void setAbcProduct(DataEnum abcProduct) {
        this.abcProduct = abcProduct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public ProductDto() {
    }

    public ProductDto(Long id, String title, BigDecimal price, String categoryTitle) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.categoryTitle = categoryTitle;
    }
}

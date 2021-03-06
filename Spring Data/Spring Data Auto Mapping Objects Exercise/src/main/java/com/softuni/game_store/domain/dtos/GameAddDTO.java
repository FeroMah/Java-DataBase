package com.softuni.game_store.domain.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class GameAddDTO {
    private String title;
    private BigDecimal price;
    private Double size;
    private String trailer;
    private String imageThumbnail;
    private String description;

    public GameAddDTO() {
    }

    public GameAddDTO(String title, BigDecimal price, Double size, String trailer, String imageThumbnail, String description) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.imageThumbnail = imageThumbnail;
        this.description = description;
    }

    @NotNull(message = "Title can not be null!")
    @Pattern(
            regexp = "([A-Z])[a-z]{4,100}",
            message = "Title has to begin with an uppercase letter and must have length between 3 and 100 symbols (inclusively)."
    )
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Length(min = 11, max = 11, message = "Length must be exactly 11 symbols!")
    public String getTrailer() {
        return this.trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Pattern(regexp = "(http(s)?:\\/\\/)?(.)+", message = "Invalid image!")
    public String getImageThumbnail() {
        return this.imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    @Min(0)
    @Digits(integer = 19, fraction = 1, message = "Size do not match!")
    public Double getSize() {
        return this.size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Min(0)
    @Digits(integer = 19, fraction = 2, message = "Price do not match!")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Length(min = 20, message = "Description do not match!")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
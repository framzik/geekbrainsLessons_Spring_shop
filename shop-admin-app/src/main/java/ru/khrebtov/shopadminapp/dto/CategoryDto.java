package ru.khrebtov.shopadminapp.dto;

import javax.validation.constraints.NotBlank;

public class CategoryDto {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    public CategoryDto() {
    }

    public CategoryDto(Long id) {
        this.id = id;
    }

    public CategoryDto(Long id, String name) {
        this(id);

        this.name = name;
    }

    public CategoryDto(Long id, String name, String description) {
        this(id, name);

        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

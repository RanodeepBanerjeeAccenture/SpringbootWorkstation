package com.accenture.library.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class AuthorDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    private List<Long> bookIds;

    // Getters and setters...

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }
}
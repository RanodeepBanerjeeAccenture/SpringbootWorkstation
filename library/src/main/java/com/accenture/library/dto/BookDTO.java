package com.accenture.library.dto;

import jakarta.validation.constraints.NotBlank;

public class BookDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;

    private Long authorId;

    // Getters and setters...

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}

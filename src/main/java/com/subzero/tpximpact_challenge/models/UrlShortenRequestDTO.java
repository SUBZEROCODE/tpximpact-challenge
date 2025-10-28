package com.subzero.tpximpact_challenge.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class UrlShortenRequestDTO {

    @Getter @Setter
    @NotBlank(message = "fullUrl is required")
    private String fullUrl;

    @Getter @Setter
    private String customAlias;
}

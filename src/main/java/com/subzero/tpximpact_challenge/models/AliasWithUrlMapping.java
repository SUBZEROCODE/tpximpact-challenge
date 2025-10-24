package com.subzero.tpximpact_challenge.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor

@Entity
@EqualsAndHashCode
@Table(name = "alias_with_url_mapping")
public class AliasWithUrlMapping {

    @Id
    @Getter @Setter
    @Schema(example = "my-custom-alias")
    private String alias;

    @Getter @Setter
    @Schema(example = "http://example.com/very/long/url")
    private String fullUrl;

    @Getter @Setter
    @Schema(example = "http://localhost:8080/my-custom-alias")
    private String shortUrl;
}

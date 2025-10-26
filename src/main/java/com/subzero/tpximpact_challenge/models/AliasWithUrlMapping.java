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
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor

@Entity
@EqualsAndHashCode
@ToString
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

    public AliasWithUrlMapping(String alias, String fullUrl){
        String apiUrl = "http://localhost:8080/";

        this.alias = alias;
        this.fullUrl = fullUrl;
        this.shortUrl = String.format(apiUrl + alias);
    }
}
package com.subzero.tpximpact_challenge.util;

import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;

import lombok.AllArgsConstructor;
import lombok.Builder;

public class MockAliasUrlMappingBuilder {
    public static AliasWithUrlMapping getCustomStubbedAliasWithUrlMappingForTesting(String alias,String fullUrl,String shortUrl) {

        AliasWithUrlMapping aliasWithUrlMapping = new AliasWithUrlMapping();

        if(alias.isEmpty()){
            aliasWithUrlMapping.setAlias("my-custom-alias");
        } else {
            aliasWithUrlMapping.setAlias(alias);
        }

        if(fullUrl.isEmpty()){
            aliasWithUrlMapping.setFullUrl("https://example.com/very/long/url");
        } else {
            aliasWithUrlMapping.setAlias(fullUrl);
        }

        if(shortUrl.isEmpty()){
            aliasWithUrlMapping.setShortUrl("http://localhost:8080/my-custom-alias");
        } else {
            aliasWithUrlMapping.setShortUrl(shortUrl);
        }
        return aliasWithUrlMapping;
    }

    public static AliasWithUrlMapping getStubbedAliasWithUrlMappingForTesting(){
        return getCustomStubbedAliasWithUrlMappingForTesting(null, null, null);
    }

}

//     // Private constructor to enforce builder usage
//     private MockAliasUrlMappingBuilder(Builder builder) {
//         this.alias = builder.alias;
//         this.fullUrl = builder.fullUrl;
//         this.shortUrl = builder.shortUrl;
//     }

//     // Getters...

//     public static Builder builder() {
//         return new Builder();
//     }


    
//     public static AliasWithUrlMapping getStubbedAliasWithUrlMappingForTesting() {
//         AliasWithUrlMapping aliasWithUrlMapping = new AliasWithUrlMapping();
//         aliasWithUrlMapping.setAlias("some-testing-alias");
//         aliasWithUrlMapping.setFullUrl("https://example.com/very/long/url");
//         aliasWithUrlMapping.setShortUrl("http://localhost:8080/my-custom-alias");

//         return aliasWithUrlMapping;
//     }

// }

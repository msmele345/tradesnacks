package com.mitchmele.tradesnacks.interstellar.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.net.ssl.SSLContext;

@Configuration
@RequiredArgsConstructor
public class SslConfig {

    @Bean
    public SSLContext trustingSslContext() {
        try {
            return SSLContexts
                    .custom()
//                    .loadTrustMaterial(null, (X509Certificate[] chain, String authType) -> true)
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    public SSLContext getTrustingContext() {
        return trustingSslContext();
    }
}
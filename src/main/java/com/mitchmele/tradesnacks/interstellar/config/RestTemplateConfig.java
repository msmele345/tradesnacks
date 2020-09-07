package com.mitchmele.tradesnacks.interstellar.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.SSLContext;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate interstellarRestTemplate(SSLContext sslContext) {

        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
//                .setSSLContext(sslContext)
                .build();

        return restTemplateBuilder
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
                .build();
    }

}

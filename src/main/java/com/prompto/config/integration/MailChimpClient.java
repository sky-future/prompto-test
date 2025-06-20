package com.prompto.config.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailChimpClient {

    private final WebClient webClient = WebClient.builder().build();

    @Value("${mailchimp.transactional.api-key}")
    private String apiKey;

    @Value("${mailchimp.transactional.base-url}")
    private String baseUrl;

     /**
    * Send an email using Mailchimp Transactional API
    * @param subject the subject of the email
    * @param html the HTML content of the email

    */

    public Mono<Void> send(String subject, String html, String toEmail) {
        String url = baseUrl + "/messages/send.json";

        Map<String, Object> body = Map.of(
                "key", apiKey,
                "message", Map.of(
                        "subject", subject,
                        "html", html,
                        "from_email", "mistert9412@gmail.com",
                        "to", List.of(Map.of("email", toEmail, "type", "to"))
                )
        );

        return webClient.post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(resp -> log.info("Mailchimp response: {}", resp))
                .then();
    }
}

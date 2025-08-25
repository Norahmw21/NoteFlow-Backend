package com.example.NoteFlow_Backend.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OllamaService {

    private final WebClient webClient;
    private final String ollamaUrl; // Store the URL as a field

    public OllamaService(@Value("${ollama.url:http://localhost:11434}") String ollamaUrl) {
        this.ollamaUrl = ollamaUrl; // Store the URL
        this.webClient = WebClient.builder()
                .baseUrl(ollamaUrl)
                .build();
    }

    public Flux<String> generateResponse(String model, String prompt) {
        OllamaRequest request = new OllamaRequest(model, prompt, true);

        return webClient.post()
                .uri("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(OllamaResponse.class)
                .map(response -> response.getResponse())
                .onErrorResume(e -> Flux.just("Error: " + e.getMessage()));
    }

    public String generateResponseSync(String model, String prompt) {
        try {
            OllamaRequest request = new OllamaRequest(model, prompt, false);

            // Use the stored ollamaUrl
            WebClient syncWebClient = WebClient.builder()
                    .baseUrl(ollamaUrl)
                    .build();

            OllamaResponse response = syncWebClient.post()
                    .uri("/api/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(OllamaResponse.class)
                    .block();

            return response != null ? response.getResponse() : "No response from AI";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static class OllamaRequest {
        private String model;
        private String prompt;
        private boolean stream;

        public OllamaRequest(String model, String prompt, boolean stream) {
            this.model = model;
            this.prompt = prompt;
            this.stream = stream;
        }

        // Getters and setters
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public String getPrompt() { return prompt; }
        public void setPrompt(String prompt) { this.prompt = prompt; }
        public boolean isStream() { return stream; }
        public void setStream(boolean stream) { this.stream = stream; }
    }

    public static class OllamaResponse {
        private String response;
        private boolean done;

        // Getters and setters
        public String getResponse() { return response; }
        public void setResponse(String response) { this.response = response; }
        public boolean isDone() { return done; }
        public void setDone(boolean done) { this.done = done; }
    }
}
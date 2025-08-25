package com.example.NoteFlow_Backend.Controller;

import com.example.NoteFlow_Backend.Service.OllamaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final OllamaService ollamaService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChatController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> chat(@RequestBody ChatRequest request,
                                    Authentication authentication) {
        String username = (authentication != null) ? authentication.getName() : "anonymous";
        System.out.println("Chat request from user: " + username);

        String model = (request.getModel() == null || request.getModel().isBlank())
                ? "llama3" : request.getModel();

        String reply = ollamaService.generateResponseSync(model, request.getMessage());
        return Map.of("response", reply);
    }



    public static class ChatRequest {
        private String message;
        private String model;
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
    }
}

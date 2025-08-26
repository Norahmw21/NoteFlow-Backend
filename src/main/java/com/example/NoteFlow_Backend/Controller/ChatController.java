package com.example.NoteFlow_Backend.Controller;

import com.example.NoteFlow_Backend.Service.OllamaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);


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

        logger.info("Chat request from user: {}", username);
        logger.info("Selected model: {}", model);
        logger.info("Message: {}", request.getMessage());
        System.out.println("DEBUG - Using model: " + model);
        System.out.println("DEBUG - Message: " + request.getMessage());

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

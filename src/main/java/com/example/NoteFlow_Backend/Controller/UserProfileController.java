package com.example.NoteFlow_Backend.Controller;


import com.example.NoteFlow_Backend.Service.UserProfileService;
import com.example.NoteFlow_Backend.dto.UpdateProfileReq;
import com.example.NoteFlow_Backend.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService svc;
    @GetMapping
    public UserProfileDto me(@RequestParam Long userId) {
        return svc.me(userId);
    }

    @PutMapping
    public UserProfileDto update(@RequestParam Long userId, @RequestBody UpdateProfileReq req) {
        return svc.update(userId, req);
    }
}

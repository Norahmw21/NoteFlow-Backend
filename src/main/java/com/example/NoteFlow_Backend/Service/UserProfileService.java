package com.example.NoteFlow_Backend.Service;

import com.example.NoteFlow_Backend.Entity.User;
import com.example.NoteFlow_Backend.Repo.UserRepo;
import com.example.NoteFlow_Backend.dto.UpdateProfileReq;
import com.example.NoteFlow_Backend.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserRepo userRepo;

    public UserProfileDto me(Long uid) {
        User u = userRepo.findById(uid).orElseThrow();
        return toDto(u);
    }

    public UserProfileDto update(Long uid, UpdateProfileReq r) {
        User u = userRepo.findById(uid).orElseThrow();
        if (r.avatarUrl() != null) u.setAvatarUrl(r.avatarUrl().trim());
        if (r.phone()     != null) u.setPhone(r.phone().trim());
        u = userRepo.save(u);
        return toDto(u);
    }

    private static UserProfileDto toDto(User u) {
        return new UserProfileDto(
                u.getUserId(),
                u.getUsername(),
                u.getEmail(),
                u.getAvatarUrl(),
                u.getPhone()
        );
    }
}

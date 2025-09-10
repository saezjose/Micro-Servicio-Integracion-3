package com.tomas.chat_microservice.interfaces.auth;

import com.tomas.chat_microservice.domain.auth.Profile;

public final class ProfileResponseMapper {
    private ProfileResponseMapper() {}

    public static ProfileResponse toResponse(Profile p) {
        return new ProfileResponse(
                p.getUsername(),
                p.getRole(),
                p.getEmail(),
                p.getIssuedAt(),
                p.getExpiresAt()
        );
    }
}

package csnojam.app.user.dto;

import csnojam.app.user.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfoDto {
    private final String email;
    private final String nickname;
    private final String profileUrl;

    public static UserInfoDto of(User user) {
        return UserInfoDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .build();
    }
}

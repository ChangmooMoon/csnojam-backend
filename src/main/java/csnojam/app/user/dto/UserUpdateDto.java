package csnojam.app.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserUpdateDto {
    private final String nickname;
}

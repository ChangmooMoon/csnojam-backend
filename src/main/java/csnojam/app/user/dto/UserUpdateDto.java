package csnojam.app.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class UserUpdateDto {
    @NotBlank
    private String nickname;
}

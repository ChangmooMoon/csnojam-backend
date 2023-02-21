package csnojam.app.user.dto;

import csnojam.app.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Validated
public class UserJoinDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "[a-zA-Z!@#$%^&*-_]{10,20}", message = "10~20 길이의 알파벳과 숫자, 특수문자만 사용할 수 있습니다.")
    private String password;
    @Size(min = 1, max = 10, message = "닉네임이 입력되지 않았거나 너무 긴 닉네임입니다.")
    private String name;

    public User asEntity(){
        User user = User.builder().build();
        BeanUtils.copyProperties(this, user);
        return user;
    }

}

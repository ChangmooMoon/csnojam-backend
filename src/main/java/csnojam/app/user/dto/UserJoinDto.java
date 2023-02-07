package csnojam.app.user.dto;

import csnojam.app.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserJoinDto {
    private String email;
    private String password;
    private String name;

    public User asEntity(){
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }

}

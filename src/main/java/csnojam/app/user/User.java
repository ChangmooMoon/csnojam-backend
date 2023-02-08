package csnojam.app.user;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class User{

    @Id
    private UUID id;

    private String email;

    private String name;

    private String password;

    private String profileUrl;

    public User updateEmail(String email){
        this.email = email;
        return this;
    }

    public User updateProfileUrl(String profileUrl){
        this.profileUrl = profileUrl;
        return this;
    }

}

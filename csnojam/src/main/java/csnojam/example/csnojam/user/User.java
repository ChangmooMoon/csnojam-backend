package csnojam.example.csnojam.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User{

    @Id
    private UUID id;
    private String email;
    private String password;
    private String profileUrl;

    public User(UUID id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User updateEmail(String email){
        this.email = email;
        return this;
    }

    public User updateProfileUrl(String profileUrl){
        this.profileUrl = profileUrl;
        return this;
    }

}

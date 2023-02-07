package csnojam.app.jwt;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

public class PrincipalDetails implements UserDetails {

    private UUID id;
    private String email;
    private String name;
    private String password;
    private String profileUrl;
    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    public PrincipalDetails(UUID id, String email, String name, String password, String profileUrl) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.profileUrl = profileUrl;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

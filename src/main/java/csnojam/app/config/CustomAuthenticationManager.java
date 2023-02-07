package csnojam.app.config;


import csnojam.app.jwt.PrincipalDetails;
import csnojam.app.jwt.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PrincipalDetailsService principalDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUsername(authentication.getName());
        if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), principalDetails.getPassword())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        return new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
    }

}

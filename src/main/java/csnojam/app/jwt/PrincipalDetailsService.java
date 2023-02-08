package csnojam.app.jwt;

import csnojam.app.user.User;
import csnojam.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 회원입니다"));

        PrincipalDetails principalDetails = PrincipalDetails.builder()
                .id(user.getId())
                .name(user.getNickname())
                .email(user.getEmail())
                .password(user.getPassword())
                .profileUrl(user.getProfileUrl())
                .build();
        return principalDetails;
    }
}

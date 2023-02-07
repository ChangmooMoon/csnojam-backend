package csnojam.app.jwt;
import csnojam.app.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;

@Slf4j
@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtProvider.getToken(request);
        System.out.println("헤더에서 토큰 받아옴:" + token);

        if (token == null) {
            System.out.println("토큰 없음");
        }
        else if (jwtProvider.validateToken(token)) {
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("토큰이 유효함");
        }
        else{
            System.out.println("토큰이 유효하지 않음");
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        Collection<String> excludeUrlPatterns = new LinkedHashSet<>();
        excludeUrlPatterns.add("member/sign-in");
        excludeUrlPatterns.add("member/login");
        return excludeUrlPatterns.stream()
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, request.getServletPath()));
    }
}

package csnojam.app.user;

import csnojam.app.jwt.JwtProvider;
import csnojam.app.user.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public UUID join(UserJoinDto userJoinDto) throws Exception {
        try{
            System.out.println("이메일 체크");
            checkDuplicateEmail(userJoinDto.getEmail());
            System.out.println("닉네임 체크");
            checkDuplicateName(userJoinDto.getName());

            User user = User.builder()
                    .email(userJoinDto.getEmail())
                    .password(encoder.encode(userJoinDto.getPassword()))
                    .nickname(userJoinDto.getName()).build();

            System.out.println("유저 객체 생성");

            userRepository.save(user);
            return user.getId();
        }
        catch (Exception e){
            throw e;
        }
    }

    private void checkDuplicateEmail(String email) throws Exception{
        if (userRepository.findByEmail(email).isPresent()){
            throw new Exception();
        }
    }

    private void checkDuplicateName(String name) throws  Exception{
        if(userRepository.findByNickname(name).isPresent()){
            throw new Exception();
        }
    }

    public String login(UserLoginDto userLoginDto) throws Exception{
        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new Exception("존재하지 않는 유저"));
        if (encoder.matches(userLoginDto.getPassword(), user.getPassword())){
            String token = jwtProvider.createToken(user.getId());
            return token;
        }
        else{
            throw new Exception();
        }
    }
}

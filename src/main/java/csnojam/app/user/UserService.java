package csnojam.app.user;

import csnojam.app.common.exception.ApiException;
import csnojam.app.jwt.JwtProvider;
import csnojam.app.user.dto.UserInfoDto;
import csnojam.app.user.dto.UserJoinDto;
import csnojam.app.user.dto.UserLoginDto;
import csnojam.app.user.dto.UserUpdateDto;
import csnojam.app.user.enums.UniqueFields;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static csnojam.app.common.response.StatusMessage.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public UUID join(UserJoinDto userJoinDto) {

        if (isEmailDuplicated(userJoinDto.getEmail())){
            throw new ApiException(EMAIL_LOCKED);
        }
        if(isNicknameDuplicated(userJoinDto.getName())){
            throw  new ApiException(NICKNAME_LOCKED);
        }

        User user = User.builder()
                .email(userJoinDto.getEmail())
                .password(encoder.encode(userJoinDto.getPassword()))
                .nickname(userJoinDto.getName()).build();

        userRepository.save(user);
        return user.getId();
    }

    public String login(UserLoginDto userLoginDto){
        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new ApiException(USER_NOT_FOUND));
        if (encoder.matches(userLoginDto.getPassword(), user.getPassword())){
            return jwtProvider.createToken(user.getId());
        }
        else{
            throw new ApiException(WRONG_PASSWORD);
        }
    }

    public boolean checkFieldDuplication(UniqueFields fields, String value) {
        switch (fields) {
            case NICKNAME:
                return isNicknameDuplicated(value);
            case EMAIL:
                return isEmailDuplicated(value);
            default:
                throw new ApiException(ERROR);
        }
    }

    private boolean isNicknameDuplicated(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    private boolean isEmailDuplicated(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserInfoDto getUserInfo(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(USER_NOT_FOUND));
        return UserInfoDto.of(user);
    }

    @Transactional
    public void changeUserNickname(UUID id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(USER_NOT_FOUND));
        user.updateNickname(userUpdateDto.getNickname());
    }
}

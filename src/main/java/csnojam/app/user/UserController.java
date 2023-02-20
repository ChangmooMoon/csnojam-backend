package csnojam.app.user;

import csnojam.app.common.response.ApiResponse;
import csnojam.app.jwt.JwtProvider;
import csnojam.app.user.dto.UserInfoDto;
import csnojam.app.user.dto.UserJoinDto;
import csnojam.app.user.dto.UserLoginDto;
import csnojam.app.user.dto.UserUpdateDto;
import csnojam.app.user.enums.UniqueFields;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

import static csnojam.app.common.response.StatusMessage.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("members")
@RestController
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("sign-up")
    public ResponseEntity<?> join(@RequestBody UserJoinDto userJoinDto){
        ResponseEntity<?> responseEntity;
        System.out.println("UserController: join 진입");
        if (userJoinDto.getEmail() == null) {
            responseEntity = ApiResponse.withNothing(EMPTY_EMAIL);
            return responseEntity;
        }
        if (userJoinDto.getPassword() == null){
            responseEntity = ApiResponse.withNothing(EMPTY_PASSWORD);
            return responseEntity;
        }
        try {
            userService.join(userJoinDto);
            responseEntity = ApiResponse.withNothing(SUCCESS);
            return responseEntity;
        } catch (Exception e) {
            log.error(e.getMessage());
            responseEntity = ApiResponse.withNothing(ERROR);
            return responseEntity;
        }
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse httpServletResponse){
        ResponseEntity<?> responseEntity;
        try {
            System.out.println("UserController: login 진입");
            String token  = userService.login(userLoginDto);
            jwtProvider.writeTokenResponse(httpServletResponse, token);
            responseEntity = ApiResponse.withNothing(SUCCESS);
            return responseEntity;
        } catch (Exception e){
            log.error(e.getMessage());
            responseEntity = ApiResponse.withNothing(LOGIN_FAIL);
            return responseEntity;
        }
    }

    @GetMapping("validate/{field}")
    public ResponseEntity<?> checkDuplication(@PathVariable UniqueFields field,
                                              String value) {
        if(userService.checkFieldDuplication(field, value))
            return ApiResponse.withNothing(INVALID_FIELD);
        else
            return ApiResponse.withNothing(VALID_FIELD);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable UUID id) {
        UserInfoDto userInfo = userService.getUserInfo(id);
        return ApiResponse.withBody(SUCCESS, userInfo);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateUserInfo(@PathVariable UUID id,
                                            @RequestBody @Valid UserUpdateDto userUpdateDto) {
        userService.changeUserNickname(id, userUpdateDto);
        return ApiResponse.withNothing(SUCCESS);
    }
}

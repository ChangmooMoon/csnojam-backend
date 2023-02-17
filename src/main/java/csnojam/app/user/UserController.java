package csnojam.app.user;

import csnojam.app.common.response.ApiResponse;
import csnojam.app.user.dto.UserInfoDto;
import csnojam.app.user.dto.UserJoinDto;
import csnojam.app.user.dto.UserLoginDto;
import csnojam.app.user.enums.UniqueFields;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static csnojam.app.common.response.StatusMessage.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("members")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("sign-up")
    public ResponseEntity<?> join(@RequestBody UserJoinDto userJoinDto){
        try {
            System.out.println("UserController: join 진입");
            userService.join(userJoinDto);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto){
        try {
            System.out.println("UserController: login 진입");
            String token = userService.login(userLoginDto);
            return ResponseEntity.ok()
                    .header("X-AUTH-TOKEN", token)
                    .body("success");
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
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
}

package csnojam.app.user;

import csnojam.app.user.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("member/sign-in")
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

    @PostMapping("member/login")
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
}

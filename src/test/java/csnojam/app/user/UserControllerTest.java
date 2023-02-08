package csnojam.app.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import csnojam.app.common.ControllerTest;
import csnojam.app.user.dto.UserJoinDto;
import csnojam.app.user.dto.UserLoginDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserController.class)
public class UserControllerTest extends ControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @DisplayName("유저 회원가입 요청")
    @Test
    void UserJoinTest() throws Exception {

        objectMapper = new ObjectMapper();
        String joinUrl = "/members/sign-up";

        // given
        UserJoinDto userJoinDto = UserJoinDto.builder()
                .email("yujinkim@test.com")
                .password("123456789")
                .name("yujin")
                .build();

        given(userService.join(any(UserJoinDto.class))).willReturn(UUID.randomUUID());

        // when
        String content = objectMapper.writeValueAsString(userJoinDto);
        ResultActions perform = mockMvc.perform(post(joinUrl)
                        .headers(HttpHeaders.EMPTY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print());

        // then
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success"));
    }

    @DisplayName("이메일 로그인 요청: 성공")
    @Test
    void UserLoginSuccessTest() throws Exception {
        objectMapper = new ObjectMapper();
        String loginUrl = "/members/sign-in";

        // given
        User user = User.builder()
                .email("yujinkim@test.com")
                .password("123456789")
                .nickname("yujin")
                .build();
        userRepository.save(user);

        UserLoginDto userLoginDto = UserLoginDto.builder()
                .email("yujinkim@test.com")
                .password("123456789")
                .build();
        String content = objectMapper.writeValueAsString(userLoginDto);

        // when
        ResultActions perform = mockMvc.perform(post(loginUrl)
                        .headers(HttpHeaders.EMPTY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print());


        // then
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success"));
    }

    @DisplayName("이메일 로그인 요청: 실패")
    @Test
    void UserLoginFailTest() throws Exception {
        objectMapper = new ObjectMapper();
        String loginUrl = "/members/sign-in";

        // given
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .email("nobody@test.com")
                .password("123456789")
                .build();
        String content = objectMapper.writeValueAsString(userLoginDto);

        // when
        ResultActions perform = mockMvc.perform(post(loginUrl)
                        .headers(HttpHeaders.EMPTY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print());

        // then
        perform.andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string("error"));
    }
}

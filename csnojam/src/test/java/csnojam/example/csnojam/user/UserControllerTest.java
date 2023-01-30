package csnojam.example.csnojam.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import csnojam.example.csnojam.ControllerTest;
import csnojam.example.csnojam.CsnojamApplication;
import csnojam.example.csnojam.user.dto.UserJoinDto;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.web.servlet.function.ServerResponse.status;

@WebAppConfiguration
public class UserControllerTest extends ControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserJoinDto userJoinDto;

    @DisplayName("유저 회원가입 요청")
    @Test
    void UserJoinTest() throws Exception {

        objectMapper = new ObjectMapper();
        String loginUrl = "/api/user/join";

        // given
        UserJoinDto userJoinDto = UserJoinDto.builder()
                .email("yujinkim@test.com")
                .password("123456789")
                .build();
        String content = objectMapper.writeValueAsString(userJoinDto);


        // when
        mockMvc.perform(post(loginUrl)
                .headers(HttpHeaders.EMPTY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success"));
    }
}

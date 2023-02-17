package csnojam.app.user;


import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import csnojam.app.common.ControllerTest;
import csnojam.app.user.dto.UserInfoDto;
import csnojam.app.user.dto.UserJoinDto;
import csnojam.app.user.dto.UserLoginDto;
import csnojam.app.user.enums.UniqueFields;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static csnojam.app.common.response.StatusMessage.INVALID_FIELD;
import static csnojam.app.common.response.StatusMessage.VALID_FIELD;
import static csnojam.app.utils.ApiDocumentUtils.getDocumentRequest;
import static csnojam.app.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest extends ControllerTest {
    private final String TAG = "사용자";

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
        perform.andExpect(status().isOk())
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
        perform.andExpect(status().isOk())
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
        perform.andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string("error"));
    }

    @DisplayName("필드 중복 확인")
    @Nested
    class Duplication {
        private final String PATH = "/members/validate/{field}";

        @DisplayName("중복되지 않은 닉네임")
        @Test
        void uniqueNickname() throws Exception {
            // given
            String field = "nickname";
            String nickname = "testUser";

            given(userService.checkFieldDuplication(eq(UniqueFields.NICKNAME), eq(nickname)))
                    .willReturn(false);

            // when
            ResultActions resultActions = mockMvc.perform(get(PATH, field)
                    .param("value", nickname));

            // then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(VALID_FIELD.getMessage()));

            // documentation
            resultActions.andDo(document("checkUserFieldValidation",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    resource(
                            ResourceSnippetParameters.builder()
                                    .tag(TAG)
                                    .description("닉네임이나 이메일의 중복 여부를 반환")
                                    .summary("닉네임/이메일 중복 체크")
                                    .pathParameters(
                                            parameterWithName("field").description("중복 체크할 필드 (nickname/email)")
                                    )
                                    .requestParameters(
                                            parameterWithName("value").description("입력된 닉네임이나 이메일 값")
                                    )
                                    .build()
                    )
            ));
        }

        @DisplayName("중복된 닉네임")
        @Test
        void duplicatedNickname() throws Exception {
            // given
            String field = "nickname";
            String nickname = "testUser";

            given(userService.checkFieldDuplication(eq(UniqueFields.NICKNAME), eq(nickname)))
                    .willReturn(true);

            // when
            ResultActions resultActions = mockMvc.perform(get(PATH, field)
                    .param("value", nickname));

            // then
            resultActions.andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").value(INVALID_FIELD.getMessage()));
        }

        @DisplayName("중복되지 않은 이메일")
        @Test
        void uniqueEmail() throws Exception {
            // given
            String field = "email";
            String email = "test@gmail.com";

            given(userService.checkFieldDuplication(eq(UniqueFields.EMAIL), eq(email)))
                    .willReturn(false);

            // when
            ResultActions resultActions = mockMvc.perform(get(PATH, field)
                    .param("value", email));

            // then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(VALID_FIELD.getMessage()));
        }

        @DisplayName("중복된 이메일")
        @Test
        void duplicatedEmail() throws Exception {
            // given
            String field = "email";
            String email = "test@gmail.com";

            given(userService.checkFieldDuplication(eq(UniqueFields.EMAIL), eq(email)))
                    .willReturn(true);

            // when
            ResultActions resultActions = mockMvc.perform(get(PATH, field)
                    .param("value", email));

            // then
            resultActions.andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").value(INVALID_FIELD.getMessage()));
        }
    }

    @DisplayName("유저 정보 조회")
    @Nested
    class UserInfo {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            UUID id = UUID.randomUUID();
            String email = "test@gmail.com";
            String nickname = "testUser";
            String profileUrl = "testUrl";

            UserInfoDto userInfo = UserInfoDto.builder()
                    .email(email)
                    .nickname(nickname)
                    .profileUrl(profileUrl)
                    .build();

            given(userService.getUserInfo(eq(id)))
                    .willReturn(userInfo);

            // when
            ResultActions resultActions = mockMvc.perform(get("/members/{id}", id));

            // then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.email").value(email))
                    .andExpect(jsonPath("$.data.nickname").value(nickname))
                    .andExpect(jsonPath("$.data.profileUrl").value(profileUrl));

            // documentation
            resultActions.andDo(document("getUserInfo",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    resource(
                            ResourceSnippetParameters.builder()
                                    .tag(TAG)
                                    .description("해당 id의 사용자 정보를 반환")
                                    .summary("사용자 정보 조회")
                                    .pathParameters(
                                            parameterWithName("id").description("사용자 고유번호")
                                    )
                                    .responseFields(
                                            fieldWithPath("message").description("요청 결과"),
                                            fieldWithPath("data.email").description("사용자 이메일"),
                                            fieldWithPath("data.nickname").description("사용자 닉네임"),
                                            fieldWithPath("data.profileUrl").description("사용자 프로필 사진 URL")
                                    )
                                    .build()
                    )
            ));
        }
    }
}

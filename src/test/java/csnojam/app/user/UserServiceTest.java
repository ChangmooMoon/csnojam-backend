package csnojam.app.user;

import csnojam.app.common.exception.ApiException;
import csnojam.app.user.dto.UserInfoDto;
import csnojam.app.user.dto.UserUpdateDto;
import csnojam.app.user.enums.UniqueFields;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static csnojam.app.common.response.StatusMessage.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @DisplayName("필드 중복 확인")
    @Nested
    class FieldDuplication {
        @DisplayName("중복되지 않은 닉네임")
        @Test
        void uniqueNickname() {
            // given
            UniqueFields fields = UniqueFields.NICKNAME;
            String nickname = "testUser";

            given(userRepository.existsByNickname(eq((nickname))))
                    .willReturn(false);

            // when
            boolean result = userService.checkFieldDuplication(fields, nickname);

            // then
            assertThat(result).isFalse();
        }

        @DisplayName("중복된 닉네임")
        @Test
        void duplicatedNickname() {
            // given
            UniqueFields fields = UniqueFields.NICKNAME;
            String nickname = "testUser";

            given(userRepository.existsByNickname(eq((nickname))))
                    .willReturn(true);

            // when
            boolean result = userService.checkFieldDuplication(fields, nickname);

            // then
            assertThat(result).isTrue();
        }

        @DisplayName("중복되지 않은 이메일")
        @Test
        void uniqueEmail() {
            // given
            UniqueFields fields = UniqueFields.EMAIL;
            String email = "test@gmail.com";

            given(userRepository.existsByEmail(eq((email))))
                    .willReturn(false);

            // when
            boolean result = userService.checkFieldDuplication(fields, email);

            // then
            assertThat(result).isFalse();
        }

        @DisplayName("중복된 이메일")
        @Test
        void duplicatedEmail() {
            // given
            UniqueFields fields = UniqueFields.EMAIL;
            String email = "test@gmail.com";

            given(userRepository.existsByEmail(eq((email))))
                    .willReturn(true);

            // when
            boolean result = userService.checkFieldDuplication(fields, email);

            // then
            assertThat(result).isTrue();
        }
    }

    @DisplayName("사용자 정보 조회")
    @Nested
    class UserInfo {
        @DisplayName("성공")
        @Test
        void success() {
            // given
            UUID id = UUID.randomUUID();
            String email = "test@gmail.com";

            User user = User.builder()
                    .id(id)
                    .email(email)
                    .build();

            given(userRepository.findById(eq(id)))
                    .willReturn(Optional.of(user));

            // when
            UserInfoDto result = userService.getUserInfo(id);

            // then
            assertThat(result.getEmail())
                    .isEqualTo(email);
        }

        @DisplayName("해당 id의 유저가 없음")
        @Test
        void userNotFound() {
            // given
            UUID id = UUID.randomUUID();

            given(userRepository.findById(id))
                    .willReturn(Optional.empty());

            // when
            ApiException exception = assertThrows(ApiException.class,
                    () -> userService.getUserInfo(id));

            // then
            assertThat(exception.getStatusMessage())
                    .isEqualTo(USER_NOT_FOUND);
        }
    }

    @DisplayName("유저 닉네임 수정")
    @Nested
    class UserNicknameUpdate {
        @DisplayName("성공")
        @Test
        void success() {
            // given
            UUID id = UUID.randomUUID();
            String nickname = "newNickname";
            UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                    .nickname(nickname)
                    .build();

            User user = mock(User.class);

            given(userRepository.findById(eq(id)))
                    .willReturn(Optional.of(user));

            // when
            userService.changeUserNickname(id, userUpdateDto);

            // then
            then(user).should().updateNickname(nickname);
        }

        @DisplayName("해당 id의 유저가 없음")
        @Test
        void userNotFound() {
            // given
            UUID id = UUID.randomUUID();
            UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                    .nickname("userNickname")
                    .build();

            given(userRepository.findById(eq(id)))
                    .willReturn(Optional.empty());

            // when
            ApiException exception = assertThrows(ApiException.class,
                    () -> userService.changeUserNickname(id, userUpdateDto));

            // then
            assertThat(exception.getStatusMessage())
                    .isEqualTo(USER_NOT_FOUND);

        }
    }
}
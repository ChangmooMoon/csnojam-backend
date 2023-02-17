package csnojam.app.user;

import csnojam.app.user.enums.UniqueFields;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;

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
}
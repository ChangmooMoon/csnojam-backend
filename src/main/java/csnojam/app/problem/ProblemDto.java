package csnojam.app.problem;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProblemDto {
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class CreationReq {
        private Problem problem;
        @Builder
        public CreationReq(Problem problem) {
            this.problem = problem;
        }
    }

    /**
     * @Builder 는 빌더 패턴 구현할 때 쓰는 애노테이션이라는데
     * https://zorba91.tistory.com/298
     * 위 링크 참고해보니 쓰는 이유가 납득이 가네요
     */

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateReq {
        private Problem problem;

        @Builder
        public UpdateReq(Problem problem) {
            this.problem = problem;
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Res {
        // 여기를 커스텀하게 바꾸면 될 듯?
    }
}

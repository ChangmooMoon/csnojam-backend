package csnojam.app.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ExampleController {

    @GetMapping("/test")
    public String test() {
        log.info("### 테스트테스트");
        return "HI";
    }
}

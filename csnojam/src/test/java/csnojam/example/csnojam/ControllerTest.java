package csnojam.example.csnojam;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.internal.matchers.ArrayEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(classes = CsnojamApplication.class)
@AutoConfigureMockMvc
public abstract class ControllerTest {
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp(){
        mockMvc = webAppContextSetup(webApplicationContext).addFilter(((request, response, chain) -> {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            chain.doFilter(request, response);
        })).build();

    }
}

package csnojam.app.common;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ComponentScan(basePackages = {"org.springframework.security"})
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

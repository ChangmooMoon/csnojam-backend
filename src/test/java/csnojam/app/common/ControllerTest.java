package csnojam.app.common;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(RestDocumentationExtension.class)
@ComponentScan({"csnojam.app.config", "csnojam.app.jwt"})
public abstract class ControllerTest {
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(((request, response, chain) -> {
                    request.setCharacterEncoding("utf-8");
                    response.setCharacterEncoding("utf-8");
                    chain.doFilter(request, response);
                }))
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();

    }

}

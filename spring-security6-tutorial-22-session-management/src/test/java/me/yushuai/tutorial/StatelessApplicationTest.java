package me.yushuai.tutorial;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author zhoushuai@189.cn
 * @since 2023-03-14
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatelessApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void loginOnSecondLoginThenFirstSessionTerminated() throws Exception {

        final MvcResult mvcResult = this.mvc.perform(formLogin())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("/"))
                .andDo(print())
                .andReturn();

        // 检查当前对象是否有session
        final MockHttpServletRequest request = mvcResult.getRequest();
        final HttpSession session = request.getSession();
        System.out.println(session);
//        isNull(session, "The session must be is null." + session.getId());


    }


//    @Configuration
//    public class StatelessSecurityConfig {
//
//        @Bean
//        SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {
//
//            http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
//                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//            return http.build();
//        }
//
//
//    }


}

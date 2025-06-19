package com.sparta.taskflow.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.taskflow.auth.dto.request.LoginRequestDto;
import com.sparta.taskflow.domain.user.entity.User;
import com.sparta.taskflow.domain.user.repository.UserRepository;
import com.sparta.taskflow.domain.user.type.RoleType;
import com.sparta.taskflow.response.ApiResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
// 테스트가 제대로 동작하려면 SecurityConfig.java 파일에서 경로 주석을 해제해주어야 한다.
public class AuthenticationIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository; // 그냥 엔티티로 만들어서 직접 넣어주는 것은 비추?

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void 인증되지_않은_사용자_일반페이지_접근불가() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/not-existing-page")
        );

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    /*
    Fixme
     원래 Spring MVC가 예외를 만들고 그걸 잡아 404 응답을 내보내줘야 하나
     GlobalExceptionHandler가 최상위 클래스인 Exception을 받아서 다 500으로 반환됨
    */
    @Test
    void 로그인_후_없는_페이지_접근_시_404() throws Exception {
        // given
        saveUser("username", "password");

        ResultActions resultActions = login("username", "password");
        String token = getJwtFromResultActions(resultActions);

        // when
        ResultActions authenticatedAccessResultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/not-existing-page")
                                  .header("Authorization", "Bearer " + token)
        );

        // then
        MvcResult authenticatedAccessMvcResult = authenticatedAccessResultActions.andReturn();
        assertThat(authenticatedAccessMvcResult.getResponse().getStatus()).isEqualTo(500); // Fixme
    }

    private void saveUser(String username, String password) {
        User user = User.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .isDeleted(false)
                        .role(RoleType.USER)
                        .build();

        userRepository.save(user);
    }

    private String getJwtFromResultActions(ResultActions resultActions)
        throws UnsupportedEncodingException, JsonProcessingException {
        MvcResult mvcResult = resultActions.andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String body = response.getContentAsString();

        ApiResponse apiResponse = objectMapper.readValue(body, ApiResponse.class);
        Map<String, String> map = (Map<String, String>) apiResponse.getData();
        String token = map.get("token");

        return token;
    }

    private ResultActions login(String username, String password) throws Exception {
        LoginRequestDto loginRequest = new LoginRequestDto();
        ReflectionTestUtils.setField(loginRequest, "username", username);
        ReflectionTestUtils.setField(loginRequest, "password", password);

        return mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth/login")
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(objectMapper.writeValueAsString(loginRequest))
        );
    }
}

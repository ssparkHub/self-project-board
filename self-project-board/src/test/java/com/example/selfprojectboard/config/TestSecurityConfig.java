package com.example.selfprojectboard.config;

import com.example.selfprojectboard.domain.UserAccount;
import com.example.selfprojectboard.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean private UserAccountRepository userAccountRepository;

    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(

            "ssparkTest",

            "pw",

            "sspark-test@email.com",

            "sspark-test",

            "test memo"
        )));
    }

}

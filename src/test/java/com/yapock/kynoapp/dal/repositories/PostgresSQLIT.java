package com.yapock.kynoapp.dal.repositories;

import com.yapock.kynoapp.dal.models.Federation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class PostgresSQLIT {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    FederationRepository FederationRepository;

    @Test
    void testListFederations() {
        List<Federation> federations = FederationRepository.findAll();

        assertThat(federations).isNotNull();
        assertThat(federations).isNotEmpty();
        assertThat(federations.size()).isGreaterThan(0);
    }

}

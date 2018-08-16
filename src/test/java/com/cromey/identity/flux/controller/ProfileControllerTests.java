package com.cromey.identity.flux.controller;

import com.cromey.identity.webflux.controller.ProfileController;
import com.cromey.identity.webflux.model.Profile;
import com.cromey.identity.webflux.repository.ProfileRepository;

import com.cromey.identity.webflux.validator.ProfileValidator;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProfileControllerTests {

    private WebTestClient client;

    private List<Profile> expectedList;

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private ProfileValidator validator;

    @BeforeEach
    void beforeEach() {
        this.client = WebTestClient
                .bindToController(new ProfileController(repository, validator))
                .configureClient()
                .baseUrl("/profiles")
                .build();

        System.out.println(client);

        this.expectedList =
                repository.findAll().collectList().block();
    }

    @Test
    public void getAllProfile() {
       this.client.get()
                .uri("/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Profile.class)
                .isEqualTo(expectedList);*/
    }

}

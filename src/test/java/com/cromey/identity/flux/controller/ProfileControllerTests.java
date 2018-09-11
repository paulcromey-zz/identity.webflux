package com.cromey.identity.flux.controller;

import com.cromey.identity.Application;
import com.cromey.identity.controller.ProfileController;
import com.cromey.identity.model.Profile;
import com.cromey.identity.repository.ProfileRepository;

import com.cromey.identity.validator.ProfileValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes=Application.class)
public class ProfileControllerTests {

    private WebTestClient client;

    private List<Profile> expectedList;

    @Autowired
    private ProfileRepository repository;
    
    @Autowired
    private ProfileValidator validator;

    @Test
    public void getAllProfile() {
    	
    	this.client = WebTestClient
                .bindToController(new ProfileController(repository, validator))
                .configureClient()
                .baseUrl("/profiles")
                .build();

        this.expectedList =
                repository.findAll().collectList().block();
    	
        this.client.get()
                .uri("/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Profile.class)
                .isEqualTo(expectedList);
        
    }

}

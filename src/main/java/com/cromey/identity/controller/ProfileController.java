package com.cromey.identity.controller;

import com.cromey.identity.repository.ProfileRepository;
import com.cromey.identity.validator.ProfileValidator;
import com.cromey.identity.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private static Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private ProfileRepository repository;
    private ProfileValidator validator;

    @Autowired
    public ProfileController(ProfileRepository repository, ProfileValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @GetMapping
    public Flux<Profile> getAllProfiles() {
        logger.info("get profiles");
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Profile>> getProfile(@PathVariable String id) {
        logger.info("get profile by id");
        return repository.findById(id)
                .map(profile -> ResponseEntity.ok(profile))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Profile> createProfile(@Valid @RequestBody Profile profile) {
        logger.info("create profile");
        return repository.save(profile);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Profile>> updateProfile(@PathVariable(value = "id") String id
            , @RequestBody Profile profile) {
        logger.info("update profile");
        return repository.findById(id)
                .flatMap(existingProfile -> {
                    existingProfile.setEmail(profile.getEmail());
                    return repository.save(existingProfile);
                })
                .map(updateProfile -> ResponseEntity.ok(updateProfile))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteProfile(@PathVariable(value = "id") String id) {
        logger.info("delete profile");
        return repository.findById(id)
                .flatMap(existingProfile ->
                        repository.delete(existingProfile)
                                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<Void> deleteAllProfiles() {
        logger.info("delete profiles");
        return repository.deleteAll();
    }

}

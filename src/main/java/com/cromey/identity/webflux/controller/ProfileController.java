package com.cromey.identity.webflux.controller;

import com.cromey.identity.webflux.model.Profile;
import com.cromey.identity.webflux.repository.ProfileRepository;
import com.cromey.identity.webflux.validator.ProfileValidator;
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
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Profile>> getProfile(@PathVariable String id) {
        return repository.findById(id)
                .map(profile -> ResponseEntity.ok(profile))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Profile> createProfile(@Valid @RequestBody Profile profile) {
        return repository.save(profile);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Profile>> updateProfile(@PathVariable(value = "id") String id
            , @RequestBody Profile profile) {
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
        return repository.findById(id)
                .flatMap(existingProfile ->
                        repository.delete(existingProfile)
                                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<Void> deleteAllProfiles() {
        return repository.deleteAll();
    }

}

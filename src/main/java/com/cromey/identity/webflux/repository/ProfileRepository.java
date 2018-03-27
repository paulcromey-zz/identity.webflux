package com.cromey.identity.webflux.repository;

import com.cromey.identity.webflux.model.Profile;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

@ComponentScan
public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {

    Flux<Profile> findByEmail(String email);

}

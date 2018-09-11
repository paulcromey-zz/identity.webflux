package com.cromey.identity.repository;

import com.cromey.identity.model.Profile;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@ComponentScan
public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {

}

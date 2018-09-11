package com.cromey.identity.validator;

import com.cromey.identity.repository.ProfileRepository;
import com.cromey.identity.error.ErrorCodes;
import com.cromey.identity.model.Profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProfileValidator implements Validator {

    private static Logger logger = LoggerFactory.getLogger(ProfileValidator.class);

    private ProfileRepository profileRepository;

    @Autowired
    public ProfileValidator(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Profile.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        logger.info("validate profile");

        Profile profile = (Profile) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", ErrorCodes.MISSING_PROFILE_EMAIL.name(), "required");
    }
}

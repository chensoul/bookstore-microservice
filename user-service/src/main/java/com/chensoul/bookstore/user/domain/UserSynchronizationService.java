package com.chensoul.bookstore.user.domain;

import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Synchronize users with the database after successful login.
 */
@Service
public class UserSynchronizationService {

    private static final Logger log = LoggerFactory.getLogger(UserSynchronizationService.class);

    private final UserRepository userRepository;

    public UserSynchronizationService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void syncWithDatabase(final Map<String, Object> claims, final String loginType) {
        final String subject = claims.get("id").toString();
        Optional<UserEntity> userEntityOptional = userRepository.getByOpenid(subject);
        UserEntity userEntity = null;
        if (!userEntityOptional.isPresent()) {
            log.info("adding new user after successful login: {}", subject);
            userEntity = new UserEntity();
            userEntity.setName(claims.get("name").toString());
            userEntity.setOpenid(subject);
            userEntity.setLoginType(loginType);
        } else {
            userEntity = userEntityOptional.get();
            userEntity.setName(claims.get("name").toString());
            userEntity.setLoginType(loginType);
            log.info("updating existing user after successful login: {}", subject);
        }
        userRepository.save(userEntity);
    }

    @EventListener(AuthenticationSuccessEvent.class)
    public void onAuthenticationSuccessEvent(final AuthenticationSuccessEvent event) {
        if (event.getSource() instanceof OAuth2LoginAuthenticationToken oauthToken
                && "github".equals(oauthToken.getClientRegistration().getRegistrationId())) {
            syncWithDatabase(
                    oauthToken.getPrincipal().getAttributes(),
                    oauthToken.getClientRegistration().getRegistrationId());
        }
    }
}

package com.chensoul.bookstore.webapp.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class FormSecurityConfig {
    private final ClientRegistrationRepository clientRegistrationRepository;

    public FormSecurityConfig(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // creates hashes with {bcrypt} prefix
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public GrantedAuthoritiesMapper formUserAuthoritiesMapper() {
        return oauthAuthorities -> {
            final String role = "USER";
            final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
            return authorities;
        };
    }

    @Bean
    public SecurityFilterChain formFilterChain(
            final HttpSecurity http,
            @Qualifier("formUserAuthoritiesMapper") final GrantedAuthoritiesMapper formUserAuthoritiesMapper)
            throws Exception {
        return http.cors(withDefaults())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/actuator/**"))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .formLogin(form ->
                        form.loginPage("/login").usernameParameter("name").failureUrl("/login?loginError=true"))
                .oauth2Login(oauth2 -> oauth2.loginPage("/login")
                        .failureUrl("/login?loginError=true")
                        .userInfoEndpoint(userInfo -> userInfo.userAuthoritiesMapper(formUserAuthoritiesMapper)))
                .logout(logout -> logout.clearAuthentication(true)
                        .logoutSuccessUrl("/?logoutSuccess=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("SESSION")
                        .logoutSuccessHandler(oidcLogoutSuccessHandler()))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/login?loginRequired=true")))
                .build();
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
        return oidcLogoutSuccessHandler;
    }
}

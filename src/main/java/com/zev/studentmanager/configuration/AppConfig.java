package com.zev.studentmanager.configuration;

import com.zev.studentmanager.Auditing.ApplicationAuditing;
import com.zev.studentmanager.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("!prod")
@RequiredArgsConstructor
@EnableJpaAuditing
public class AppConfig {
    private final UserService userService;
    private final PreFilter preFilter;

    private final String[] WHITE_LIST = {
      "/auth/**","/user/**"
    };



    // cors configuration
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                       .allowedOrigins("http://localhost:80")
                       .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                       .allowedHeaders("*")
                       .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

    // create password encoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // create security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(@NonNull HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)  // disable ui logging springboot security
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest.requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated())  // configure api can pass
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // configure allow set token on session
                .authenticationProvider(provider()).addFilterBefore(preFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    // create provider
    @Bean
    public AuthenticationProvider provider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService.userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // allow api swagger pass filter
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return webSecurity -> webSecurity.ignoring()
                .requestMatchers("/v3/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**");
    }

    // auditing configuration
    @Bean
    public AuditorAware<Long> auditorAware(){
        return new ApplicationAuditing();
    }
}

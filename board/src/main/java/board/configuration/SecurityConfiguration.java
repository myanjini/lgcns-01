package board.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import board.security.CustomAuthenticationSuccessHandler;
import board.security.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;
    
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login", "/home", "/join", "/joinProc").permitAll()
            .requestMatchers("/board", "/board/**", "/api/**").hasAnyRole("ADMIN", "USER")
            .anyRequest().authenticated()
        );
        http.formLogin(auth -> auth
            .loginPage("/login")
            .loginProcessingUrl("/loginProc")
            .permitAll()
            .successHandler(successHandler)
        );
        
        http.csrf(auth -> auth.disable());
        
        http.sessionManagement(auth -> auth
            .sessionFixation(ses -> ses.newSession())
            .maximumSessions(1)
            .maxSessionsPreventsLogin(true)
        );
        
        // http.sessionManagement(auth -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        http.logout(auth -> auth.logoutUrl("/logout").logoutSuccessUrl("/"));
        
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

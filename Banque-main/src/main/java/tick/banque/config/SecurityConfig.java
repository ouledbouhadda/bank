package tick.banque.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(requests -> requests
                        .requestMatchers("/login", "/login?logout", "/login?error").permitAll()
                        .requestMatchers("/admin/**", "/uploads/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/client/**").hasAuthority("ROLE_CLIENT")
                        .requestMatchers("/agent/**").hasAnyAuthority("ROLE_AGENT", "ROLE_ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .successHandler(new CustomLoginSuccessHandler())
                        .permitAll()) // This enables and uses the default login page provided by Spring Security
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.example.application.security;

import com.example.application.data.postgres.Connect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String LOGIN_PROCESSING_URL = "/login";
  private static final String LOGIN_FAILURE_URL = "/login?error";
  private static final String LOGIN_URL = "/login";
  private static final String LOGOUT_SUCCESS_URL = "/login";

      Collection<UserDetails> users = new java.util.ArrayList<>();

  /**
   * Require login to access internal pages and configure login form.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // Vaadin handles CSRF internally
    http.csrf().disable()

        // Register our CustomRequestCache, which saves unauthorized access attempts, so the user is redirected after login.
        .requestCache().requestCache(new CustomRequestCache())

        // Restrict access to our application.
        .and().authorizeRequests()

        // Allow all Vaadin internal requests.
        .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

        // Allow all requests by logged-in users.
        .anyRequest().authenticated()

        // Configure the login page.
        .and().formLogin()
        .loginPage(LOGIN_URL).permitAll()
        .loginProcessingUrl(LOGIN_PROCESSING_URL)
        .failureUrl(LOGIN_FAILURE_URL)

        // Configure logout
        .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
  }


  @Bean
  @Override
  public UserDetailsService userDetailsService() {

    try {
      Connect connect = Connect.getInstance();
      Connection connection = connect.getConnection();

      String sql = "SELECT * FROM oop.login";
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);

      while (resultSet.next()) {
        String nim = resultSet.getString("nim");
        String password = resultSet.getString("password");
        String role = resultSet.getString("role");
        UserDetails user = User.withUsername(nim)
                .password("{noop}"+password)
                .roles(role)
                .build();
        users.add(user);
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
    return new InMemoryUserDetailsManager(users);

  }



  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers(
        // Client-side JS
        "/VAADIN/**",

        // the standard favicon URI
        "/favicon.ico",

        // the robot exclusion standard
        "/robots.txt",

        // web application manifest
        "/manifest.webmanifest",
        "/sw.js",
        "/offline.html",
            "/offline-stub.html",

        // icons and images
        "/icons/**",
        "/images/**",
        "/styles/**",

        // (development mode) H2 debugging console
        "/h2-console/**"
            , "/sw-runtime-resources-precache.js"
            );
  }
}
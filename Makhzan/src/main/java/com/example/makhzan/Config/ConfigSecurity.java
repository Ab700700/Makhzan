package com.example.makhzan.Config;

import com.example.makhzan.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {
    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/makhzan/customer/register","/api/v1/makhzan/landlord/register","/api/v1/makhzan/user/login","/api/v1/makhzan/storage/search/*/**", "/api/v1/makhzan/storage/search-storage-name/*/*/**", "/api/v1/makhzan/storage/search-storage-landlord-name/*/*/**","api/v1/makhzan/storage/get-reviews/**").permitAll()
                .requestMatchers("/api/v1/makhzan/customer/update","/api/v1/makhzan/customer/delete","/api/v1/makhzan/customer/user", "/api/v1/makhzan/order/add","/api/v1/makhzan/order/delete","/api/v1/makhzan/review/add", "/api/v1/makhzan/review/update/**","/api/v1/makhzan/review/delete/**", "/api/v1/makhzan/customer/orders","/api/v1/makhzan/customer/accepted-orders", "/api/v1/makhzan/customer/reject-orders", "/api/v1/makhzan/customer/pending-orders").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/makhzan/landlord/update","/api/v1/makhzan/landlord/delete","/api/v1/makhzan/landlord/accept/**","api/v1/makhzan/landlord/storage-orders/**","api/v1/makhzan/landlord/get-landlord","api/v1/makhzan/landlord/get-storages","api/v1/makhzan/storage/add","/api/v1/makhzan/media/add","api/v1/makhzan/media/update/**","api/v1/makhzan/media/delete/**").hasAuthority("LANDLORD")
                .requestMatchers("/api/v1/makhzan/order/get", "/api/v1/makhzan/order/get-accepted","/api/v1/makhzan/order/get-pending", "/api/v1/makhzan/order/get-rejected","/api/v1/makhzan/order/order-start-before/**", "/api/v1/makhzan/order/order-start-after/**","api/v1/makhzan/customer/get", "api/v1/makhzan/landlord/get","api/v1/makhzan/review/get", "api/v1/makhzan/storage/get","api/v1/makhzan/storage/verify/*/**", "/api/v1/makhzan/storage/get-city","/api/v1/makhzan/storage/get-size/**", "/api/v1/makhzan/storage/get-high","/api/v1/makhzan/storage/get-name/**", "/api/v1/makhzan/storage/get-storagename/**","/api/v1/makhzan/storage/get-available", "/api/v1/makhzan/storage/get-small","/api/v1/makhzan/storage/get-medium", "/api/v1/makhzan/storage/get-large","/api/v1/makhzan/storage/get-outside", "/api/v1/makhzan/storage/get-company", "/api/v1/makhzan/customer/get","/api/v1/makhzan/customer/mostrented","/api/v1/makhzan/customer/lessrented").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/makhzan/user/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return  http.build();
    }
}

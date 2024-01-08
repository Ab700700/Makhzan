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
                .requestMatchers("/api/v1/makhzan/customer/register","/api/v1/makhzan/landlord/register").permitAll()
                .requestMatchers("/api/v1/makhzan/customer/update","/api/v1/makhzan/customer/delete","/api/v1/makhzan/customer/user").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/makhzan/landlord/update","/api/v1/makhzan/landlord/delete","/api/v1/makhzan/landlord/accept/**","api/v1/makhzan/landlord/storage-orders/**","api/v1/makhzan/landlord/get-landlord","api/v1/makhzan/landlord/get-storages").hasAuthority("LANDLORD")
                .requestMatchers("/api/v1/makhzan/media/add","api/v1/makhzan/media/update/**","api/v1/makhzan/media/delete/**").hasAuthority("LANDLORD")
                .requestMatchers("/api/v1/makhzan/order/get","/api/v1/makhzan/order/get-accepted","/api/v1/makhzan/order/get-pending","/api/v1/makhzan/order/get-rejected","/api/v1/makhzan/order/get-copmleted","/api/v1/makhzan/order/order-start-before/**","/api/v1/makhzan/order/order-start-after/**","api/v1/makhzan/customer/get","api/v1/makhzan/landlord/get","api/v1/makhzan/review/get","api/v1/makhzan/storage/get","api/v1/makhzan/storage/verify/**/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/makhzan/customer/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return  http.build();
    }
}

package ru.geekbrains.dropbox.frontend.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.geekbrains.dropbox.frontend.service.SecUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    SecUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

//    @Autowired
//    public void configAuthBuilder(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

    //    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService());
//    }


//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication().passwordEncoder(passwordEncoder())
//                .withUser("a").password("a").roles("USER")
//                .and()
//                .withUser("user1").password("user1").roles("USER")
//                .and()
//                .withUser("user2").password("user2").roles("USER");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/vaadinServlet/**", "/VAADIN/**", "/PUSH/**",
                        "/UIDL/**",
                        "/start", "/start/**", "/auth", "/auth/**", "/login", "/login/**",
                        "/register", "/environment").permitAll()
                .antMatchers("/**").fullyAuthenticated()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/start"))
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

//    //    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence charSequence) {
//                return charSequence.toString();
//            }
//
//            @Override
//            public boolean matches(CharSequence charSequence, String s) {
//                return charSequence.toString().equals(s);
//            }
//        };
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean(name = "authenticationManager")
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}

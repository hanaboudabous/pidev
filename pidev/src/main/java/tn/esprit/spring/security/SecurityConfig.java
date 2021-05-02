package tn.esprit.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.services.MyUserDetailsService;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
	 @Autowired
	 MyUserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{

		//auth.userDetailsService(userDetailsService).passwordEncoder(encodePWD());
	}
	
    @Override
	protected void configure(HttpSecurity http) throws Exception
	{    
    	http.csrf().disable();
    	/*authorizeRequests().anyRequest().authenticated()
		.and().httpBasic();*/
	}
	
	public PasswordEncoder getencodePWD()
	{
		return new PasswordEncoder(){

			@Override
			public String encode(CharSequence c) {
				
				return c.toString();
			}

			@Override
			public boolean matches(CharSequence c, String a) {
				
				return true;
			}
			
		};
	}

	
	@Bean
	public BCryptPasswordEncoder encodePWD()
	{
		return new BCryptPasswordEncoder();
	}
}

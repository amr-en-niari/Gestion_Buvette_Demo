package www.uniregal.ma.Config;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ConfigurationApp extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsService userDetailsService;

	   @Bean
	   public BCryptPasswordEncoder bCryptPasswordEncoder() { 
	      return new BCryptPasswordEncoder();
	   } 
	   
	   @SuppressWarnings("deprecation")
	   @Bean
	   public PasswordEncoder passwordEncoder() {
	    return NoOpPasswordEncoder.getInstance();
	   }
	   @Override 
	   protected void configure(HttpSecurity http) throws Exception { 
		   http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(
					"/login",
					"/resources/**",
					"/css/**",
					"/js/**",
					"/img/**").permitAll()
			.antMatchers("/users/addNew").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login").permitAll()
			.and()
			.logout().invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login.html").permitAll();
	   }
	   
	    @Bean
		public AuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();		
			provider.setUserDetailsService(userDetailsService);	
			provider.setPasswordEncoder(passwordEncoder());
			return provider;
		}
}

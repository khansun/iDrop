package io.khansun.iDrop;

import io.khansun.iDrop.Models.AppUser;
import io.khansun.iDrop.Models.UserRole;
import io.khansun.iDrop.Services.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;
@SpringBootApplication
public class IDropApplication {

	public static void main(String[] args) {
		SpringApplication.run(IDropApplication.class, args);
	}
	@Bean
	CommandLineRunner run(AppUserService appUserService){
		return args -> 	{
			appUserService.saveUserRole(new UserRole(null,"ROLE_USER"));
			appUserService.saveUserRole(new UserRole(null,"ROLE_ADMIN"));
			appUserService.saveUserRole(new UserRole(null,"ROLE_SUPER_ADMIN"));
			appUserService.saveAppUser(new AppUser(null,"John Travolta","john","1234", new ArrayList<>()));
			appUserService.saveAppUser(new AppUser(null,"Will Smith","will","1234", new ArrayList<>()));
			appUserService.saveAppUser(new AppUser(null,"Jim Carry","jim","1234", new ArrayList<>()));
			appUserService.saveAppUser(new AppUser(null,"Arnold Schwarzenegger","arnold","1234", new ArrayList<>()));
			appUserService.addAppUserRole("john","ROLE_USER");
			appUserService.addAppUserRole("will","ROLE_USER");
			appUserService.addAppUserRole("jim","ROLE_ADMIN");
			appUserService.addAppUserRole("arnold","ROLE_SUPER_ADMIN");

		};
	}
	@Bean
	BCryptPasswordEncoder PasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);


		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With, *",
				"Access-Control-Request-Method", "Access-Control-Request-Headers", "*"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}


}

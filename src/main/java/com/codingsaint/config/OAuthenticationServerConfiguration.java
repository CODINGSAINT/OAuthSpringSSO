package com.codingsaint.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.codingsaint.security.CustomAuthenticationEntryPoint;
import com.codingsaint.security.CustomLogoutSuccessHandler;

/**
 * Responsibe for O Auth Implementation
 * 
 * @author The Saint
 *
 */
public class OAuthenticationServerConfiguration {

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfig extends
			ResourceServerConfigurerAdapter {
		@Autowired
		private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
		@Autowired
		private CustomLogoutSuccessHandler customLogoutSuccessHandler;

		@Override
		public void configure(HttpSecurity http) throws Exception {

			http.exceptionHandling()
					.authenticationEntryPoint(customAuthenticationEntryPoint)
					.and()
					.logout()
					.logoutUrl("/oauth/logout")//The Logout URL
					.logoutSuccessHandler(customLogoutSuccessHandler)
					.and()
					.csrf()
					.requireCsrfProtectionMatcher(
							new AntPathRequestMatcher("/oauth/authorize"))
					.disable().headers().frameOptions().disable()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and().authorizeRequests().antMatchers("/hello/**")
					.permitAll().antMatchers("/secure/**").authenticated();

		}

		/**
		 * <code>OAuth2Config</code> Enable OAuth Authorization
		 * 
		 * @author The Saint
		 *
		 */
		@Configuration
		@EnableAuthorizationServer
		protected static class OAuth2Config extends
				AuthorizationServerConfigurerAdapter {
			//default Client ID
			private static final String CLIENT_ID = "coding_saint_client";
			//default client secret
			private static final String CLIENT_SECRET = "coding_saint_client_secret";
			
			// Autowire AuthenticationManager
			// #org.springframework.security.authentication.AuthenticationManager
			// This is required to process Authentication
			@Autowired
			@Qualifier("authenticationManagerBean")
			private AuthenticationManager authenticationManager;

			// Use JDBC to keep Tokens and User Details
			@Autowired
			private DataSource dataSource;

			@Autowired
			private Environment env;

			// A TokenStore bean. Here the implementation is JdbcTokenStore
			@Bean
			public TokenStore tokenStore() {
				return new JdbcTokenStore(dataSource);
			}

			/**
			 * Auth Server Endpoints Config
			 */
			@Override
			public void configure(
					AuthorizationServerEndpointsConfigurer endpoints)
					throws Exception {
				endpoints.authenticationManager(authenticationManager)
						.tokenStore(tokenStore());
			}

			/**
			 * AuthServer Client Details Service
			 */
			@Override
			public void configure(ClientDetailsServiceConfigurer clients)
					throws Exception {
				clients.inMemory().withClient(CLIENT_ID).secret(CLIENT_SECRET)
						.authorizedGrantTypes("refresh_token", "password")
						.scopes("read", "write");
				// .accessTokenValiditySeconds(1800);
			}

		}
	}
}

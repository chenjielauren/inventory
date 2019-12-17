package com.silgan.inventory.security;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${ldap.urls}")
	private String ldapUrls;

	@Value("${ldap.base.dn}")
	private String ldapBaseDn;
	
	@Value("${ldap.username}")
	private String ldapSecurityPrincipal;
	 
	@Value("${ldap.password}")
	private String ldapPrincipalPassword;
	 
	@Value("${ldap.user.dn.pattern}")
	private String ldapUserDnPattern;
	 
	@Value("${ldap.user-search-filter}")
	private String userSearchFilter;
	
	@Value("${ldap.group-search-filter}")
	private String groupSearchFilter;
	 
	@Value("${ldap.group-search-base}")
	private String groupSearchBase;
	 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()			
				.anyRequest().fullyAuthenticated()
				.and()
			.formLogin()			
        	.failureUrl("/login?error")
        	.permitAll()
        	.and()
        .logout()
        	.invalidateHttpSession(true)
        	.deleteCookies("JSESSIONID")
        	.permitAll();
		http.cors().and().csrf().disable();
	}
	
	  @Override
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	      auth
	      .ldapAuthentication()
	      .userSearchFilter(userSearchFilter)
	      .groupSearchFilter(groupSearchFilter)
	      .groupSearchBase(groupSearchBase)
	      .contextSource()
	      .url(ldapUrls + ldapBaseDn)
		  .managerDn(ldapSecurityPrincipal)
		  .managerPassword(ldapPrincipalPassword)
		  .and()
		  .userDnPatterns(ldapUserDnPattern);
	  }
	  

}

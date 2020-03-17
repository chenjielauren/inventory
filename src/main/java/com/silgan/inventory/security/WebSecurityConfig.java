package com.silgan.inventory.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

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
	 
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//		.inMemoryAuthentication()
////		.withUser("lchen2").password("Witness900326").authorities("ROLE_ADMIN")
////		.and()
////		.withUser("lchen2").password("Witness900326").authorities("ROLE_INV")
////		.and()
//		.withUser("lchen2").password("Witness900326").authorities("ROLE_ITEM");
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()			
				.anyRequest().fullyAuthenticated()
				.and()
			.formLogin()			
        	.failureUrl("/login?error")
        	.permitAll()
        	.and()
        .logout()
        	.invalidateHttpSession(true)
        	.deleteCookies("JSESSIONID")
        	.permitAll()
		.and().csrf().disable();
	
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

	 public String search(String query) {
		 LdapUserDetailsImpl ldapDetails = (LdapUserDetailsImpl) SecurityContextHolder
		            .getContext().getAuthentication().getPrincipal();
		String dn = ldapDetails.getDn();
		int beginIndex = dn.indexOf("cn=") + 3;
		int endIndex = dn.indexOf(",");
		String username = dn.substring(beginIndex, endIndex);
	     return username;
	 }
	 
	 @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        return new DefaultSpringSecurityContextSource(Arrays.asList(ldapUrls), ldapBaseDn);
    }
	  
//public class WebSecurityConfig{
}

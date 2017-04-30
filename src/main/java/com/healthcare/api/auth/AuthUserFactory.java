package com.healthcare.api.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.healthcare.api.auth.model.AuthUser;
import com.healthcare.model.entity.Admin;
import com.healthcare.model.entity.Role;

/**
 * 
 * @author orange
 *
 */
public final class AuthUserFactory {
	private AuthUserFactory() {
	}

	public static AuthUser create(Admin user) {
		return new AuthUser(user.getId(), user.getUsername(), user.getFirst_name(), user.getLast_name(),
				user.getEmail(), user.getPassword(), mapToGrantedAuthorities(user.getRole()), (user.getStatus() == 1));
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(Role role) {
		List<GrantedAuthority> authoritys = new ArrayList<GrantedAuthority>();
		authoritys.add(new SimpleGrantedAuthority(role.getLevel_name()));
		return authoritys;
	}
}

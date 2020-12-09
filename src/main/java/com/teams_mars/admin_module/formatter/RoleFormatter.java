package com.teams_mars.admin_module.formatter;


import com.teams_mars.admin_module.domain.Role;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;


@Component
public class RoleFormatter implements Formatter<Role> {
	@Override
	public Role parse(String s, Locale locale) throws ParseException {
		return null;
	}

	@Override
	public String print(Role role, Locale locale) {
		return null;
	}

/*	@Autowired
	private RoleService roleService;
	
	@Override
	public String print(Role role, Locale locale) {
		System.out.println("***********");
		return String.valueOf(role.getId());
	}

	@Override
	public Role parse(String text, Locale locale) throws ParseException {
		System.out.println("-----------" + text);
		System.out.println(roleService.get(Long.parseLong(text)).getRole());
		return roleService.get(Long.parseLong(text));
	}*/

}

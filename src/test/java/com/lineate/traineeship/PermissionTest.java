package com.lineate.traineeship;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.Assert.assertTrue;

public class PermissionTest {

	@ParameterizedTest
	@EnumSource(Permission.class)
	public void testCheckFlagsPermission(Permission permission) {
		int level = permission.getLevel();
		String name = permission.name();
		assertTrue((name.equals("read") && level == 0) || (name.equals("write") && level == 1));
	}

}

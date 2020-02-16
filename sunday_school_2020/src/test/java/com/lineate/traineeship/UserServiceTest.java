package com.lineate.traineeship;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class UserServiceTest {

    @Test
    public void testCreateUserWithDefaultGroup() {
        UserService userService = ServiceFactory.createUserService();

        User boris = userService.createUser("Boris");

        SoftAssertions.assertSoftly(softy -> {
            softy.assertThat(boris).isNotNull()
                    .extracting(User::getName).isEqualTo("Boris");
            softy.assertThat(boris.getGroups())
                    .containsOnly(new Group("Boris"));
        });
    }

    @Test
    public void testCreateUserWithNewGroup() {
        UserService userService = ServiceFactory.createUserService();

        Group testGroup = userService.createGroup("TestGroupName");
        User boris = userService.createUser("Boris", testGroup);

        SoftAssertions.assertSoftly(softy -> {
            softy.assertThat(boris).isNotNull()
                    .extracting(User::getName).isEqualTo("Boris");
            softy.assertThat(boris.getGroups())
                    .contains(new Group("TestGroupName"));
        });
    }

    @Test
    public void testCreateGroup() {
        UserService userService = ServiceFactory.createUserService();

        Group testGroup = userService.createGroup("TestGroupName");

        SoftAssertions.assertSoftly(softy -> {
            softy.assertThat(testGroup).isNotNull()
                    .extracting(Group::getName).isEqualTo("TestGroupName");
        });
    }
}

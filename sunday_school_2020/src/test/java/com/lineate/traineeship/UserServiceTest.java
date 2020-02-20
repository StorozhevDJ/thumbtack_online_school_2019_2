package com.lineate.traineeship;

import static org.junit.Assert.assertTrue;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

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
    
    @Test
    public void testAddUserToGroup() {
        UserService userService = ServiceFactory.createUserService();
        
        User userBoris = userService.createUser("Boris");
        Group testGroup = userService.createGroup("TestGroupName");

        userService.addUserToGroup(userBoris, testGroup);
        
        SoftAssertions.assertSoftly(softy -> {
            softy.assertThat(userBoris.getGroups()).contains(new Group("TestGroupName"));
        });
    }
    
	@Test
    public void testCreateEntityUserSeveralGroups() {
    	UserService userService = ServiceFactory.createUserService();
    	EntityService entityService = ServiceFactory.createEntityService();

        Group testGroup1 = userService.createGroup("TestGroupName1");
        Group testGroup2 = userService.createGroup("TestGroupName2");
        
        User userBoris = userService.createUser("Boris", testGroup1);
        testGroup2.addUser(userBoris);
        
        assertTrue(entityService.save(userBoris, new Entity("EntityName", "EntityValue")));

        SoftAssertions.assertSoftly(softy -> {
            softy.assertThat(userBoris.getGroups()).contains(new Group("TestGroupName1"));
            softy.assertThat(userBoris.getGroups()).contains(new Group("TestGroupName2"));
        });
    }
	
    @Test
    public void testGetUsersFromGroup() {
    	UserService userService = ServiceFactory.createUserService();

        Group testGroup = userService.createGroup("TestGroupName1");
        
        User userBoris = userService.createUser("Boris", testGroup);
        User secondUser = userService.createUser("SecondUser", testGroup);
        
        SoftAssertions.assertSoftly(softy -> {
            softy.assertThat(testGroup.getUsers()).contains(userBoris);
            softy.assertThat(testGroup.getUsers()).contains(secondUser);
        });
    }
    
    @Test
    public void testDeleteUserFromGroup() {
    	UserService userService = ServiceFactory.createUserService();

        Group testGroup = userService.createGroup("TestGroupName1");
        
        User userBoris = userService.createUser("Boris", testGroup);
        User secondUser = userService.createUser("SecondUser", testGroup);
        
        assertTrue(testGroup.removeUser(secondUser));
        
        SoftAssertions.assertSoftly(softy -> {
            softy.assertThat(testGroup.getUsers()).doesNotContain(secondUser);
            softy.assertThat(testGroup.getUsers()).contains(userBoris);
        }
        );
        
    }
}

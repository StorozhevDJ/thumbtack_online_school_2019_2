package com.lineate.traineeship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

public class EntityRepositoryTest {

	@Test
	public void testSaveAndDeletEntityMock() {
		UserService userService = ServiceFactory.createUserService();
		EntityRepository entityRepository = mock(EntityRepository.class);

		EntityService entityService = ServiceFactory.createEntityService(entityRepository);

		User userBoris = userService.createUser("Boris");
		Group testGroup = userService.createGroup("TestGroupName");

		userService.addUserToGroup(userBoris, testGroup);

		Entity entity = new Entity("key", "123");
		when(entityRepository.save(entity)).thenReturn(true);
		when(entityRepository.delete(entity)).thenReturn(true);
		assertTrue(entityRepository.save(entity));

		User user = userService.createUser("Boris", userService.createGroup("admin"));
		assertTrue(entityService.save(user, entity));
		assertTrue(entityService.delete(user, entity));
	}

	@Test
	public void testSaveAndGetByNameDelete() {
		UserService userService = ServiceFactory.createUserService();
		EntityRepository entityRepository = mock(EntityRepository.class);
		EntityService entityService = ServiceFactory.createEntityService(entityRepository);

		Entity entity = new Entity("key", "123");
		when(entityRepository.save(entity)).thenReturn(true);
		assertTrue(entityRepository.save(entity));
		assertFalse(entityRepository.save(new Entity("key2", "1234")));

		User user = userService.createUser("Boris", userService.createGroup("admin"));
		assertTrue(entityService.save(user, entity));
	}

}

package com.lineate.traineeship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class EntityServiceTest {

	@Test
	public void testCreateEntity() {
		UserService userService = ServiceFactory.createUserService();
		EntityService entityService = ServiceFactory.createEntityService();

		Group adminGroup = userService.createGroup("admin");
		User userBoris = userService.createUser("Boris", adminGroup);

		Entity entity = new Entity("key");
		Entity entityNull = new Entity((String) null);

		Assertions.assertAll(() -> assertTrue(entityService.save(userBoris, entity)),
				() -> assertFalse(entityService.save(userBoris, entityNull)),
				() -> Assertions.assertThrows(IllegalArgumentException.class, () -> {// По идее должен кидаться
																						// IllegalArgumentException, но
																						// приходит NullPointerException
					entityService.save(userBoris, null);
				}),
				() -> Assertions.assertThrows(NullPointerException.class, () -> {// проверка для NullPointerException
					entityService.save(userBoris, null);
				}));
	}

	@Test
	public void testDeleteEntity() {
		UserService userService = ServiceFactory.createUserService();
		EntityService entityService = ServiceFactory.createEntityService();

		User userBoris = userService.createUser("Boris", userService.createGroup("admin"));
		User otherUser = userService.createUser("OtherUser");
		assertTrue(entityService.save(userBoris, new Entity("E1")));
		assertTrue(entityService.save(userBoris, new Entity("E2")));

		Assertions.assertAll(
				() -> assertTrue(entityService.delete(userBoris, new Entity("E2"))),
				() -> assertNull(entityService.getByName(userBoris, "E2")), // NullPointerException!
				() -> assertFalse(entityService.delete(otherUser, new Entity("E1"))));
	}

	@Test
	public void testCheckGetByNullAndErrorName() {
		Entity entity = new Entity("key", "123");
		assertNotNull(entity.getName());

		UserService userService = ServiceFactory.createUserService();
		EntityService entityService = ServiceFactory.createEntityService();

		Group adminGroup = userService.createGroup("admin");
		User userBoris = userService.createUser("Boris", adminGroup);

		assertTrue(entityService.save(userBoris, entity));

		Assertions.assertAll(
				() -> Assertions.assertEquals(entityService.getByName(userBoris, "key"), entity),
				() -> Assertions.assertEquals(entityService.getByName(userBoris, null), entity), // This test is fail with
																								  // "java.lang.NullPointerException"
				() -> Assertions.assertEquals(entityService.getByName(userBoris, ""), entity),
				() -> Assertions.assertNull(entityService.getByName(userBoris, "errorValue")));
	}

	@Test
	public void testCheckSetNullEntityName() {
		Entity entity = new Entity(null, "123");
		assertNull(entity.getName());

		UserService userService = ServiceFactory.createUserService();
		EntityService entityService = ServiceFactory.createEntityService();

		Group adminGroup = userService.createGroup("admin");
		User userBoris = userService.createUser("Boris", adminGroup);

		assertFalse(entityService.save(userBoris, entity));
	}

	@Test
	public void testChangeEntityValue() {
		UserService userService = ServiceFactory.createUserService();
		EntityService entityService = ServiceFactory.createEntityService();

		User userBoris = userService.createUser("Boris", userService.createGroup("admin"));
		Entity entity = new Entity("EntityName", "EntityValue");
		assertTrue(entityService.save(userBoris, entity));

		assertEquals("EntityValue", entityService.getByName(userBoris, "EntityName").getValue());

		entity.setValue("NewValue");
		assertTrue(entityService.save(userBoris, entity));

		assertEquals("NewValue", entityService.getByName(userBoris, "EntityName").getValue());
	}

	@Test
	public void testEntitySpaceSymbols() {
		UserService userService = ServiceFactory.createUserService();
		EntityService entityService = ServiceFactory.createEntityService();

		User userBoris = userService.createUser("Boris", userService.createGroup("admin"));
		assertFalse(entityService.save(userBoris, new Entity("Entity Name", "EntityValue")));
		assertFalse(entityService.save(userBoris, new Entity("EntityName ", "EntityValue")));

	}

	@Test
	public void testEntityNameLength() {
		UserService userService = ServiceFactory.createUserService();
		EntityService entityService = ServiceFactory.createEntityService();

		User userBoris = userService.createUser("Boris", userService.createGroup("admin"));
		Assertions.assertAll(
				() -> Assertions.assertTrue(
						entityService.save(userBoris, new Entity("EntityName123456789012345678901", "EntityValue"))), // len=31
				() -> Assertions.assertTrue(
						entityService.save(userBoris, new Entity("EntityName1234567890123456789012", "EntityValue"))), // len=32
				() -> Assertions.assertFalse(
						entityService.save(userBoris, new Entity("EntityName12345678901234567890123", "EntityValue"))), // len=33
				() -> Assertions.assertFalse(
						entityService.save(userBoris, new Entity("EntityName123456789012345678901234", "EntityValue")))// len=34
		);
	}

	@Test
	public void testEntityNameEmpty() {
		UserService userService = ServiceFactory.createUserService();
		EntityService entityService = ServiceFactory.createEntityService();

		User userBoris = userService.createUser("Boris", userService.createGroup("admin"));
		assertFalse(entityService.save(userBoris, new Entity("", "EntityValue")));
		assertFalse(entityService.save(userBoris, new Entity(null, "EntityValue")));
	}

	@Test
	public void testEntityPermission() {
		UserService userService = ServiceFactory.createUserService();
		EntityService entityService = ServiceFactory.createEntityService();

		Group adminGroup = userService.createGroup("admin");
		Group otherGroup = userService.createGroup("OtherGroup");
		User userBoris = userService.createUser("Boris", adminGroup);
		User userOther = userService.createUser("Other", otherGroup);

		Entity entity1 = new Entity("key", "123");
		Entity entity2 = new Entity("key2", "123");

		Assertions.assertAll(() -> assertTrue(entityService.save(userBoris, entity1)),
				() -> assertTrue(entityService.save(userBoris, entity2)),
				() -> assertFalse(entityService.save(userOther, entity1)),
				() -> assertFalse(entityService.save(userOther, entity2)));

		entityService.grantPermission(entity2, adminGroup, Permission.read);

		Assertions.assertAll(() -> assertTrue(entityService.save(userBoris, entity1)),
				() -> assertTrue(entityService.save(userBoris, entity2)),
				() -> assertFalse(entityService.save(userOther, entity1)),
				() -> assertFalse(entityService.save(userOther, entity2)));

		entityService.grantPermission(entity2, otherGroup, Permission.write);

		Assertions.assertAll(() -> assertTrue(entityService.save(userBoris, entity1)),
				() -> assertTrue(entityService.save(userBoris, entity2)),
				() -> assertFalse(entityService.save(userOther, entity1)),
				() -> assertTrue(entityService.save(userOther, entity2)));

		assertNotNull(entityService.getByName(userBoris, "key"));
		assertNull(entityService.getByName(userOther, "key"));
		mainq();
	}

	
	 public static void mainq() {
	        ArrayList<String> firstName = new ArrayList<String>(Arrays.asList("Максим", "Наташа", "Даша", "Юра"));
	        ArrayList<String> secondName = new ArrayList<String>(Arrays.asList("Иванов", "Петрова", "Сидорова", "Юрьев"));
	        ArrayList<String> total = new ArrayList<>();

	        int arrayLen = firstName.size();
	        if (arrayLen == secondName.size()) {
	        		for (int i = 0; i < arrayLen; i++) {
	        			total.add(firstName.get(i) + " " + secondName.get(i));
	        		}
	        }
	        
	        //ArrayList<String> test = new ArrayList<String>(Arrays.asList("Максим Иванов", "Наташа Петрова", "Даша Сидорова", "Юра Юрьев"));

	        System.out.println(total);

	    }
}

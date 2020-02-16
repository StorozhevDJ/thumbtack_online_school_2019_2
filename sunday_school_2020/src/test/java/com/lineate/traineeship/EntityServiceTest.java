package com.lineate.traineeship;

import org.junit.Assert;
import org.junit.Test;

public class EntityServiceTest {

    @Test
    public void testCreateEntity() {
        UserService userService = ServiceFactory.createUserService();
        EntityService entityService = ServiceFactory.createEntityService();

        Group adminGroup = userService.createGroup("admin");
        User userBoris = userService.createUser("Boris", adminGroup);

        Entity entity = new Entity("key", "123");

        Assert.assertTrue(entityService.save(userBoris, entity));
    }

    @Test
    public void testCheckNullName() {
        Entity entity = new Entity("key", "123");
        Assert.assertNotNull( entity.getName());


        UserService userService = ServiceFactory.createUserService();
        EntityService entityService = ServiceFactory.createEntityService();

        Group adminGroup = userService.createGroup("admin");
        User userBoris = userService.createUser("Boris", adminGroup);

        entityService.save(userBoris, entity);

        Assert.assertNotNull(entityService.getByName(userBoris,"Boris"));
    }

    @Test
    public void testCheckEntityName() {
        Entity entity = new Entity("key", "123");
        String value1 = entity.getValue();
        entity.setValue("456");
        Assert.assertNotEquals(value1, entity.getValue());
    }


}

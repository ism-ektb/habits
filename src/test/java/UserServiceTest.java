import module.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
    UserService userService = new UserService();


    @Test
    public void testCreate() {
        User user = new User("Иван", "2@2.2", "1234");
        User user1 = userService.create(user);
        Assertions.assertEquals(user1.getId(), 1L);
        User user2 = userService.create(user);
        Assertions.assertNull(user2);
        User newUser = new User("Иван", "7@8.9", "1234");
        User user3 = userService.create(newUser);
        Assertions.assertEquals(user3.getId(), 2L);
    }

    @Test
    public void testPatch() {
        User user = new User("Иван", "2@2.2", "1234");
        User user1 = userService.create(user);
        User patch = new User(null, "2@2.8", null);
        User userAfterPatch = userService.patch(1L, patch);
        Assertions.assertNotNull(userAfterPatch);
        User userAfterPatch1 = userService.patch(10L, patch);
        Assertions.assertNull(userAfterPatch1);

    }

    @Test
    public void testDelete() {
        User user = new User("Иван", "2@2.2", "1234");
        User user1 = userService.create(user);
        User userAfterDelete = userService.delete(10L);
        Assertions.assertNull(userAfterDelete);
        User userAfterDelete1 = userService.delete(1L);
        Assertions.assertNotNull(userAfterDelete1);
    }

}
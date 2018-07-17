package org.art.java_core.cglib.tx_manager;

import org.art.java_core.cglib.tx_manager.context.ApplicationContext;
import org.art.java_core.cglib.tx_manager.model.User;
import org.art.java_core.cglib.tx_manager.services.UserServiceImpl;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Application context tests.
 */
class AppTest {

    private static final String USER_SERVICE_NAME = "userService";

    private static ApplicationContext context;

    @BeforeAll
    static void initAll() {
        //Application context initialization
        context = new ApplicationContext();

        //User service registration
        context.registerService(USER_SERVICE_NAME, UserServiceImpl.class);
    }

    @BeforeEach
    void init() {
        UserServiceImpl userService = context.getService(USER_SERVICE_NAME, UserServiceImpl.class);
        userService.deleteAll();
    }

    @Test
    @DisplayName("User service 'singleton' test")
    void test0() {
        UserServiceImpl userService1 = context.getService(USER_SERVICE_NAME, UserServiceImpl.class);
        UserServiceImpl userService2 = context.getService(USER_SERVICE_NAME, UserServiceImpl.class);
        assertSame(userService1, userService2);
    }

    @Test
    @DisplayName("User 'save' test")
    void test1() {
        UserServiceImpl userService = context.getService(USER_SERVICE_NAME, UserServiceImpl.class);

        User u1 = new User(1L, "John", "Dou", 45);
        User u2 = new User(2L, "Larry", "Li", 21);
        User u3 = new User(3L, "Sam", "Martins", 34);
        User u4 = new User(4L, "William", "Gardner", 26);

        int initialSize = userService.getAll().size();

        userService.save(u1);
        userService.save(u2);
        userService.save(u3);
        userService.save(u4);

        List<User> users = userService.getAll();

        assertSame(initialSize + 4, users.size());
    }

    @Test
    @DisplayName("Users 'save all' test")
    void test2() {
        UserServiceImpl userService = context.getService(USER_SERVICE_NAME, UserServiceImpl.class);

        User u1 = new User(1L, "John", "Dou", 45);
        User u2 = new User(2L, "Larry", "Li", 21);
        User u3 = new User(3L, "Sam", "Martins", 34);
        User u4 = new User(4L, "William", "Gardner", 26);

        Collection<User> userList = new ArrayList<>();
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);
        userList.add(u4);

        int initialSize = userService.getAll().size();

        userService.saveAll(userList);

        List<User> users = userService.getAll();

        assertSame(initialSize + 4, users.size());
    }

    @Test
    @DisplayName("Users deletion test")
    void test3() {
        UserServiceImpl userService = context.getService(USER_SERVICE_NAME, UserServiceImpl.class);
        List<User> users = userService.getAll();
        users.forEach(user -> userService.delete(user.getId()));
        assertSame(0, userService.getAll().size());
    }

    @Test
    @DisplayName("Age rating recalculation test")
    void test4() {
        UserServiceImpl userService = context.getService(USER_SERVICE_NAME, UserServiceImpl.class);

        User u1 = new User(1L, "John", "Dou", 45);
        User u2 = new User(2L, "Larry", "Li", 21);
        User u3 = new User(3L, "Sam", "Martins", 34);
        User u4 = new User(4L, "William", "Gardner", 26);

        userService.save(u1);
        userService.save(u2);
        userService.save(u3);
        userService.saveAndRecalculateAgeRatings(u4);

        System.out.println(userService.getAll());

        assertAll(
                () -> assertTrue(u2.getCurrentAgeRating() > u4.getCurrentAgeRating()),
                () -> assertTrue(u4.getCurrentAgeRating() > u3.getCurrentAgeRating()),
                () -> assertTrue(u3.getCurrentAgeRating() > u1.getCurrentAgeRating())
        );
    }

    @Test
    @DisplayName("Transactional operation test")
    void test5() throws InterruptedException {
        UserServiceImpl userService = context.getService(USER_SERVICE_NAME, UserServiceImpl.class);

        User u1 = new User(1L, "John", "Dou", 45);
        User u2 = new User(2L, "Larry", "Li", 21);
        User u3 = new User(3L, "Sam", "Martins", 34);

        userService.save(u1);
        userService.save(u2);
        userService.save(u3);

        assertSame(3, userService.getAll().size());

        User u4 = new User(4L, "William", "Gardner", 26);

        Thread t1 = new Thread(() -> userService.saveAndRecalculateAgeRatings(u4));

        User u5 = new User(5L, "Harry", "Potter", 16);
        User u6 = new User(6L, "Richard", "Gof", 28);

        List<User> newUsers = new ArrayList<>();
        newUsers.add(u5);
        newUsers.add(u6);

        Thread t2 = new Thread(() -> userService.saveAll(newUsers));

        t1.start();
        Thread.sleep(10);
        t2.start();

        t1.join();
        t2.join();

        /*
           'u5' and 'u6' user's age ratings should not be recalculated because of
           transaction execution of the method invoked by the thread 't1'
         */
        assertAll(
                () -> assertEquals(0, u5.getCurrentAgeRating()),
                () -> assertEquals(0, u6.getCurrentAgeRating())
        );
    }

    @AfterAll
    static void tearDownAll() {
        context.close();
    }
}

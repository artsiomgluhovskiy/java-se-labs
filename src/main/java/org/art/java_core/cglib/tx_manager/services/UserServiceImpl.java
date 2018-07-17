package org.art.java_core.cglib.tx_manager.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.java_core.cglib.tx_manager.annotations.Transactional;
import org.art.java_core.cglib.tx_manager.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User service implementation.
 */
public class UserServiceImpl implements UserService {

    public static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private final Map<Long, User> userDbMock = new ConcurrentHashMap<>();

    public UserServiceImpl() {
        LOG.debug("UserServiceImpl: service instantiation");
    }

    @Transactional
    public User save(User user) {
        Objects.requireNonNull(user);
        userDbMock.put(user.getId(), user);
        return user;
    }

    @Transactional
    @Override
    public void saveAll(Collection<User> users) {
        Objects.requireNonNull(users);
        users.forEach(u -> userDbMock.put(u.getId(), u));
    }

    @Override
    public User get(Long id) {
        return userDbMock.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userDbMock.values());
    }

    @Transactional
    @Override
    public User update(User user) {
        Objects.requireNonNull(user);
        userDbMock.put(user.getId(), user);
        return user;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userDbMock.remove(id);
    }

    @Transactional
    @Override
    public int deleteAll() {
        int usersNumber = userDbMock.size();
        userDbMock.clear();
        return usersNumber;
    }

    /**
     * Complex update operation that should be executed in transaction (atomically).
     */
    @Transactional
    @Override
    public User saveAndRecalculateAgeRatings(User user) {
        save(user);
        AgeRatingCounter counter = new AgeRatingCounter();

        /*
          Artificial sleep (workload simulation). Is used in order to detect
          concurrent user db modification in tests.
        */
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignore) {
            //NOP
        }

        userDbMock
                .values()
                .stream()
                .sorted(Comparator.comparingInt(User::getAge).reversed())
                .forEach(u -> u.setCurrentAgeRating(counter.getAndIncrement()));
        return user;
    }

    private class AgeRatingCounter {

        private int current = 1;

        int getAndIncrement() {
            return current++;
        }
    }
}



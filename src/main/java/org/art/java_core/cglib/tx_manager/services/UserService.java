package org.art.java_core.cglib.tx_manager.services;

import org.art.java_core.cglib.tx_manager.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {

    User save(User user);

    void saveAll(Collection<User> users);

    User get(Long id);

    List<User> getAll();

    User update(User user);

    void delete(Long id);

    int deleteAll();

    User saveAndRecalculateAgeRatings(User user);
}

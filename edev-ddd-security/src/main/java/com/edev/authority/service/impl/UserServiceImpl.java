package com.edev.authority.service.impl;

import com.edev.authority.entity.User;
import com.edev.authority.service.UserService;
import com.edev.support.dao.BasicDao;

import java.util.Collection;

import static com.edev.utils.ValidUtils.*;

public class UserServiceImpl implements UserService {
    private final BasicDao dao;
    public UserServiceImpl(BasicDao dao) {
        this.dao = dao;
    }
    private void valid(User user) {
        isNull(user, "user");
        isNull(user.getId(), "id");
        isNull(user.getUsername(), "username");
    }
    @Override
    public Long register(User user) {
        valid(user);
        isError(userExists(user.getUsername()), "The username exists[%s]", user.getUsername());
        return dao.insert(user);
    }

    @Override
    public void modify(User user) {
        valid(user);
        User oldUser = load(user.getId());
        isError(!user.getUsername().equals(oldUser.getUsername()),
                "The username cannot modify[%s]", user.getUsername());
        dao.update(user);
    }

    @Override
    public void remove(Long userId) {
        dao.delete(userId, User.class);
    }

    @Override
    public User load(Long userId) {
        return dao.load(userId, User.class);
    }

    @Override
    public void removeByName(String username) {
        User template = new User();
        template.setUsername(username);
        dao.delete(template);
    }

    @Override
    public User loadByName(String username) {
        User template = new User();
        template.setUsername(username);
        Collection<User> users = dao.loadAll(template);
        if(users==null||users.isEmpty()) return null;
        return users.iterator().next();
    }

    @Override
    public boolean userExists(String username) {
        return loadByName(username)!=null;
    }
}

package com.edev.trade.authority.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.exception.ValidException;
import com.edev.trade.authority.entity.User;
import com.edev.trade.authority.service.UserService;
import lombok.NonNull;

import java.util.Collection;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final BasicDao dao;
    public UserServiceImpl(BasicDao dao) {
        this.dao = dao;
    }

    private void validUser(@NonNull User user) {
        if(user.getId()==null) throw new ValidException("The id is null");
        if(user.getName()==null) throw new ValidException("The name is null");
        if(user.getPassword()==null) throw new ValidException("The password is null");
    }

    @Override
    public Long register(@NonNull User user) {
        validUser(user);
        return dao.insert(user);
    }

    @Override
    public void modify(@NonNull User user) {
        validUser(user);
        dao.update(user);
    }

    @Override
    public void deleteById(@NonNull Long userId) {
        dao.delete(userId, User.class);
    }

    @Override
    public void delete(User template) {
        dao.delete(template);
    }

    @Override
    public User load(@NonNull Long userId) {
        return dao.load(userId, User.class);
    }

    @Override
    public User loadByName(@NonNull String userName) {
        User template = new User();
        template.setName(userName);
        Collection<User> users = dao.loadAll(template);
        if(users==null||users.isEmpty()) return null;
        return users.iterator().next();
    }

    @Override
    public void saveAll(@NonNull List<User> users) {
        users.forEach(this::validUser);
        dao.insertOrUpdateForList(users);
    }

    @Override
    public void deleteAll(@NonNull List<Long> userIds) {
        dao.deleteForList(userIds, User.class);
    }

    @Override
    public Collection<User> loadAll(@NonNull List<Long> userIds) {
        return dao.loadForList(userIds, User.class);
    }
}

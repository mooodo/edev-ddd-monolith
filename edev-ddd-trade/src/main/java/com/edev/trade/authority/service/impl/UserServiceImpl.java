package com.edev.trade.authority.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.exception.ValidException;
import com.edev.trade.authority.entity.User;
import com.edev.trade.authority.service.UserService;

import java.util.Collection;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final BasicDao dao;
    public UserServiceImpl(BasicDao dao) {
        this.dao = dao;
    }

    private void validUser(User user) {
        if(user.getId()==null) throw new ValidException("The id is null");
        if(user.getName()==null) throw new ValidException("The name is null");
        if(user.getPassword()==null) throw new ValidException("The password is null");
    }

    @Override
    public Long register(User user) {
        validUser(user);
        return dao.insert(user);
    }

    @Override
    public void modify(User user) {
        validUser(user);
        dao.update(user);
    }

    @Override
    public void deleteById(Long userId) {
        dao.delete(userId, User.class);
    }

    @Override
    public void delete(User user) {
        dao.delete(user);
    }

    @Override
    public User load(Long userId) {
        return dao.load(userId, User.class);
    }

    @Override
    public User loadByName(String userName) {
        User template = User.build();
        template.setName(userName);
        Collection<User> users = dao.loadAll(template);
        if(users==null||users.isEmpty()) return null;
        return users.iterator().next();
    }

    @Override
    public void saveAll(List<User> users) {
        users.forEach(this::validUser);
        dao.insertOrUpdateForList(users);
    }

    @Override
    public void deleteAll(List<Long> ids) {
        dao.deleteForList(ids, User.class);
    }

    @Override
    public Collection<User> loadAll(List<Long> ids) {
        return dao.loadForList(ids, User.class);
    }
}

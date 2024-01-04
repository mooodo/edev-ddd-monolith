package com.edev.trade.authority.service;

import com.edev.trade.authority.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    Long register(User user);
    void modify(User user);
    void deleteById(Long userId);
    void delete(User template);
    User load(Long userId);
    User loadByName(String userName);
    void saveAll(List<User> users);
    void deleteAll(List<Long> userIds);
    Collection<User> loadAll(List<Long> userIds);
}

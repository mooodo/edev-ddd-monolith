package com.edev.trade.authority.service;

import com.edev.trade.authority.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    Long register(User user);
    void modify(User user);
    void deleteById(Long userId);
    void delete(User user);
    User load(Long userId);
    void saveAll(List<User> users);
    void deleteAll(List<Long> ids);
    Collection<User> loadAll(List<Long> ids);
}

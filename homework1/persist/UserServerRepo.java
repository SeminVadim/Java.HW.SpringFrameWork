package ru.geekbrains.persist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserServerRepo {

    private Map<Long, UserServer> userMap = new ConcurrentHashMap<>();
    private AtomicLong identity = new AtomicLong(0);


    public List<UserServer> findAll(){
        //return (List<UserServer>) userMap.values();
        return new ArrayList<>(userMap.values());
    }

    public UserServer findById(long id) {
        return userMap.get(id);
    }

    public void insertUser (UserServer user) {
        long id = identity.incrementAndGet();
        user.setId(id);
        userMap.put(id,user);
    }
    public void updateUser (UserServer user) {
        userMap.put(user.getId(),user);
    }
    public void deleteUser (long id) {
        userMap.remove(id);
    }

}

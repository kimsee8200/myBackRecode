package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private SqlSession session;

    private String namespace = "com.fastcampus.ch4.dao.UserMapper.";

    @Override
    public int deleteUser(String id){
        Map map = new HashMap();
        map.put("id",id);
        return session.delete(namespace+"delete",map);
    }

    @Override
    public User selectUser(String id) throws Exception {
        Map map = new HashMap();
        map.put("id",id);
        return session.selectOne(namespace+"select",map);
    }

    // 사용자 정보를 user_info테이블에 저장하는 메서드
    @Override
    public int insertUser(User user) throws Exception {
        return session.insert(namespace+"insert",user);
    }

    @Override
    public int updateUser(User user) throws Exception {
        return session.update(namespace+"update",user);
    }

    @Override
    public int count() throws Exception {
       return session.selectOne(namespace+"count");
    }

    @Override
    public int deleteAll() throws Exception {
        return session.delete(namespace+"deleteAll");
    }
}
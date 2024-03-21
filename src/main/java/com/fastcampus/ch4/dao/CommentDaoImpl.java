package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.CommentDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDaoImpl implements CommentDao {
    @Autowired
    SqlSession session;

    String namespace = "com.fastcampus.ch4.dao.CommentMapper.";

    @Override
    public int count(Integer bno)throws Exception{
        return session.selectOne(namespace+"count",bno);
    }

    @Override
    public CommentDto select(Integer cno) throws Exception {
        return session.selectOne(namespace+"select", cno);
    }

    @Override
    public List<CommentDto> selectAll(Integer bno)throws Exception {
        return session.selectList(namespace+"selectAll",bno);
    }

    @Override
    public int insert(CommentDto co) throws Exception{
        return session.insert(namespace+"insert",co);
    }

    @Override
    public int update(CommentDto co) throws Exception {
        return session.update(namespace+"update", co);
    }

    @Override
    public int delete(Integer cno, String commenter)throws Exception{
        Map map = new HashMap();
        map.put("cno",cno);
        map.put("commenter",commenter);
        return session.delete(namespace+"delete",map);
    }

    @Override
    public int deleteAll(Integer bno) throws Exception{
        return session.delete(namespace+"deleteAll", bno);
    }

}

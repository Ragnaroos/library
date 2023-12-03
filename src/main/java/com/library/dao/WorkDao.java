package com.library.dao;

import com.library.bean.User;
import com.library.bean.Work;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkDao {
    private final static String NAMESPACE = "com.library.dao.WorkDao.";
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public ArrayList<Work> selectAllWork(){
        List<Work> works = sqlSessionTemplate.selectList(NAMESPACE+"selectAllWork");
        return (ArrayList<Work>) works;
    }

    public Work selectByWorkName(String work_name){
        return sqlSessionTemplate.selectOne(NAMESPACE+"selectByWorkName", work_name);
    }

    public int updateWork(Work work){
        return sqlSessionTemplate.update(NAMESPACE+"updateWork", work);
    }

    public int deleteWorkByName(String work_name){
        return sqlSessionTemplate.delete(NAMESPACE+"deleteWorkByName", work_name);
    }

    public int insertWork(Work work){
        return sqlSessionTemplate.insert(NAMESPACE+"insertWork", work);
    }

    public ArrayList<Work> queryWork(final String searchWord) {
        String search = "%" + searchWord + "%";
        List<Work> result = sqlSessionTemplate.selectList(NAMESPACE + "queryWork", search);
        return (ArrayList<Work>) result;
    }
}

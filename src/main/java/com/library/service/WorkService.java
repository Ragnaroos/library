package com.library.service;

import com.library.bean.Work;
import com.library.dao.WorkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WorkService {
    @Autowired
    private WorkDao workDao;

    public ArrayList<Work> selectAllWork(){
        return workDao.selectAllWork();
    }

    public Work selectByWorkName(String work_name){
        return workDao.selectByWorkName(work_name);
    }

    public boolean updateWork(Work work){return workDao.updateWork(work)>0;}

    public boolean deleteWorkByName(String work_name){
        return workDao.deleteWorkByName(work_name)>0;
    }

    public boolean insertWork(Work work){
        return workDao.insertWork(work)>0;
    }

    public ArrayList<Work> queryWork(String searchWord){
        return workDao.queryWork(searchWord);
    }
}

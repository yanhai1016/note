package com.yh.node.service;

import com.yh.node.dao.NotebookDao;
import com.yh.node.dao.NotebookTypeDao;
import com.yh.node.entity.Notebook;
import com.yh.node.entity.NotebookType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class NotebookService {

    @Autowired
    private NotebookDao dao;

    @Autowired
    private NotebookTypeDao notebookTypeDao;

    @Transactional
    public List<Notebook> findBySpecial(String userId){
        return dao.findBySpecial(userId);
    }

    @Transactional
    public List<Notebook> findByNormal(String userId){
        return dao.findByNormal(userId);
    }

    @Transactional
    public void deleteNotebook(String id){
        dao.delete(id);
    }

    @Transactional
    public Map<String, Object> addNotebook(String notebookName, String userId){
        Map<String, Object> result=new HashMap<String, Object>();
        if(notebookName==null||notebookName.trim().length()==0){
            result.put("success",false);
            result.put("name_null",true);
            return result;
        }
        Notebook nb=new Notebook();
        nb.setName(notebookName);
        nb.setUserId(userId);
        Notebook notebook=dao.findByName(nb);
        if(notebook!=null){
            result.put("success",false);
            result.put("name_repeat",true);
            return result;
        }
        nb.setName(nb.getName().trim());
        nb.setId(UUID.randomUUID().toString());
        nb.setCreateTime(new Date());
        NotebookType nbt=notebookTypeDao.findNormal();
        nb.setNotebookTypeId(nbt.getId());
        System.out.println(nb);
        dao.add(nb);
        result.put("success",true);
        return result;
    }

    @Transactional
    public Map<String, Object> updateNotebook(Notebook nb){
        Map<String, Object> result=new HashMap<String, Object>();
        if(nb.getName()==null||nb.getName().trim().length()==0){
            result.put("success",false);
            result.put("name_null",true);
            return result;
        }
        Notebook notebook=dao.findByName(nb);
        if(notebook!=null){
            result.put("success",false);
            result.put("name_repeat",true);
            return result;
        }
        dao.update(nb);
        result.put("success",true);
        return result;
    }

    @Transactional
    public void initSpecialNotebook(String userId){
        List<NotebookType> nbts=notebookTypeDao.findSpecial();
        for(NotebookType nbt: nbts){
            Notebook nb=new Notebook();
            nb.setName(nbt.gettDesc());
            nb.setNotebookTypeId(nbt.getId());
            nb.setCreateTime(new Date());
            nb.setId(UUID.randomUUID().toString());
            nb.setUserId(userId);
            dao.add(nb);
        }
    }
}

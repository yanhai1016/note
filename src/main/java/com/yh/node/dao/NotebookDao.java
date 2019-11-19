package com.yh.node.dao;



import com.yh.node.entity.Notebook;

import java.util.List;

public interface NotebookDao {
    void add(Notebook notebook);
    void uupdate(Notebook notebook);
    void delete(String id);
    List<Notebook> findBySpecail(String userId);
    List<Notebook> findByNormal(String userId);

}

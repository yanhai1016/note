package com.yh.node.dao;


import com.yh.node.entity.NotebookType;

import java.util.List;

public interface NotebookTypeDao {
    NotebookType findNormal();
    List<NotebookType> findSpecail();
}

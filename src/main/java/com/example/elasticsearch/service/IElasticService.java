package com.example.elasticsearch.service;

import com.example.elasticsearch.bean.DocBean;
import org.springframework.data.domain.Page;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface IElasticService {

//    void createIndex();
//
//    void deleteIndex(String index);

    void deleteById(Long id);

    DocBean insert (DocBean docBean);

    void update(DocBean docBean);

    void save(DocBean docBean);

    void saveAll(List<DocBean> list);

    Iterator<DocBean> findAll();

    Optional<DocBean> findById(Long id);

    Page<DocBean> findByContent(String content);

    Page<DocBean> findByFirstCode(String firstCode);

    Page<DocBean> findBySecordCode(String secordCode);

    Page<DocBean> query(String key);
}


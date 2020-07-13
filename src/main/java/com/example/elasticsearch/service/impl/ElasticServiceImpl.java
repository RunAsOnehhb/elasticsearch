package com.example.elasticsearch.service.impl;
import com.example.elasticsearch.bean.DocBean;
import com.example.elasticsearch.dao.ElasticRepository;
import com.example.elasticsearch.service.IElasticService;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptService;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.elasticsearch.script.ScriptType.INLINE;

@Service("elasticService")
public class ElasticServiceImpl implements IElasticService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Autowired
    private ElasticRepository elasticRepository;

    private Pageable pageable = PageRequest.of(0,10);

//    @Override
//    public void createIndex() {
//        elasticsearchTemplate.createIndex(DocBean.class);
//    }
//
//    @Override
//    public void deleteIndex(String index) {
//        elasticsearchTemplate.deleteIndex(index);
//    }

    @Override
    public void deleteById(Long id){
        elasticsearchTemplate.delete("ems","_doc", String.valueOf(id));

    }

    @Override
    public DocBean insert(DocBean docBean){
       return elasticRepository.save(docBean);
    }

    @Override
    public void update(DocBean docBean){
        HashMap<String,Object> params=new HashMap<>();
       System.out.println( elasticRepository.findById(docBean.getId()));
        params.put("id",docBean.getId());
        params.put("content",docBean.getContent());
        params.put("firstCode",docBean.getFirstCode());
        params.put("secondCode",docBean.getSecordCode());
        params.put("type",docBean.getType());
        UpdateRequest updateRequest =new UpdateRequest().doc(params);
        UpdateRequest updateRequest1 =new UpdateRequest().doc(params);
         UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withIndexName("ems")
                .withType("_doc")
                .withId(String.valueOf(docBean.getId()))
                 .withUpdateRequest(updateRequest1)
                .build();
         updateQuery.setDoUpsert(true);
System.out.println(updateQuery.getUpdateRequest());
        elasticsearchTemplate.update(updateQuery);
    }

    @Override
    public void save(DocBean docBean) {

        elasticRepository.save(docBean);
    }

    @Override
    public void saveAll(List<DocBean> list) {
        elasticRepository.saveAll(list);
    }

    @Override
    public Iterator<DocBean> findAll() {
        return elasticRepository.findAll().iterator();
    }
    @Override
    public Optional<DocBean> findById(Long id) {
       return elasticRepository.findById(id);
    }

    @Override
    public Page<DocBean> findByContent(String content) {
        return elasticRepository.findByContent(content,pageable);
    }

    @Override
    public Page<DocBean> findByFirstCode(String firstCode) {
        return elasticRepository.findByFirstCode(firstCode,pageable);
    }

    @Override
    public Page<DocBean> findBySecordCode(String secordCode) {
        return elasticRepository.findBySecordCode(secordCode,pageable);
    }

    @Override
    public Page<DocBean> query(String key) {
        return elasticRepository.findByContent(key,pageable);
    }
}


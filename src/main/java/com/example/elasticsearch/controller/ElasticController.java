package com.example.elasticsearch.controller;


import com.example.elasticsearch.bean.DocBean;
//import com.example.elasticsearch.bean.FundInfoBean;
import com.example.elasticsearch.service.IElasticService;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/elastic")
public class ElasticController {

    @Autowired
    private IElasticService elasticService;

    @RequestMapping(value = "/demo", method = RequestMethod.DELETE)
    public void deleteOne(Long id){
         elasticService.deleteById(id);
    }
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public Optional<DocBean> FindById(Long id){
        return elasticService.findById(id);
    }
    @RequestMapping(value = "/demo",method = RequestMethod.POST)
    public DocBean insert(DocBean docBean){
      return elasticService.insert(docBean);
    }
    @RequestMapping(value = "/demo",method = RequestMethod.PUT)
    public void update(DocBean docBean){
        elasticService.save(docBean);
    }
//    @RequestMapping(value = "/demo2",method = RequestMethod.PUT)
//    public void update1(DocBean docBean){
//        System.out.println(docBean);
//        elasticService.findById(docBean.getId());
//        System.out.println(elasticService.findById(docBean.getId()));
//        elasticService.update(docBean);
//    }
}


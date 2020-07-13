package com.example.elasticsearch.bean;

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.script.Script;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * 提供es的工具类
 *
 * @author neo_chen
 * @version Ver 1.0 2018/11/16 新增
 * @Package
 */
public class EsUtil {

    /**
     * 在es的对应字段上追加对应的值
     * @param id  要追加的文档的id
     * @param clazz 文档对应Class对象
     * @param addFiled 要追加的字段
     * @param addParam 要追加的值
     * @return UpdateQuery
     * @eg 给这个文档的browseCount字段值+1
     * POST /#{index}/#{type}/#{id}/_update
     *  {
     *    "script": {
     *      "inline": "ctx._source.browseCount += params.incr",
     *      "params": {
     *        "incr":1
     *      }
     *    }
     *  }
     */
    public static <T>UpdateQuery filedValueAdd(String id, Class<T> clazz, String addFiled, Object addParam) {
        //构建UpdataRequest对象
        UpdateRequest updateRequest = new UpdateRequest();
        //设置参数
        Map<String, Object> params = new HashMap<>();
        params.put("incr", addParam);
        //构架Script对象,这里注意是 org.elasticsearch.script.Script
        Script script = new Script(Script.DEFAULT_SCRIPT_TYPE, "painless", "ctx._source." + addFiled + " += params.incr", params);
        updateRequest.script(script);
        //构架UpdateQueryBuilder 对象
        UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder();
        //设置要修改的_id
        updateQueryBuilder.withId(id);
        //设置updateRequest对象
        updateQueryBuilder.withUpdateRequest(updateRequest);
        //设置 class 对象,其实这里用class对象就是要区实体类标注的 index 和 type 也可以直接设置index 和 type
        /*
        //可以替换成
        updateQueryBuilder.withIndexName("your index");
        updateQueryBuilder.withType("yourType");
        */
        updateQueryBuilder.withClass(clazz);
        UpdateQuery build = updateQueryBuilder.build();
        return build;
    }

}

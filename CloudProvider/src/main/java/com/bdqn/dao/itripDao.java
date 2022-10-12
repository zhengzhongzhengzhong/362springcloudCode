package com.bdqn.dao;

import com.alibaba.fastjson.JSON;
import com.bdqn.Espojo.JsonRootBean;
import com.bdqn.Espojo.ListObject;
import com.bdqn.util.Client;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

import java.io.IOException;

public class itripDao {
    public static void main(String[] args) throws IOException {
        search();
    }

    public static void search() throws IOException {

        String str="{\n" +
                "   \"from\": 0, \n" +
                "   \"size\": 3, \n" +
                "   \"sort\": [\n" +
                "     {\n" +
                "       \"id\": {\n" +
                "         \"order\": \"asc\"\n" +
                "       }\n" +
                "     }\n" +
                "   ]\n" +
                "}\n";

        Search
                search=new Search.Builder(str).addIndex("website")
                .addType("logs").build();
        JestClient
                client = Client.getInstance().getJestClient();
        //调用集群返回测试结果
        JestResult result = client.execute(search);
        System.out.println(result.getJsonString());
        //把es 查询json结果转成java 对象
        JsonRootBean resultNew= JSON.parseObject(result.getJsonString(), JsonRootBean.class);

        for(ListObject obj:resultNew.getHits().getHits())
        {
            System.out.println(obj._source.getId()+obj._source.getHotelName());
            System.out.println(obj._source.getAddress());
        }


    }
}

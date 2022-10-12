package com.bdqn.util;

import com.alibaba.fastjson.JSON;
import com.bdqn.Espojo.JsonRootBean;
import com.bdqn.Espojo.ListObject;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.cluster.Health;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Date;

public class TextES {
    public static void main(String[] args) throws Exception {
       //判断健康状态
        // Heath();
      //  insert();
     // delete();
        search();

    }
    public static void search() throws IOException {

        String str="{\n" +
                "  \"from\": 0,\n" +
                "  \"size\": 5, \n" +
                "  \"sort\": [\n" +
                "    {\n" +
                "      \"id\": {\n" +
                "        \"order\": \"asc\"\n" +
                "      }\n" +
                "    }\n" +
                "  ], \n" +
                "  \"query\": {\n" +
                "    \"match_all\": {}\n" +
                "  }\n" +
                "}";

        Search
                search=new Search.Builder(str).addIndex("userdb")
                .addType("utable").build();
        JestClient
                client = Client.getInstance().getJestClient();
        //调用集群返回测试结果
        JestResult result = client.execute(search);
        System.out.println(result.getJsonString());
        //把es 查询json结果转成java 对象
        JsonRootBean resultNew=JSON.parseObject(result.getJsonString(), JsonRootBean.class);

        for(ListObject obj:resultNew.getHits().getHits())
        {
            System.out.println(obj._source.getName());
        }


    }

    public static void delete() throws IOException {
        //6.0
        Delete index = new Delete.Builder("AYOg8_m40Hgx51uiNvad").index("userdb").type("utable").build();
        JestClient
                client = Client.getInstance().getJestClient();
        //调用集群返回测试结果
        JestResult result = client.execute(index);
        System.out.println(result.getJsonString());
    }

    public static void insert() throws IOException {

        for(int i=1;i<=100;i++) {
            //6.0
            Userpojo pojo = new Userpojo();
            pojo.setId(i);
            pojo.setName("测试名字"+i);
            pojo.setMark("备注");
            Index index = new Index.Builder(pojo).index("userdb").type("utable").id(""+i).build();
            JestClient
                    client = Client.getInstance().getJestClient();
            //调用集群返回测试结果
            JestResult result = client.execute(index);
            System.out.println(result.getJsonString());
        }
    }
    public static void Heath() throws Exception {
        //通过searchbox包实例化健康值对象
        Health health=new Health.Builder().build();
        //调用jestClinet
        JestClient
                client=Client.getInstance().getJestClient();
        //调用集群返回测试结果
        JestResult result=client.execute(health);
        System.out.println(result.getJsonString());
    }
}

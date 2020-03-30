package com.mkl;

import com.mkl.config.RedisConfig;
import com.mkl.util.StrHashUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mkl
 * @date 2020/3/30 8:30
 * @description
 */
public class LuaApplication {
    private final static String luaAdd = "local hash = ARGV[1]"+ "\n" +
            "local bloomkey =  KEYS[1]"+ "\n" +
            "local size = redis.call('get',bloomkey+'size')"+ "\n" +
            "if (redis.call('exists',bloomkey)==0)"+ "\n" +
            "then"+ "\n" +
            "for i=1,string.len(hash) do"+ "\n" +
            "local a='0'"+ "\n" +
            "redis.call('setbit',bloomkey,i,string.sub(hash,i,i))"+ "\n" +
            "redis.call('set',bloomkey+'size','0')"+ "\n" +
            "end"+ "\n" +
            "else"+"\n"+
            "for i=1,string.len(hash) do"+"\n"+
            "if(string.sub(hash,i,i)==\"1\") then"+"\n"+
            "redis.call('setbit',bloomkey,i,'1')"+"\n"+
            "redis.call('INCR',bloomkey+'size')"+"\n"+
            "end"+"\n"+
            "end"+"\n"+
            "end";

    private final static String luaExists = "local hash = ARGV[1]"+ "\n" +
            "local hash = ARGV[1]"+ "\n" +
            "local bloomkey =  KEYS[1]"+ "\n" +
            "if (redis.call('EXISTS',bloomkey)==0)"+ "\n" +
            "then"+ "\n" +
            "return 'this bloomkey is null'"+ "\n" +
            "else"+ "\n" +
            "for i=1,string.len(hash) do"+ "\n" +
            "if(string.sub(hash,i,i)==\"1\") then"+ "\n" +
            "if(redis.call('getbit',bloomkey,i)=='0') then"+ "\n" +
            "return 'this object does not exist'"+ "\n" +
            "end"+ "\n" +
            "end"+ "\n" +
            "return 'this object exist'"+ "\n" +
            "end"+ "\n";
    /**
     * 测试添加字符串至布隆过滤器
     */
    public static void main(String[] args) {
        Jedis jedis = RedisConfig.getJedis();
        List<String> keys = new ArrayList<>();
        keys.add(StrHashUtil.getHashBiteArry("bloom1"));
        List<String> arggs = new ArrayList<>();
        arggs.add("mkl");
        String luaLoad = jedis.scriptLoad(luaAdd);
        System.out.println(luaLoad);
        Object obj = jedis.evalsha(luaLoad,keys,arggs);
        System.out.println(obj);
    }

    /**
     * 测试查询字符串是否存在
     */
//    public static void main(String[] args) {
//        Jedis jedis = RedisConfig.getJedis();
//        List<String> keys = new ArrayList<>();
//        keys.add("bloom1");
//        List<String> arggs = new ArrayList<>();
//        arggs.add("mkl");
//        String luaLoad = jedis.scriptLoad(luaExists);
//        System.out.println(luaLoad);
//        Object obj = jedis.evalsha(luaLoad,keys,arggs);
//        System.out.println(obj);
//    }

}

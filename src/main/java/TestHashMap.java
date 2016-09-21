import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * Created by wjf on 2016/8/4.
 */
public class TestHashMap {
    public static void main(String[] args){
        Map<String,Integer> map=new HashedMap();
        map.put("w",123);
        System.out.println(map.get("w"));
        int a=134217728;
        System.out.println(a/1024/1024);
    }
}
// java 接口中不能有已经实现的方法
interface A{
    public void a();
/*    public void b(){

    }*/

}

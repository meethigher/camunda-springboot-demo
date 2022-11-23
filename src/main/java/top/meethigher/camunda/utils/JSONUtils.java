package top.meethigher.camunda.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * json工具类
 *
 * @author chenchuancheng
 * @since 2022/11/24 8:51
 */
public class JSONUtils {

    /**
     * 对象转map
     *
     * @param object 对象
     * @return map
     */
    public static Map<String, Object> object2Map(Object object) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
        Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : entrySet) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}

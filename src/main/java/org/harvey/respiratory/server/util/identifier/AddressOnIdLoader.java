package org.harvey.respiratory.server.util.identifier;


import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * 加载地址字典
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-16 13:03
 */
public class AddressOnIdLoader {
    private volatile Map<String, ProvinceIdMessage> dict;

    /**
     * 高性能损耗
     */
    private static Map<String, ProvinceIdMessage> load() {
        try (JSONReader jsonReader = new JSONReader(new FileReader("src/main/resources/address_on_id.json"))) {
            jsonReader.config(Feature.SupportNonPublicField, true);
            return jsonReader.readObject(new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, ProvinceIdMessage> get() {
        if (dict != null) {
            return dict;
        }
        synchronized (this) {
            if (dict != null) {
                return dict;
            }
            dict = load();
        }
        return dict;
    }

}

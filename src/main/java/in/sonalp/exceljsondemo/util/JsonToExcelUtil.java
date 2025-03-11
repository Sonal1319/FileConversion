package in.sonalp.exceljsondemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonToExcelUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> parseJson(String json) throws Exception {
        return objectMapper.readValue(json, Map.class);
    }

    public static String convertMapToJson(Map<String, Object> map) throws Exception {
        return objectMapper.writeValueAsString(map);
    }
}
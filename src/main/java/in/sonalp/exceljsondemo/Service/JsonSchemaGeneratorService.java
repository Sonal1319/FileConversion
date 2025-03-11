package in.sonalp.exceljsondemo.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.victools.jsonschema.generator.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
@Service
public class JsonSchemaGeneratorService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public String generateSchema(List<Map<String, String>> data) {
        try {
            SchemaGeneratorConfig config = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2019_09).build();
            SchemaGenerator generator = new SchemaGenerator(config);
            Object schema = generator.generateSchema(data.getClass());
            return objectMapper.writeValueAsString(schema);
        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }
}
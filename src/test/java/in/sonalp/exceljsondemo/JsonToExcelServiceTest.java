package in.sonalp.exceljsondemo;

import in.sonalp.exceljsondemo.Service.JsonToExcelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class JsonToExcelServiceTest {
    JsonToExcelService service = new JsonToExcelService();
    @Test
    void testConvertJsonToExcel_Success() throws Exception {
        // Arrange
        Map<String, Object> jsonData = createSampleJsonData();
        // Act
        byte[] excelFileBytes = service.convertJsonToExcel(jsonData);
        // Assert
        assertNotNull(excelFileBytes);
        assertTrue(excelFileBytes.length > 0);
    }
    @Test
    void testConvertJsonToExcel_Error() {
        // Arrange
        Map<String, Object> jsonData = null;
        // Act and Assert
        assertThrows(RuntimeException.class, () -> service.convertJsonToExcel(jsonData));
    }
    private Map<String, Object> createSampleJsonData() {
        Map<String, Object> topLevel = new HashMap<>();
        Map<String, Object> midLevel = new HashMap<>();
        Map<String, Object> questions = new HashMap<>();
        Map<String, Object> questionData = new HashMap<>();
        questionData.put("key1", "value1");
        questionData.put("key2", Arrays.asList("value2a", "value2b"));
        questions.put("question1", questionData);
        midLevel.put("midKey", questions);
        topLevel.put("topKey", midLevel);
        return topLevel;
    }
}
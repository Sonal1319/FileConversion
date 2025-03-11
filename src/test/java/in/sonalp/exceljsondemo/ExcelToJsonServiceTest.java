package in.sonalp.exceljsondemo;

import in.sonalp.exceljsondemo.Service.ExcelToJsonService;
import in.sonalp.exceljsondemo.util.ExcelToJsonUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExcelToJsonServiceTest {
    @Mock
    private ExcelToJsonUtil excelToJsonUtil;
    @InjectMocks
    private ExcelToJsonService service;
    @Test
    void testConvertExcelToJson_Success() throws Exception {
        // Arrange
        File file = new File("test.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test.xlsx", fileInputStream);
        when(excelToJsonUtil.convertRowToJson(any())).thenReturn(new LinkedHashMap<>());
        when(excelToJsonUtil.convertJsonDataToString(any())).thenReturn("json data");
        // Act
        String result = service.convertExcelToJson(multipartFile);
        // Assert
        assertNotNull(result);
        assertEquals("json data", result);
    }

    @Test
    void testConvertExcelToJson_Error() throws Exception {
        // Arrange
        File file = new File("test.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test.xlsx", fileInputStream);
        when(excelToJsonUtil.convertRowToJson(any())).thenThrow(IOException.class);
        // Act and Assert
        assertThrows(IOException.class, () -> service.convertExcelToJson(multipartFile));
    }
}

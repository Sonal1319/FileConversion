package in.sonalp.exceljsondemo.util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Component
public class ExcelToJsonUtil {
    public Map<String, String> convertRowToJson(XSSFRow row) {
        Map<String, String> jsonData = new HashMap<>();
        for (int i = 0; i < row.getLastCellNum(); i++) {
            XSSFCell cell = row.getCell(i);
            String columnName = getColumnName(i); // method below
            String cellValue = getCellValue(cell);
            if (!cellValue.isEmpty()) {
                jsonData.put(columnName, cellValue);
            }
        }
        return jsonData;
    }
    public String convertJsonDataToString(List<Object> jsonData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonData);
        } catch (JsonProcessingException e) {
            return "Error formatting JSON: " + e.getMessage();
        }
    }
    private String getColumnName(int index) {
        String columnName = "";
        int charCode = 65 + index;
        columnName += (char) charCode;
        return columnName;
    }
    private String getCellValue(XSSFCell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue();
            case NUMERIC: return String.valueOf(cell.getNumericCellValue());
            default: return "";
        }
    }
}

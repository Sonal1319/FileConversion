package in.sonalp.exceljsondemo.Service;

import in.sonalp.exceljsondemo.util.ExcelToJsonUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelToJsonService {
    private final ExcelToJsonUtil excelToJsonUtil;

    public ExcelToJsonService(ExcelToJsonUtil excelToJsonUtil) {
        this.excelToJsonUtil = excelToJsonUtil;
    }

    public String convertExcelToJson(MultipartFile file) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            List<Object> jsonData = new ArrayList<>();
            Map<String, Object> workbookData = new LinkedHashMap<>();
            workbookData.put("Workbook Name", file.getOriginalFilename());
            jsonData.add(workbookData);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Map<String, Object> sheetData = new LinkedHashMap<>();
                sheetData.put("Sheet Name", sheet.getSheetName());
                List<Map<String, String>> sheetRows = new ArrayList<>();
                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                    XSSFRow row = sheet.getRow(j);
                    if (row != null && row.getLastCellNum() > 0) {
                        Map<String, String> rowMap = excelToJsonUtil.convertRowToJson(row);
                        if (!rowMap.isEmpty()) {
                            sheetRows.add(rowMap);
                        }
                    }
                }
                sheetData.put("Data", sheetRows);
                jsonData.add(sheetData);
            }
            workbook.close();
            return excelToJsonUtil.convertJsonDataToString(jsonData);
        } catch (IOException e) {
            return "Error converting Excel to JSON: " + e.getMessage();
        }
    }
}

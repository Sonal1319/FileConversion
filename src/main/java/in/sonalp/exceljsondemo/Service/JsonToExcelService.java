package in.sonalp.exceljsondemo.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class JsonToExcelService {

    public byte[] convertJsonToExcel(Map<String, Object> jsonData) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            createSheetFromJson(workbook, jsonData);
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error generating Excel file", e);
        }
    }
    private void getHeadersFromJson(Map<String, Object> json, List<String> headers, String prefix) {
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            String newPrefix = prefix.isEmpty() ? entry.getKey() : prefix + " " + entry.getKey();
            headers.add(newPrefix);
            if (entry.getValue() instanceof Map) {
                getHeadersFromJson((Map<String, Object>) entry.getValue(), headers, newPrefix);
            } else if (entry.getValue() instanceof Iterable) {
                headers.add(newPrefix + " Value");
            }
        }
    }
    private void createSheetFromJson(Workbook workbook, Map<String, Object> jsonData) {
        String sheetName = jsonData.keySet().iterator().next().toString() + " Data";
        Sheet sheet = workbook.createSheet(sheetName);
        int rowIndex = 0;

        List<String> headerList = new ArrayList<>();
        getHeadersFromJson(jsonData, headerList, "");
        Set<String> uniqueHeaders = new LinkedHashSet<>();
        for (String header : headerList) {
            uniqueHeaders.add(header.substring(header.lastIndexOf(" ") + 1));
        }
        headerList.clear();
        headerList.addAll(uniqueHeaders);
        headerList.add(0, headerList.remove(headerList.size()-1).split(" ")[0]);
        String[] headers = headerList.toArray(new String[0]);
        Row headerRow = sheet.createRow(rowIndex++);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        for (Map.Entry<String, Object> topEntry : jsonData.entrySet()) {
            Map<String, Object> midDataInner = (Map<String, Object>) topEntry.getValue();
            for (Map.Entry<String, Object> midEntry : midDataInner.entrySet()) {
                Map<String, Object> questions = (Map<String, Object>) midEntry.getValue();
                for (Map.Entry<String, Object> questionEntry : questions.entrySet()) {
                    Map<String, Object> questionDataInner = (Map<String, Object>) questionEntry.getValue();
                    Row row = sheet.createRow(rowIndex++);
                    int cellIndex = 0;
                    Cell cell = row.createCell(cellIndex++);
                    cell.setCellValue(topEntry.getKey());
                    for (Map.Entry<String, Object> entry : questionDataInner.entrySet()) {
                        Cell dataCell = row.createCell(cellIndex++);
                        if (entry.getValue() instanceof Iterable) {
                            dataCell.setCellValue(String.join(",", (Iterable<String>) entry.getValue()));
                        } else {
                            dataCell.setCellValue(entry.getValue().toString());
                        }
                    }
                }
            }
        }
    }
}
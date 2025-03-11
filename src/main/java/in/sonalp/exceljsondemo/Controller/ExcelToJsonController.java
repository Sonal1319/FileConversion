package in.sonalp.exceljsondemo.Controller;

import in.sonalp.exceljsondemo.Service.ExcelToJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ExcelToJsonController {
    @Autowired
    private ExcelToJsonService excelToJsonService;
    @PostMapping("/excel-to-json")
    public String convertExcelToJson(@RequestParam("file") MultipartFile file) {
        return excelToJsonService.convertExcelToJson(file);
    }
}

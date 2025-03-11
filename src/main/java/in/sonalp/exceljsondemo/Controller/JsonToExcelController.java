package in.sonalp.exceljsondemo.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.sonalp.exceljsondemo.Service.JsonToExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;
@RestController
public class JsonToExcelController {
    @Autowired
    private JsonToExcelService jsonToExcelService;
    @PostMapping(value = "/json-to-excel", consumes = "multipart/form-data")
    public ResponseEntity<byte[]> convertJsonToExcel(@RequestParam("file") MultipartFile file) {
        String fileName = "output.xlsx";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonData = objectMapper.readValue(file.getInputStream(), Map.class);
            byte[] excelBytes = jsonToExcelService.convertJsonToExcel(jsonData);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            ContentDisposition disposition = ContentDisposition.attachment().filename(fileName).build();
            headers.setContentDisposition(disposition);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new byte[0]);
        }
    }
}
package com.element_hierarchy.element_hierarchy.Service.ExcelHanlder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.element_hierarchy.element_hierarchy.Model.ADM_REF_BO;
import com.element_hierarchy.element_hierarchy.Service.AdmRefBo.IAdmRefBoService;

@Service
public class ExcelHanlderService implements IExcelHanlderService {
  @Override
  public List<ADM_REF_BO> readFile(MultipartFile file, String sheetName) {
    List<ADM_REF_BO> list = new ArrayList<>();
    try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
      Sheet sheet = workbook.getSheet(sheetName);
      if (sheet == null) {
        throw new RuntimeException("Sheet không tồn tại");
      }
      int uiCtrlCodeColumIndex = 1;
      int boBpmColumIndex = 3;
      for (Row row : sheet) {
        String uiCtrlCode = "";
        String boBpm = "";
        if (row.getCell(uiCtrlCodeColumIndex) != null) {
          uiCtrlCode = row.getCell(uiCtrlCodeColumIndex).getStringCellValue();
        }
        if (row.getCell(boBpmColumIndex) != null) {
          boBpm = row.getCell(boBpmColumIndex).getStringCellValue();
        }
        if (uiCtrlCode.isEmpty()) {
          continue;
        }

        ADM_REF_BO adm_REF_BO = new ADM_REF_BO();
        adm_REF_BO.setBoFieldName(boBpm);
        adm_REF_BO.setUiCtrlCode(uiCtrlCode);
        adm_REF_BO.setCreatedUser("Dong");
        adm_REF_BO.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        list.add(adm_REF_BO);
      }
      list.remove(0);
      list.remove(0);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
  }

  @Override
  public void writeWord() {
    File file = new File("C:\\Users\\DONG\\OneDrive\\Desktop\\TTS\\Test.docx");
     try {
            FileInputStream templateInputStream = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(templateInputStream);
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("${name}", "John Doe");
            // placeholders.put("${date}", "August 15, 2023");
            // placeholders.put("${content}", "This is the content of the document...");
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0); 
                    for (String placeholder : placeholders.keySet()) {
                        if (text != null && text.contains(placeholder)) {
                            text = text.replace(placeholder, placeholders.get(placeholder));
                            run.setText(text, 0);
                        }
                    }
                }
            }
            FileOutputStream outputStream = new FileOutputStream(new File("output.docx"));
            document.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
  }
}

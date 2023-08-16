package com.element_hierarchy.element_hierarchy.Service.ExcelHanlder;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.element_hierarchy.element_hierarchy.Model.ADM_REF_BO;
public interface IExcelHanlderService {
      public List<ADM_REF_BO> readFile(MultipartFile file,String sheetName);

      public void writeWord();
}

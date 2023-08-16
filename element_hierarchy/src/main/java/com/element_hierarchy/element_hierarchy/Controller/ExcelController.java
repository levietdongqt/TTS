package com.element_hierarchy.element_hierarchy.Controller;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.element_hierarchy.element_hierarchy.Model.ADM_REF_BO;
import com.element_hierarchy.element_hierarchy.Model.ADM_REF_UI;
import com.element_hierarchy.element_hierarchy.Model.REF_MAP_DETAIL;
import com.element_hierarchy.element_hierarchy.Service.IAdmRefUiService;
import com.element_hierarchy.element_hierarchy.Service.AdmRefBo.IAdmRefBoService;
import com.element_hierarchy.element_hierarchy.Service.ExcelHanlder.IExcelHanlderService;
import com.element_hierarchy.element_hierarchy.Service.RefMapDetail.IRefMapDetailService;

@Controller
@RequestMapping(path = "/excelHandler")
public class ExcelController {
  @Autowired
  IExcelHanlderService excelHanlderService;
  @Autowired
  IAdmRefBoService admRefBoService;
  @Autowired
  IAdmRefUiService admRefUiService;
  @Autowired
  IRefMapDetailService refMapDetailService;

  @GetMapping("")
  public String showPage(@ModelAttribute("mess") String mess,
      Model model) {

    if (mess.isEmpty()) {
      model.addAttribute("show", false);
    } else {
      model.addAttribute("show", true);
      model.addAttribute("mess", mess);
    }
    return "excel";
  }

  @PostMapping("/saveBo")
  public String convertExcel(@NotNull @RequestParam("file") MultipartFile file,
                             @RequestParam("sheetName") String sheetName,
                             RedirectAttributes redirectAttributes) {
    if (file.isEmpty()) {
      redirectAttributes.addFlashAttribute("mess", "Lưu thất bại, File không được trống");
      return "redirect:/excelHandler";
    }
     StringBuilder message = new StringBuilder();
    try {
      List<ADM_REF_BO> list = excelHanlderService.readFile(file, sheetName);
      admRefBoService.buildBoFieldDir(list);
      TreeSet<ADM_REF_BO> tree = admRefBoService.sortByUICtrlCode(admRefBoService.checkUnique(list));
      admRefBoService.saveToRefBoService(tree);
      message.append("Lưu ADM_REF_BO thành công. ");

      String boNameParent = list.get(0).getBoFieldName();
      List<ADM_REF_BO> insertedList = admRefBoService.getListByBoFieldDir(boNameParent);
      List<ADM_REF_BO> saveList = admRefBoService.mappingUiCtrlCodeToRefBo(list, insertedList);
      List<REF_MAP_DETAIL> refMapDetailList = refMapDetailService.buildRefMapDetailsV2(saveList);
      refMapDetailService.saveToRefMapDetail(refMapDetailList);
      message.append("\n Lưu REF_MAP_DETAIL thành công");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("mess", message.append("\n").append(e.getMessage()).toString());
      return "redirect:/excelHandler";
    }

    redirectAttributes.addFlashAttribute("mess", message);
    return "redirect:/excelHandler";
  }

  @PostMapping("/saveRefDetail")
  public String saveRefDetail(@RequestParam("file") MultipartFile file,
      @RequestParam("sheetName") String sheetName,
      RedirectAttributes redirectAttributes) {

    if (file.isEmpty()) {
      redirectAttributes.addFlashAttribute("mess", "Lưu thất bại, File không được trống");
      return "redirect:/excelHandler";
    }
    try {
      List<ADM_REF_BO> list = excelHanlderService.readFile(file, sheetName);
      admRefBoService.buildBoFieldDir(list);
      String boNameParent = list.get(0).getBoFieldName();
      List<ADM_REF_BO> insertedList = admRefBoService.getListByBoFieldDir(boNameParent);
      if (insertedList.isEmpty() || boNameParent.isEmpty()) {
        redirectAttributes.addFlashAttribute("mess", "Lưu thất bại, Sai tên Bo");
        return "redirect:/excelHandler";
      }
      // TreeSet<ADM_REF_UI> admRefUiTree =
      // admRefUiService.getByUiCtrlCodeStartingWith(sheetName);
      // if (admRefUiTree.isEmpty()) {
      // redirectAttributes.addFlashAttribute("mess", "Lưu thất bại, Sheet không tồn
      // tại");
      // return "redirect:/excelHandler";
      // }
      List<ADM_REF_BO> saveList = admRefBoService.mappingUiCtrlCodeToRefBo(list, insertedList);
      List<REF_MAP_DETAIL> refMapDetailList = refMapDetailService.buildRefMapDetailsV2(saveList);
      refMapDetailService.saveToRefMapDetail(refMapDetailList);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("mess", e.getMessage());
      return "redirect:/excelHandler";
    }
    redirectAttributes.addFlashAttribute("mess", "Lưu vào REF_MAP_DETAIL thành công");
    return "redirect:/excelHandler";
  }
   @GetMapping("/writeWord")
  public String writeWord(){
    excelHanlderService.writeWord();
    return "Ghi thanh cong";
  }
}

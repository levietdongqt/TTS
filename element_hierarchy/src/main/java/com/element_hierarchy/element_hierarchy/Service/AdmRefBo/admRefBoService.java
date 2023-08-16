package com.element_hierarchy.element_hierarchy.Service.AdmRefBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.element_hierarchy.element_hierarchy.Model.ADM_REF_BO;
import com.element_hierarchy.element_hierarchy.Model.ADM_REF_UI;
import com.element_hierarchy.element_hierarchy.Model.UiCtrlCodeOderComparator;
import com.element_hierarchy.element_hierarchy.Repository.IAdmRefBoRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class admRefBoService implements IAdmRefBoService {

  @Autowired
  IAdmRefBoRepo admRefBoRepo;

  @Override
  public void buildBoFieldDir(List<ADM_REF_BO> root) {
    Map<String, ADM_REF_BO> map = new HashMap<>();
    root.stream().forEach(t -> {
      map.put(t.getUiCtrlCode(), t);
    });
    try {
      for (ADM_REF_BO item : root) {
        String uiCtrlCodeItem = getUiCtrlCodeForWard(item.getUiCtrlCode());
        ADM_REF_BO parent = map.get(uiCtrlCodeItem);
        while (noParent(uiCtrlCodeItem, parent)) {
          uiCtrlCodeItem = getUiCtrlCodeForWard(uiCtrlCodeItem);
          parent = map.get(uiCtrlCodeItem);
        }
        if (parent != null) {
          item.setBoFieldDir(parent.getBoFieldDir() + "." + item.getBoFieldName());
          item.setLevel(parent.getLevel() + 1);
        } else if (item.getBoFieldDir() == null) {
          item.setBoFieldDir(item.getBoFieldName());
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public List<ADM_REF_BO> buildAdmRefBosUpdate(TreeSet<ADM_REF_UI> root, String[] values, boolean[] isUpdates) {
    List<ADM_REF_BO> updateList = new ArrayList<>();
    List<ADM_REF_BO> rootList = new ArrayList<>();
    int index = 0;
    int parentLevel = Integer.MAX_VALUE;
    boolean isChange = false;
    String currentBoFieldName = "";
    for (ADM_REF_UI item : root) {
      if (isUpdates[index]) {
        currentBoFieldName = values[index];
      } else {
        currentBoFieldName = item.getValue();
      }
      ADM_REF_BO adm_REF_BO = getAdm_REF_BO(item, currentBoFieldName);
      rootList.add(adm_REF_BO);
      // if (!currentBoFieldName.equalsIgnoreCase(item.getValue())) {
      // parentLevel = item.getLevel();
      // String newBoFieldDir = null;
      // String oldBoFieldDir = item.getReference();
      // if (oldBoFieldDir.lastIndexOf(".") > 0) {
      // newBoFieldDir = oldBoFieldDir.substring(0, oldBoFieldDir.lastIndexOf(".")) +
      // "." + currentBoFieldName;
      // }
      // isChange = true;
      // ADM_REF_BO adm_REF_BO = getAdm_REF_BO(item, currentBoFieldName);
      // adm_REF_BO.setBoFieldDir(newBoFieldDir); // Phải lấy lại tham chiếu từ thằng
      // ông nội tới ptử hiện tại.
      // updateList.add(adm_REF_BO);
      // index++;
      // continue;
      // }
      // if (isChange && item.getLevel() > parentLevel) {
      // ADM_REF_BO adm_REF_BO = getAdm_REF_BO(item, currentBoFieldName);
      // updateList.add(adm_REF_BO);
      // } else {
      // isChange = false;
      // }
      index++;
    }
    index = 0;
    buildBoFieldDir(rootList);
    for (ADM_REF_BO adm_REF_BO : rootList) {
      if (isUpdates[index]) {
        parentLevel = adm_REF_BO.getLevel();
        updateList.add(adm_REF_BO);
        isChange = true;
        index++;
        continue;
      }
      if (isChange && parentLevel < adm_REF_BO.getLevel()) {
        updateList.add(adm_REF_BO);
      } else {
        isChange = false;
      }

      index++;
    }
    return updateList;
  }

  private ADM_REF_BO getAdm_REF_BO(ADM_REF_UI item, String currentBoFieldName) {
    ADM_REF_BO adm_REF_BO = new ADM_REF_BO();
    adm_REF_BO.setUiCtrlCode(item.getUiCtrlCode());
    adm_REF_BO.setBoFieldCode(item.getBoFieldCode());
    adm_REF_BO.setBoFieldName(currentBoFieldName);
    return adm_REF_BO;
  }

  @Override
  public List<ADM_REF_BO> mappingUiCtrlCodeToRefBo(List<ADM_REF_BO> hasUiCtrlCodeList,
      List<ADM_REF_BO> noUiCtrlCodeList) {
    // hasUiCtrlCodeList lấy từ file excel lên
    // noUiCtrlCodeList được lấy từ table ADM_REF_BO sau khi insert
    Map<String, ADM_REF_BO> map = new HashMap<>();
    noUiCtrlCodeList.stream().forEach(t -> {
      map.put(t.getBoFieldDir(), t);
    });
    List<ADM_REF_BO> saveList = new ArrayList<>();
    for (ADM_REF_BO adm_REF_BO : hasUiCtrlCodeList) {
      ADM_REF_BO noUiCtrlCode = map.get(adm_REF_BO.getBoFieldDir());
      if (noUiCtrlCode != null) {
        adm_REF_BO.setBoFieldCode(noUiCtrlCode.getBoFieldCode());
        saveList.add(adm_REF_BO);
      }
    }
    return saveList;
  }

  @Override
  public TreeSet<ADM_REF_BO> sortByUICtrlCode(Set<ADM_REF_BO> list) {
    TreeSet<ADM_REF_BO> tree = new TreeSet<>(new UiCtrlCodeOderComparator());
    tree.addAll(list);
    return tree;
  }

  @Override
  public Set<ADM_REF_BO> checkUnique(List<ADM_REF_BO> list) {
    Set<ADM_REF_BO> uniqueList = new HashSet<>();
    uniqueList.addAll(removeEmpty(list));

    return uniqueList;
  }

  @Override
  public List<ADM_REF_BO> removeEmpty(List<ADM_REF_BO> list) {
    List<ADM_REF_BO> uniqueList = new ArrayList<>();
    list.stream().forEach(t -> {
      if (!t.getBoFieldName().trim().isEmpty()) {
        uniqueList.add(t);
      }
    });
    return uniqueList;
  }

  @Override
  public void saveToRefBoService(Set<ADM_REF_BO> tree) {
    String JSON = "";
    try {
      JSON = new ObjectMapper().writeValueAsString(tree);
      System.out.println(JSON);
      admRefBoRepo.saveToAdmRefBo(JSON);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("Lưu ADM_REF_BO_OK");
  }

  @Override
  public void updateToAdmRefBo(List<ADM_REF_BO> admRefBos) {
    String JSON = "";
    try {
      JSON = new ObjectMapper().writeValueAsString(admRefBos);
      System.out.println(JSON);
      admRefBoRepo.updateToAdmRefBo(JSON);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("Update ADM_REF_BO_OK");
  }

  private String getUiCtrlCodeForWard(String uiCtrlCode) {
    return uiCtrlCode.substring(0, uiCtrlCode.lastIndexOf("."));
  }

  private boolean noParent(String uiCtrlCodeItem, ADM_REF_BO parent) {
    return parent == null && uiCtrlCodeItem.lastIndexOf(".") != -1;
  }

  @Override
  public List<ADM_REF_BO> getListByBoFieldDir(String boFieldDir) {
    return admRefBoRepo.getListByBoFieldDir(boFieldDir);
  }

}

package com.element_hierarchy.element_hierarchy.Service.RefMapDetail;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.element_hierarchy.element_hierarchy.Model.ADM_REF_BO;
import com.element_hierarchy.element_hierarchy.Model.ADM_REF_UI;
import com.element_hierarchy.element_hierarchy.Model.REF_MAP_DETAIL;
import com.element_hierarchy.element_hierarchy.Repository.IRefMapDetailsRepo;
import com.element_hierarchy.element_hierarchy.Repository.IRefMapDetailsRepoTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RefMapDetailService implements IRefMapDetailService {
  @Autowired
  IRefMapDetailsRepo refMapDetailsRepo;
  @Autowired
  IRefMapDetailsRepoTest refMapDetailsRepoTest;


  @Override
  public List<REF_MAP_DETAIL> buildRefMapDetailsV2(List<ADM_REF_BO> saveList) {
    List<REF_MAP_DETAIL> ref_MAP_DETAILs = new ArrayList<>();
    for (ADM_REF_BO adm_REF_BO : saveList) {
      REF_MAP_DETAIL item = new REF_MAP_DETAIL();
      item.setMapCode("MapByDong");
      item.setCreatedUser("dong");
      item.setUiCtrlCode(adm_REF_BO.getUiCtrlCode());
      item.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
      item.setStatus("1");
      item.setBoFieldCode(adm_REF_BO.getBoFieldCode());
      ref_MAP_DETAILs.add(item);
    }
    return ref_MAP_DETAILs;
  }

  @Override
  @Transactional
  public int saveToRefMapDetail(List<REF_MAP_DETAIL> ref_MAP_DETAILs) {
    System.out.println("\n Vô lưu");
    String JSON = "";
    try {
      JSON = new ObjectMapper().writeValueAsString(ref_MAP_DETAILs);
      System.out.println(JSON);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    refMapDetailsRepo.insertRefMapDetail(JSON);
    System.out.println("\n lưu Ok");
    return 1;
  }

  @Override
  public int updateToRefMapDetail(List<REF_MAP_DETAIL> ref_MAP_DETAILs) {
    List<REF_MAP_DETAIL> list = new ArrayList<>();
    ref_MAP_DETAILs.stream().forEach(t -> {
      if (!(t.getBoFieldCode() == null || t.getBoFieldCode().equals("null"))) {
        list.add(t);
      }
    });
    String JSON = "";
    try {
      JSON = new ObjectMapper().writeValueAsString(ref_MAP_DETAILs);
      System.out.println(JSON);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    refMapDetailsRepo.UpdateRefMapDetail(JSON);
    return 1;
  }

}

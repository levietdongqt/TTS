package com.element_hierarchy.element_hierarchy.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.element_hierarchy.element_hierarchy.Model.ADM_REF_UI;
import com.element_hierarchy.element_hierarchy.Model.REF_MAP_DETAIL;
import com.element_hierarchy.element_hierarchy.Model.TRECORD;
import com.element_hierarchy.element_hierarchy.Repository.IAdmRefUiRepo;
import com.element_hierarchy.element_hierarchy.Repository.IRefMapDetailsRepoTest;

@Service
@Transactional
public class AdmRefService implements IAdmRefUiService {
  @Autowired
  private IAdmRefUiRepo admRefUiRepo;
  @Autowired
  IRefMapDetailsRepoTest refMapDetailsRepoTest;

  @Override
  public Set<ADM_REF_UI> getAll() {
    Set<ADM_REF_UI> tree = new TreeSet<>(admRefUiRepo.getAll());
    return tree;
  }

  @Override
  public ADM_REF_UI getByUiCtrlCode(String uiCtrlCode) {
    return admRefUiRepo.getByUiCtrlCode(uiCtrlCode);
  }

  @Override
  public TreeSet<ADM_REF_UI> getByUiCtrlCodeStartingWith(String uiCtrlCode) {
    return new TreeSet<>(admRefUiRepo.getByUiCtrlCodeStartingWith(uiCtrlCode));
  }

  @Override
  public TreeSet<ADM_REF_UI> mappingRefMapToAdmRefUI(TreeSet<ADM_REF_UI> adm_REF_UIs, String uiCtrlCode) {
    List<REF_MAP_DETAIL> ref_MAP_DETAILs = refMapDetailsRepoTest.getListByUiCtrlCode(uiCtrlCode);
    if (!ref_MAP_DETAILs.isEmpty()) {
      ref_MAP_DETAILs.forEach(t -> {
        for (ADM_REF_UI adm_REF_UI : adm_REF_UIs) {
          if (adm_REF_UI.getUiCtrlCode().equals(t.getUiCtrlCode())) {
            if (t.getDescription() == null) {
              adm_REF_UI.setValue("");
            } else {
              adm_REF_UI.setValue(t.getDescription());
            }
            break;
          }
        }
      });
    }
    return adm_REF_UIs;
  }

  @Override
  public int countLevel(Set<ADM_REF_UI> root) {
    if (root == null) {
      return 0;
    }
    if (root.size() < 2) {
      return 1;
    }
    int maxLevel = root.stream().max((o1, o2) -> o1.getLevel() - o2.getLevel()).get().getLevel();
    return maxLevel;
  }

  private String getUiCtrlCodeForWard(String uiCtrlCode) {
    return uiCtrlCode.substring(0, uiCtrlCode.lastIndexOf("."));
  }

  private boolean noParent(String uiCtrlCodeItem, ADM_REF_UI parent) {
    return parent == null && uiCtrlCodeItem.lastIndexOf(".") != -1;
  }

  @Override
  public boolean buildRelationship(Set<ADM_REF_UI> root) {
    if (root.isEmpty()) {
      return false;
    }
    Map<String, ADM_REF_UI> map = new HashMap<>();
    root.stream().forEach(t -> {
      map.put(t.getUiCtrlCode(), t);
    });
    for (ADM_REF_UI item : root) {
      if (item.getUiCtrlCode() == null) {
        continue;
      }
      String uiCtrlCodeItem = getUiCtrlCodeForWard(item.getUiCtrlCode());
      ADM_REF_UI parent = map.get(uiCtrlCodeItem);
      while (noParent(uiCtrlCodeItem, parent)) {
        uiCtrlCodeItem = getUiCtrlCodeForWard(uiCtrlCodeItem);
        parent = map.get(uiCtrlCodeItem);
      }
      if (parent != null) {
        item.setLevel(parent.getLevel() + 1);
      }
      // if (parent != null) {
      // if (parent.getLevel() == 1) {
      // item.setReference(parent.getValue() + "." + item.getValue());
      // } else {
      // item.setReference(parent.getReference() + "." + item.getValue());
      // }
      // item.setLevel(parent.getLevel() + 1);
      // } else {
      // item.setReference(item.getValue());
      // }

    }
    return true;
  }

  @Override
  public void test2() {
    List<TRECORD> list = new ArrayList<>();
    TRECORD item1 = new TRECORD();
    item1.setID(100000);
    item1.setUI_CTRL_CODE("AAAA");
    item1.setBo_field_code("AAAA");
    item1.setMap_code("AAAA");
    item1.setService_field_code("AAAA");
    item1.setCreated_user("DONG");
    item1.setStatus("0");
    item1.setDescription("AAAA");
    item1.setProc_field_code("AAAA");
    item1.setRule_field_code("AAAA");
    item1.setTmpl_field_code("AAAA");
    item1.setCreated_date(Timestamp.valueOf(LocalDateTime.now()));
    TRECORD item2 = new TRECORD();
    item2.setID(102000);
    item2.setUI_CTRL_CODE("BBBB");
    item2.setBo_field_code("BBBB");
    item2.setMap_code("BBBB");
    item2.setService_field_code("BBBB");
    item2.setCreated_user("DONG");
    item2.setStatus("1");
    item2.setDescription("BBBB");
    item2.setProc_field_code("BBBB");
    item2.setRule_field_code("BBBB");
    item2.setTmpl_field_code("BBBB");
    item2.setCreated_date(Timestamp.valueOf(LocalDateTime.now()));
    list.add(item1);
    list.add(item2);
    TRECORD[] a = list.toArray(new TRECORD[0]);
    Iterator<TRECORD> inIterator = list.iterator();
    // refMapDetailsRepoTest.insertRefMapDetail2(list);
    List<String> strings = new ArrayList<>();
    strings.add("TEST1");
    strings.add("TEST2");
    // refMapDetailsRepoTest.updateToRefMapDetail(strings);
    System.out.println("sfsd");
  }

  @Override
  public boolean buildTree(ADM_REF_UI root, Set<ADM_REF_UI> children) {
    if (children.isEmpty()) {
      return false;
    }
    Map<String, ADM_REF_UI> map = new HashMap<>();
    if (root != null) {
      map.put(root.getUiCtrlCode(), root);
    }
    children.stream().forEach(t -> {
      map.put(t.getUiCtrlCode(), t);
    });
    for (ADM_REF_UI item : children) {
      if (item.getUiCtrlCode() == null) {
        continue;
      }
      String uiCtrlCodeItem = getUiCtrlCodeForWard(item.getUiCtrlCode());
      ADM_REF_UI parent = map.get(uiCtrlCodeItem);
      while (noParent(uiCtrlCodeItem, parent)) {
        uiCtrlCodeItem = getUiCtrlCodeForWard(uiCtrlCodeItem);
        parent = map.get(uiCtrlCodeItem);
      }
      if (parent != null) {
        // parent.getChildren().add(item);
        item.setReference(parent.getUiCtrlCode() + "." + item.getUiCtrlCode());
        item.setLevel(parent.getLevel() + 1);
      }
    }
    return true;
  }

  @Override
  public TreeSet<ADM_REF_UI> getListByUiCtrlCodeHasBoFieldName(String uiCtrlCode) {
    TreeSet<ADM_REF_UI> list = new TreeSet<>();
    List<Object[]> result = admRefUiRepo.getListByUiCtrlCodeHasBoFieldName(uiCtrlCode);

    int index = 0;
    for (Object[] objects : result) {
      ADM_REF_UI adm_REF_UI = new ADM_REF_UI();
      adm_REF_UI.setUiCtrlCode(String.valueOf(objects[0]));
      adm_REF_UI.setUiDesc(String.valueOf(objects[1]));
      adm_REF_UI.setUiCtrlName(String.valueOf(objects[2]));
      adm_REF_UI.setCreatedUser("Dong");
      adm_REF_UI.setBoFieldCode(String.valueOf(objects[4]));
      adm_REF_UI.setReference(String.valueOf(objects[5]));
      if (String.valueOf(objects[3]) == null || String.valueOf(objects[3]).equals("null")) {
        adm_REF_UI.setValue("");
      } else {
        adm_REF_UI.setValue(String.valueOf(objects[3]));
      }
      list.add(adm_REF_UI);
    }
    return list;
  }

  @Override
  public List<ADM_REF_UI> getParentList() {
    return admRefUiRepo.getParenList();
  }
}

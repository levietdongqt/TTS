package com.element_hierarchy.element_hierarchy.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.element_hierarchy.element_hierarchy.Model.ADM_REF_UI;
import com.element_hierarchy.element_hierarchy.Model.REF_MAP_DETAIL;
import com.element_hierarchy.element_hierarchy.Model.ResponseObject;
import com.element_hierarchy.element_hierarchy.Service.IAdmRefUiService;

@RestController
@RequestMapping(path = "/api/")
public class AdmController {
  @Autowired
  IAdmRefUiService admRefUiService;

  @GetMapping("buildTree/buildAll")
  public ResponseEntity<?> getAll() {
    Set<ADM_REF_UI> tree = admRefUiService.getAll();
    admRefUiService.buildTree(null, tree);
    Set<ADM_REF_UI> rootList = new TreeSet<>();
    tree.stream().filter(t -> t.getReference().isEmpty())
        .forEach(t -> rootList.add(t));
    return ResponseEntity.status(HttpStatus.OK).body(
        new ResponseObject(HttpStatus.OK.toString(), "Request successfully!!", rootList));
  }

  @GetMapping("buildRelationship/{uiCtrlCode}")
  public ResponseEntity<?> getByuiCtrlCode(@PathVariable String uiCtrlCode) {
    // TreeSet<ADM_REF_UI> root =
    // admRefUiService.getByUiCtrlCodeStartingWith(uiCtrlCode);
    // admRefUiService.mappingRefMapToAdmRefUI(root, uiCtrlCode);

    TreeSet<ADM_REF_UI> root = admRefUiService.getListByUiCtrlCodeHasBoFieldName(uiCtrlCode);
    if (root.isEmpty()) {
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(
          new ResponseObject(HttpStatus.NOT_FOUND.toString(), "Don't find item with: " + uiCtrlCode, new TreeSet<>()));
    }
    admRefUiService.buildRelationship(root);
    return ResponseEntity.status(HttpStatus.OK).body(
        new ResponseObject(HttpStatus.OK.toString(), "Request successfully!", root));
  }
  @GetMapping("getParentList")
  public ResponseEntity<?> getParentList() {
    List<ADM_REF_UI> parentList = admRefUiService.getParentList();
    return ResponseEntity.status(HttpStatus.OK).body( parentList);
  }

  @GetMapping("test")
  public REF_MAP_DETAIL test() {
    ArrayList<REF_MAP_DETAIL> list = new ArrayList<>();
    REF_MAP_DETAIL item1 = new REF_MAP_DETAIL();
    item1.setUiCtrlCode("AAAA");
    item1.setBoFieldCode("AAAA");
    item1.setMapCode("AAAA");
    item1.setServiceFieldCode("AAAA");
    list.add(0, item1);
    Object[] a = list.toArray();

    admRefUiService.test2();
    return item1;
  }
  // @GetMapping("buildTree/{uiCtrlCode}")
  // public ResponseEntity<?> buildTree(@PathVariable String uiCtrlCode) {
  // ADM_REF_UI root = admRefUiService.getByUiCtrlCode(uiCtrlCode);
  // TreeSet<ADM_REF_UI> children =
  // admRefUiService.getByUiCtrlCodeStartingWith(uiCtrlCode);
  // if (root == null) {
  // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
  // new ResponseObject(HttpStatus.NOT_FOUND.toString(), "Don't find item with ID:
  // " + uiCtrlCode, null));
  // }
  // admRefUiService.buildTree(root, children);
  // return ResponseEntity.status(HttpStatus.OK).body(
  // root);
  // }
}

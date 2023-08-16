package com.element_hierarchy.element_hierarchy;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import com.element_hierarchy.element_hierarchy.Model.ADM_REF_UI;
import com.element_hierarchy.element_hierarchy.Service.AdmRefService;

public class test {

  public Set<ADM_REF_UI> testt() {
    List<ADM_REF_UI> a = new LinkedList<>();
    ADM_REF_UI num1 = new ADM_REF_UI();
    num1.setUiCtrlCode("R.H");
    ADM_REF_UI num2 = new ADM_REF_UI();
    num2.setUiCtrlCode("R.H.1.2");
    ADM_REF_UI num3 = new ADM_REF_UI();
    num3.setUiCtrlCode("R.H.2");
    ADM_REF_UI num4 = new ADM_REF_UI();
    num4.setUiCtrlCode("R.H.1.3");
    ADM_REF_UI num5 = new ADM_REF_UI();
    num5.setUiCtrlCode("R.H.1.2.3");
    ADM_REF_UI num6 = new ADM_REF_UI();
    num6.setUiCtrlCode("R.H.1.4");
    ADM_REF_UI num7 = new ADM_REF_UI();
    num7.setUiCtrlCode("R.H.2.1.2");
    // a.add(num1);
    a.add(num2);
    a.add(num3);
    a.add(num4);
    a.add(num5);
    a.add(num6);
    a.add(num7);
    Set<ADM_REF_UI> tree = new TreeSet<>(a);
    new AdmRefService().buildTree(num1, tree);
    // int level = new AdmRefService().countLevel(num1);
    // System.out.println(level);
    return tree;
  }

  public static void main(String[] args) {
    String boFieldDir = "DEE.A.F.";
    int indexDot = boFieldDir.lastIndexOf(".");
    int length = boFieldDir.length();
    int firstDot = boFieldDir.indexOf(".");
    StringBuilder sub = new StringBuilder(boFieldDir);
    while (indexDot > 0) {
      if (indexDot == firstDot) {
        break;
      }
      sub.setLength(indexDot);
      indexDot = sub.lastIndexOf(".");
    }
    System.out.println(sub);
  }
}

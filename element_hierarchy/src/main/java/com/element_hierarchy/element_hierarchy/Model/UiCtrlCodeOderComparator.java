package com.element_hierarchy.element_hierarchy.Model;

import java.util.Comparator;

public class UiCtrlCodeOderComparator implements Comparator<ADM_REF_BO> {

  @Override
  public int compare(ADM_REF_BO o1, ADM_REF_BO o2) {
    String uiCtrlCode = o1.getUiCtrlCode();
    String uiCtrlCodeOther = o2.getUiCtrlCode();
    if (uiCtrlCode == null) {
      return 1;
    }
    if (uiCtrlCodeOther == null) {
      return -1;
    }
    String[] parts = uiCtrlCode.split("\\.");
    String[] partsOther = uiCtrlCodeOther.split("\\.");
    int index = findFirstDifferentCharIndex(parts, partsOther);
    if (hasRelationship(parts, partsOther, index)) { // isChildAndParent
      return parts.length - partsOther.length;
    }
    try {
      return compareWithNumber(parts, partsOther, index);
    } catch (NumberFormatException e) {
      return parts[index].compareTo(partsOther[index]);
    }
  }

  private int findFirstDifferentCharIndex(String[] parts, String[] partsOther) {
    int index = 0;
    // while (index < parts.length && index < partsOther.length) {
    // if (parts[index].equalsIgnoreCase(partsOther[index])) {
    // index++;
    // continue;
    // }
    // break;
    // }
    int minLength = Math.min(parts.length, partsOther.length);
    while (index < minLength && parts[index].equalsIgnoreCase(partsOther[index])) {
      index++;
    }
    return index;
  }

  private int compareWithNumber(String[] parts, String[] partsOther, int index) throws NumberFormatException {
    int numThis = Integer.parseInt(parts[index]);
    int numOther = Integer.parseInt(partsOther[index]);
    return numThis - numOther;
  }

  private boolean hasRelationship(String[] parts, String[] partsOther, int index) {
    return index == parts.length || index == partsOther.length;
  }

}

package com.element_hierarchy.element_hierarchy.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ADM_REF_UI")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ADM_REF_UI implements Comparable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int ID;
  @Column(name = "UI_CTRL_CODE")
  private String uiCtrlCode;
  @Column(name = "PROCESS_CODE")
  private String processCode;
  @Column(name = "UI_CODE")
  @JsonIgnore
  private String uiCode;
  @Column(name = "UI_DESC")
  private String uiDesc;
  @Column(name = "UI_CTRL_Name")
  private String uiCtrlName;
  @Column(name = "UI_CTRL_TYPE")
  @JsonIgnore
  private String uiCtrlType;
  @Column(name = "MASTERDATA_GROUP_CODE")
  @JsonIgnore
  private String masterDataGroupCode;
  @Column(name = "DATA_TYPE")
  @JsonIgnore
  private String dataType;
  @Column(name = "SPECIFICATION")
  @JsonIgnore
  private String specification;
  @Column(name = "STATUS")
  @JsonIgnore
  private String status;
  @Column(name = "CREATED_DATE")
  private LocalDateTime createdDate;
  @JsonIgnore
  @Column(name = "CREATED_USER")
  private String createdUser;
  @Transient
  private String value = "";
  @Transient
  private int level = 1;
  @Transient
  private String reference = "";
  @Transient
  private String boFieldCode = "";
  // @Override
  // public int hashCode() {
  // String uiCtrlCode = this.getUiCtrlCode();
  // int indexOfSecondDot = uiCtrlCode.indexOf(".", uiCtrlCode.indexOf(".") + 1);
  // return uiCtrlCode.substring(0, indexOfSecondDot).hashCode();
  // }
  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    ADM_REF_UI other = (ADM_REF_UI) obj;
    return this.getUiCtrlCode().equalsIgnoreCase(other.getUiCtrlCode());
  }

  @Override
  public int compareTo(Object o) {
    ADM_REF_UI other = (ADM_REF_UI) o;
    String uiCtrlCode = this.getUiCtrlCode();
    String uiCtrlCodeOther = other.getUiCtrlCode();
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
    // if (parts.length == partsOther.length) {
    // try {
    // int numThis = Integer.parseInt(parts[index]);
    // int numOther = Integer.parseInt(partsOther[index]);
    // return numThis - numOther;

    // } catch (NumberFormatException e) {
    // return parts[index].compareTo(partsOther[index]);
    // }
    // }
    // if (parts.length > partsOther.length) {
    // return 1;
    // }
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
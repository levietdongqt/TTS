package com.element_hierarchy.element_hierarchy.Service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.data.repository.query.Param;

import com.element_hierarchy.element_hierarchy.Model.ADM_REF_UI;
import com.element_hierarchy.element_hierarchy.Model.REF_MAP_DETAIL;

public interface IAdmRefUiService {
    public Set<ADM_REF_UI> getAll();

    public List<ADM_REF_UI> getParentList();

    public ADM_REF_UI getByUiCtrlCode(String uiCtrlCode);

    public TreeSet<ADM_REF_UI> getByUiCtrlCodeStartingWith(String uiCtrlCode);

    public boolean buildTree(ADM_REF_UI root, Set<ADM_REF_UI> children);

    public boolean buildRelationship(Set<ADM_REF_UI> root);

    public int countLevel(Set<ADM_REF_UI> root);

    public TreeSet<ADM_REF_UI> mappingRefMapToAdmRefUI(TreeSet<ADM_REF_UI> adm_REF_UIs, String uiCtrlCode);

    public TreeSet<ADM_REF_UI> getListByUiCtrlCodeHasBoFieldName(String uiCtrlCode);

    public void test2();
}

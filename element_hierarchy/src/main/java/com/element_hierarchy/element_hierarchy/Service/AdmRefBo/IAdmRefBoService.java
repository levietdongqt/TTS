package com.element_hierarchy.element_hierarchy.Service.AdmRefBo;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.element_hierarchy.element_hierarchy.Model.ADM_REF_BO;
import com.element_hierarchy.element_hierarchy.Model.ADM_REF_UI;

public interface IAdmRefBoService {
    public void buildBoFieldDir(List<ADM_REF_BO> root);
    public void saveToRefBoService(Set<ADM_REF_BO> tree);
    public TreeSet<ADM_REF_BO> sortByUICtrlCode(Set<ADM_REF_BO> list);
    public Set<ADM_REF_BO> checkUnique(List<ADM_REF_BO> list);
    public List<ADM_REF_BO> getListByBoFieldDir(TreeSet<ADM_REF_BO> boFieldDir);
    public List<ADM_REF_BO> mappingUiCtrlCodeToRefBo(List<ADM_REF_BO> hasUiCtrlCodeList,List<ADM_REF_BO> noUiCtrlCodeList);
    public List<ADM_REF_BO> buildAdmRefBosUpdate(TreeSet<ADM_REF_UI> root, String[] values,boolean[] isUpdates);
    public void updateToAdmRefBo(List<ADM_REF_BO> admRefBos);
    public List<ADM_REF_BO> removeEmpty(List<ADM_REF_BO> list);
}

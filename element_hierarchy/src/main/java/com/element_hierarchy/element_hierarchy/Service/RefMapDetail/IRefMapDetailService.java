package com.element_hierarchy.element_hierarchy.Service.RefMapDetail;

import java.util.List;
import java.util.TreeSet;

import com.element_hierarchy.element_hierarchy.Model.ADM_REF_BO;
import com.element_hierarchy.element_hierarchy.Model.ADM_REF_UI;
import com.element_hierarchy.element_hierarchy.Model.REF_MAP_DETAIL;

public interface IRefMapDetailService {
    public List<REF_MAP_DETAIL> buildRefMapDetailsV2( List<ADM_REF_BO> adm_REF_BOs);
    public int saveToRefMapDetail(List<REF_MAP_DETAIL> ref_MAP_DETAILs);
    public int updateToRefMapDetail(List<REF_MAP_DETAIL> ref_MAP_DETAILs);
}
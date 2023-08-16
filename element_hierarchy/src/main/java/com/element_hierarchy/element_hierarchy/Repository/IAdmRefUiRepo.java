/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.element_hierarchy.element_hierarchy.Repository;

import com.element_hierarchy.element_hierarchy.Model.ADM_REF_UI;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author DONG
 */
@Repository
public interface IAdmRefUiRepo extends JpaRepository<ADM_REF_UI, Integer> {
    @Procedure(procedureName = "ADM_REF_UI_HANDLE.LOAD_REF_UI_BY_UiCtrlCode", outputParameterName = "onResult", refCursor = true)
    public ADM_REF_UI getByUiCtrlCode(@Param("uiCtrlCode") String uiCtrlCode);

    @Procedure(procedureName = "ADM_REF_UI_HANDLE.LOAD_REF_UI_STARTW_UiCtrlCode", outputParameterName = "onResult", refCursor = true)
    public List<ADM_REF_UI> getByUiCtrlCodeStartingWith(
            @Param("uiCtrlCode") String uiCtrlCode);

    @Procedure(procedureName = "ADM_REF_UI_HANDLE.Get_List_By_Ref_Map_LeftJoin_AdM_Ref_BO", outputParameterName = "onResult", refCursor = true)
    public List<Object[]> getListByUiCtrlCodeHasBoFieldName(@Param("uiCtrlCode") String uiCtrlCode);

    @Procedure(procedureName = "ADM_REF_UI_HANDLE.LOAD_ALL_ADM_REF_UI", outputParameterName = "onResult", refCursor = true)
    public List<ADM_REF_UI> getAll();

    @Procedure(procedureName = "ADM_REF_UI_HANDLE.LOAD_Parent", outputParameterName = "onResult", refCursor = true)
    public List<ADM_REF_UI> getParenList();
}

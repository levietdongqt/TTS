package com.element_hierarchy.element_hierarchy.Repository;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.element_hierarchy.element_hierarchy.Model.REF_MAP_DETAIL;
import com.element_hierarchy.element_hierarchy.Model.TRECORD;

@Repository
public interface IRefMapDetailsRepo extends JpaRepository<REF_MAP_DETAIL, Integer> {
    @Procedure(procedureName = "REF_MAP_DETAIL_hanlder.Save_By_JSON")
    public void insertRefMapDetail(@Param("Json_data") String Json_data);

    @Transactional
    @Procedure(procedureName = "REF_MAP_DETAIL_hanlder.Update_By_JSON")
    public void UpdateRefMapDetail(@Param("Json_data") String Json_data);

    @Procedure(procedureName = "REF_MAP_DETAIL_hanlder.get_List_By_UiCtrlCode", refCursor = true)
    public List<REF_MAP_DETAIL> getListByUiCtrlCode(
            @Param("uiCtrlCodes") String uiCtrlCodes);
}

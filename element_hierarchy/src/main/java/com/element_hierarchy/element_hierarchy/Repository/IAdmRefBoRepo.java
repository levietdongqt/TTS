package com.element_hierarchy.element_hierarchy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.element_hierarchy.element_hierarchy.Model.ADM_REF_BO;

@Repository
public interface IAdmRefBoRepo extends JpaRepository<ADM_REF_BO, Integer> {

  @Procedure(procedureName = "ADM_REF_BO_HANDLE.Save_By_JSON")
  public void saveToAdmRefBo(@Param("Json_data") String Json_data);

  @Procedure(procedureName = "ADM_REF_BO_HANDLE.Get_List_By_Bo_Field_Dir",outputParameterName = "onResult",refCursor = true)
  public List<ADM_REF_BO> getListByBoFieldDir(@Param("v_bo_field_dir")String boFieldDir);
  @Procedure(procedureName = "ADM_REF_BO_HANDLE.Update_By_JSON")
  public void updateToAdmRefBo(@Param("Json_Data")String Json_Data);
}

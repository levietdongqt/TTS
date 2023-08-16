package com.element_hierarchy.element_hierarchy.Repository;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.element_hierarchy.element_hierarchy.Model.REF_MAP_DETAIL;
import com.element_hierarchy.element_hierarchy.Model.TRECORD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;

@Repository
@EnableJpaRepositories
// @NamedStoredProcedureQuery(name = "getListByUiCtrlCode", procedureName =
// "ref_map_detail_test_hanlder.get_List_By_UiCtrlCode", parameters = {
// @StoredProcedureParameter(mode = ParameterMode.IN, name = "uiCtrlCode", type
// = String.class),
// @StoredProcedureParameter(mode = ParameterMode.OUT, name = "onResult", type =
// Class.class)
// })
public class IRefMapDetailsRepoTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int insertRefMapDetail(List<REF_MAP_DETAIL> list) {
        StringBuilder sql = new StringBuilder();
        sql.append("DECLARE ");
        sql.append(" records ref_map_detail_test_hanlder.trecords; ");
        sql.append("BEGIN ");
        sql.append(" records \\:= ref_map_detail_test_hanlder.trecords(); records.EXTEND(:size); ");
        for (int i = 0; i < list.size(); i++) {
            sql.append(" records(").append(i + 1).append(") \\:= ")
                    .append("ref_map_detail_test_hanlder.TRECORD")
                    .append(list.get(i).toString()).append("; ");
        }
        sql.append(" ref_map_detail_test_hanlder.SaveTo_REF_MAP_DETAIL(records =>records,inserted => :inserted); ");
        sql.append("END;");
        Query query = entityManager.createNativeQuery(sql.toString());
        float inserted = 0f;
        query.setParameter("size", list.size());
        query.setParameter("inserted", inserted);
        System.out.println(list.get(0).toString());
        query.executeUpdate();
        return (int) inserted;
    };

    public List<REF_MAP_DETAIL> getListByUiCtrlCode(String uiCtrlCode) {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("ref_map_detail_test_hanlder.get_List_By_UiCtrlCode");
        storedProcedure.registerStoredProcedureParameter("uiCtrlCode", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("onResult", List.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("uiCtrlCode", uiCtrlCode);
        storedProcedure.execute();
        List<REF_MAP_DETAIL> resultList = new ArrayList<>();
        List<Object[]> objects = storedProcedure.getResultList();
        for (Object[] object : objects) {
            if (object[8] == null || object[8].equals("null")) {
                continue;
            }
            REF_MAP_DETAIL ref_MAP_DETAIL = new REF_MAP_DETAIL();
            ref_MAP_DETAIL.setCreatedDate((Timestamp) object[10]);
            ref_MAP_DETAIL.setMapCode(String.valueOf(object[1]));
            // ref_MAP_DETAIL.setBoFieldCode(String.valueOf(object[3]));
            ref_MAP_DETAIL.setDescription(String.valueOf(object[8]));
            ref_MAP_DETAIL.setUiCtrlCode(String.valueOf(object[2]));
            resultList.add(ref_MAP_DETAIL);
        }
        return resultList;
    }
    @Modifying
    @jakarta.transaction.Transactional
    public int updateToRefMapDetail(List<REF_MAP_DETAIL> list) {
        StringBuilder sql = new StringBuilder();
        sql.append("DECLARE ");
        sql.append(" records ref_map_detail_test_hanlder.trecords; ");
        sql.append("BEGIN ");
        sql.append(" records \\:= ref_map_detail_test_hanlder.trecords(); records.EXTEND(:size); ");
        for (int i = 0; i < list.size(); i++) {
            sql.append(" records(").append(i + 1).append(") \\:= ")
                    .append("ref_map_detail_test_hanlder.TRECORD")
                    .append(list.get(i).toString()).append("; ");
        }
        sql.append(" ref_map_detail_test_hanlder.UpdateTo_REF_MAP_DETAIL(records =>records); ");
        sql.append("END;");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("size", list.size());
        System.out.println(list.get(0).toString());
        query.executeUpdate();
        return 1;
    }

    @Transactional
    public void insertRefMapDetail2(List<TRECORD> list) {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("ref_map_detail_test_hanlder.SaveTo_REF_MAP_DETAIL");
        storedProcedure.registerStoredProcedureParameter("records", Array.class,
                ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("inserted", Integer.class,
                ParameterMode.OUT);
        int inserted = 0;
        Object[] a = list.toArray(new Object[0]);
        storedProcedure.setParameter("records",a);
        storedProcedure.setParameter("inserted", inserted);
        storedProcedure.execute();
        List<REF_MAP_DETAIL> resultList = storedProcedure.getResultList();
    };
}

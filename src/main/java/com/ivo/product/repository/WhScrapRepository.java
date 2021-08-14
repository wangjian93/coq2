package com.ivo.product.repository;

import com.ivo.product.entity.WhScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface WhScrapRepository extends JpaRepository<WhScrap, Long> {

    @Query(value = "from WhScrap a where a.costCenter=:costCenter and a.userDepartment in :userDepartments " +
            "and a.dateOfOrder>=:fromDate and a.dateOfOrder<=:toDate")
    List<WhScrap> getWhScrapByCostCenterAndUserDepartment(String costCenter, String[] userDepartments, Date fromDate, Date toDate);

    /**
     * 根据起草部门和成本中心统计仓库报废费用
     * @param costCenter 成本中心
     * @param userDepartments 起草单位
     * @return
     */
    @Query(value = "select sum(a.scrapMoneyCny) from WhScrap a where a.costCenter=:costCenter and a.userDepartment in :userDepartments " +
            "and a.dateOfOrder>=:fromDate and a.dateOfOrder<=:toDate")
    Double sumByCostCenterAndUserDepartment(String costCenter, String[] userDepartments, Date fromDate, Date toDate);

    /**
     * 根据成本中心、报费原因统计仓库报废费用
     * @param costCenter 成本中心
     * @param costTypes 报废原因
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    @Query(value = "select sum(a.scrapMoneyCny) from WhScrap a where a.costCenter=:costCenter and a.costType in :costTypes " +
            "and a.dateOfOrder>=:fromDate and a.dateOfOrder<=:toDate")
    Double sumByCostCenterAndCostType(String costCenter, String[] costTypes, Date fromDate, Date toDate);

    /**
     * 根据成本中心、实验类型统计仓库报废费用
     * @param costCenter 成本中心
     * @param expTypes 实验类型
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return
     */
    @Query(value = "select sum(a.scrapMoneyCny) from WhScrap a where a.costCenter=:costCenter and a.expType in :expTypes " +
            "and a.dateOfOrder>=:fromDate and a.dateOfOrder<=:toDate")
    Double sumByCostCenterAndExpType(String costCenter, String[] expTypes, Date fromDate, Date toDate);
}

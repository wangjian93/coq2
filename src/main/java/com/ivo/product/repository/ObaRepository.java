package com.ivo.product.repository;

import com.ivo.product.entity.Oba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
public interface ObaRepository extends JpaRepository<Oba, Long> {

    /**
     * OBA费用汇总求和求和
     * @param fromDate
     * @param toDate
     * @param fab
     * @return
     */
    @Query("select  sum(a.amount) from Oba a where a.happenTime>=:fromDate and a.happenTime<=:toDate and a.fab=:fab")
    Double sumObaAmont(Date fromDate, Date toDate, String fab);

    @Query(" from Oba a where a.happenTime>=:fromDate and a.happenTime<=:toDate and a.fab=:fab")
    List<Oba> getObaAmont(Date fromDate, Date toDate, String fab);
}

package com.ivo.product.repository;

import com.ivo.product.entity.ObaMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 */
public interface ObaMonthRepository extends JpaRepository<ObaMonth, Long> {

    List<ObaMonth> findByFabAndMonthIn(String fab, List<String> months);
}

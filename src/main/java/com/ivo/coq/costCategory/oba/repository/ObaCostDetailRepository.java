package com.ivo.coq.costCategory.oba.repository;

import com.ivo.coq.costCategory.oba.entity.ObaCostDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ObaCostDetailRepository extends JpaRepository<ObaCostDetail, Long> {

    List<ObaCostDetail> findByProjectOrderById(String project);
}

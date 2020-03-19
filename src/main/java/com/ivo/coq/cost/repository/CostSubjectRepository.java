package com.ivo.coq.cost.repository;

import com.ivo.coq.cost.entity.CostSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface CostSubjectRepository extends JpaRepository<CostSubject, Long> {

    /**
     * 根据机种筛选
     * @param project 机种
     * @return List<CostSubject>
     */
    List<CostSubject> findByProjectOrderById(String project);
}

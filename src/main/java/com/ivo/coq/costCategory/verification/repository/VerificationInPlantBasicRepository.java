package com.ivo.coq.costCategory.verification.repository;

import com.ivo.coq.costCategory.verification.entity.VerificationInPlantBasic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface VerificationInPlantBasicRepository extends JpaRepository<VerificationInPlantBasic, Long> {

    List<VerificationInPlantBasic> findByValidFlagIsTrue();

    VerificationInPlantBasic findFirstByNameAndValidFlagIsTrue(String name);
}

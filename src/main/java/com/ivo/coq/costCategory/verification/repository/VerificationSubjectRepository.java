package com.ivo.coq.costCategory.verification.repository;

import com.ivo.coq.costCategory.verification.entity.VerificationSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 验证项目仓库
 * @author wj
 * @version 1.0
 */
public interface VerificationSubjectRepository extends JpaRepository<VerificationSubject, Long> {

    /**
     * 筛选验证项目名
     * @param subjectName 验证项目
     * @return VerificationSubject
     */
    VerificationSubject findFirstBySubjectName(String subjectName);

    /**
     * 晒寻温湿度类或非温湿度类
     * @param humitureType 温湿度类型
     * @return
     */
    List<VerificationSubject> findByHumitureType(String humitureType);
}

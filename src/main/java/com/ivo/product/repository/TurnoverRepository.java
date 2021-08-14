package com.ivo.product.repository;

import com.ivo.product.entity.Turnover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface TurnoverRepository extends JpaRepository<Turnover, Long> {

    List<Turnover> findBySeasonGreaterThanEqualAndSeasonLessThanEqual(String fromS, String toS);
}

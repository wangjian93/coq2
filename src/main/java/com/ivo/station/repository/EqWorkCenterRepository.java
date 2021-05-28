package com.ivo.station.repository;

import com.ivo.station.entity.EqWorkCenter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wj
 * @version 1.0
 */
public interface EqWorkCenterRepository extends JpaRepository<EqWorkCenter, Long> {

    EqWorkCenter findFirstByEqAndFab(String eq, String fab);

    EqWorkCenter findFirstByWorkCenterAndFab(String workCenter, String fab);
}

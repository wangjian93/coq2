package com.ivo.coq.report.service.impl;

import com.ivo.coq.report.entity.InLossAmount;
import com.ivo.coq.report.entity.InLossAmountDetail;
import com.ivo.coq.report.repository.InLossAmountDetailRepository;
import com.ivo.coq.report.repository.InLossAmountRepository;
import com.ivo.coq.report.service.InLossAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
public class InLossAmountServiceImpl implements InLossAmountService {

    private InLossAmountRepository inLossAmountRepository;
    private InLossAmountDetailRepository inLossAmountDetailRepository;

    @Autowired
    public InLossAmountServiceImpl(InLossAmountRepository inLossAmountRepository,
                                   InLossAmountDetailRepository inLossAmountDetailRepository) {
        this.inLossAmountRepository = inLossAmountRepository;
        this.inLossAmountDetailRepository = inLossAmountDetailRepository;
    }

    @Override
    public List<InLossAmount> getInLossAmount(String FAB_ID, Date fromDate, Date toDate) {
        return inLossAmountRepository.findByFabIdAndFabDateBetween(FAB_ID, fromDate, toDate);
    }

    @Override
    public List<InLossAmountDetail> getInLossAmountDetail(String FAB_ID, Date FAB_DATE) {
        return inLossAmountDetailRepository.findByFabIdAndFabDate(FAB_ID, FAB_DATE);
    }
}

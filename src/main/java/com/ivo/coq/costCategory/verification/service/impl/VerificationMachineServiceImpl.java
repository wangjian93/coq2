package com.ivo.coq.costCategory.verification.service.impl;

import com.ivo.coq.costCategory.verification.entity.VerificationMachine;
import com.ivo.coq.costCategory.verification.repository.VerificationMachineRepository;
import com.ivo.coq.costCategory.verification.service.VerificationMachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Slf4j
@Service
public class VerificationMachineServiceImpl implements VerificationMachineService {

    private VerificationMachineRepository repository;

    @Autowired
    public VerificationMachineServiceImpl(VerificationMachineRepository repository) {
        this.repository = repository;
    }

    @Override
    public VerificationMachine getVerificationMachine(String machineName) {
        return repository.findFirstByMachineName(machineName);
    }

    @Override
    public List<VerificationMachine> getHumitureVerificationMachines() {
        return repository.findByHumitureType(VerificationMachine.Humiture_Type_Yes);
    }

    @Override
    public List<VerificationMachine> getNoHumitureVerificationMachines() {
        return repository.findByHumitureType(VerificationMachine.Humiture_Type_No);
    }
}

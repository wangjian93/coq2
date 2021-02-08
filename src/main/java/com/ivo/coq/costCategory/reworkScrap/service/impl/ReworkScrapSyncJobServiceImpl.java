package com.ivo.coq.costCategory.reworkScrap.service.impl;

import com.ivo.common.enums.PlantEnum;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostArray;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostCell;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapCostLcm;
import com.ivo.coq.costCategory.reworkScrap.entity.ReworkScrapSyncJob;
import com.ivo.coq.costCategory.reworkScrap.repository.ReworkScrapSyncJobRepository;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapCostDetailService;
import com.ivo.coq.costCategory.reworkScrap.service.ReworkScrapSyncJobService;
import com.ivo.coq.project.entity.EngineeringExperiment;
import com.ivo.coq.project.entity.EngineeringExperimentProduct;
import com.ivo.coq.project.entity.EngineeringExperimentWo;
import com.ivo.coq.project.entity.Sample;
import com.ivo.coq.project.service.EngineeringExperimentService;
import com.ivo.rest.oracle.OracleService;
import com.ivo.rest.oracle.entity.OracleReworkScrapArray;
import com.ivo.rest.oracle.entity.OracleReworkScrapCell;
import com.ivo.rest.oracle.entity.OracleReworkScrapLcm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class ReworkScrapSyncJobServiceImpl implements ReworkScrapSyncJobService {

    private EngineeringExperimentService engineeringExperimentService;

    private ReworkScrapSyncJobRepository reworkScrapSyncJobRepository;

    private OracleService oracleService;

    private ReworkScrapCostDetailService reworkScrapCostDetailService;

    @Autowired
    public ReworkScrapSyncJobServiceImpl(EngineeringExperimentService engineeringExperimentService,
                                          ReworkScrapSyncJobRepository reworkScrapSyncJobRepository,
                                         OracleService oracleService,
                                         ReworkScrapCostDetailService reworkScrapCostDetailService) {
        this.engineeringExperimentService = engineeringExperimentService;
        this.reworkScrapSyncJobRepository = reworkScrapSyncJobRepository;
        this.oracleService = oracleService;
        this.reworkScrapCostDetailService = reworkScrapCostDetailService;
    }

    @Override
    public void generateJob(String project) {
        log.info("生成重工报废数据同步任务 " + project);
        List<ReworkScrapSyncJob> oldJobList =  reworkScrapSyncJobRepository.findByProject(project);
        for(ReworkScrapSyncJob job : oldJobList) {
            reworkScrapCostDetailService.deleteArray(job.getId());
            reworkScrapCostDetailService.deleteCell(job.getId());
            reworkScrapCostDetailService.deleteLcm(job.getId());
        }
        reworkScrapSyncJobRepository.deleteAll(oldJobList);

        List<EngineeringExperiment> engineeringExperimentList = engineeringExperimentService.getEngineeringExperiments(project);
        List<ReworkScrapSyncJob> jobList = new ArrayList<>();
        for(EngineeringExperiment engineeringExperiment : engineeringExperimentList) {
            Sample sample = engineeringExperiment.getSample();
            if(StringUtils.equalsAnyIgnoreCase(engineeringExperiment.getPlant(), PlantEnum.Array.getPlant(), PlantEnum.Cell.getPlant())) {
                List<EngineeringExperimentProduct> productList = engineeringExperiment.getProductList();
                if(productList != null && productList.size()>0) {
                    for(EngineeringExperimentProduct product : productList) {
                        ReworkScrapSyncJob reworkScrapSyncJob = new ReworkScrapSyncJob(sample);
                        reworkScrapSyncJob.setPlant(engineeringExperiment.getPlant());
                        reworkScrapSyncJob.setProduct(product.getProduct());
                        reworkScrapSyncJob.setTft(product.getTft());
                        reworkScrapSyncJob.setCf(product.getCf());
                        reworkScrapSyncJob.setStatus(ReworkScrapSyncJob.STATUS_WAIT);
                        jobList.add(reworkScrapSyncJob);
                    }
                }
            }
            if(StringUtils.equalsIgnoreCase(engineeringExperiment.getPlant(), PlantEnum.Lcm.getPlant())) {
                List<EngineeringExperimentWo> woList = engineeringExperiment.getWoList();
                if(woList != null && woList.size()>0) {
                    for(EngineeringExperimentWo wo : woList) {
                        ReworkScrapSyncJob reworkScrapSyncJob = new ReworkScrapSyncJob(sample);
                        reworkScrapSyncJob.setPlant(engineeringExperiment.getPlant());
                        reworkScrapSyncJob.setWo(wo.getWo());
                        reworkScrapSyncJob.setStatus(ReworkScrapSyncJob.STATUS_WAIT);
                        jobList.add(reworkScrapSyncJob);
                    }
                }
            }
        }
        reworkScrapSyncJobRepository.saveAll(jobList);
    }

    @Override
    public void runJob(String project) {
        log.info("执行机种的重工报废数据同步任务 " + project);
        List<ReworkScrapSyncJob> jobList = reworkScrapSyncJobRepository.findByProjectOrderByPlant(project);
        for(ReworkScrapSyncJob job : jobList) {
            if(StringUtils.equalsIgnoreCase(job.getStatus(), ReworkScrapSyncJob.STATUS_COMP)) continue;

            if(StringUtils.equalsIgnoreCase(job.getPlant(), PlantEnum.Array.getPlant())) {
                job.setStatus("RUN");
                reworkScrapSyncJobRepository.save(job);

                if(StringUtils.isNotEmpty(job.getProduct())) {
                    List<OracleReworkScrapArray> arrayList = oracleService.getOracleReworkScrapArray(job.getInDate(), job.getOutDate(),
                            job.getProduct());
                    reworkScrapCostDetailService.deleteArray(job.getId());
                    if(arrayList != null && arrayList.size()>0) {
                        List<ReworkScrapCostArray> reworkScrapCostArrayList = new ArrayList<>();
                        for(OracleReworkScrapArray array : arrayList) {
                            ReworkScrapCostArray reworkScrapCostArray = new ReworkScrapCostArray(job);
                            reworkScrapCostArray.setFabDate(array.getFAB_DATE());
                            reworkScrapCostArray.setProdId(array.getPROD_ID());
                            reworkScrapCostArray.setRouteId(array.getROUTE_ID());
                            reworkScrapCostArray.setEvtCate(array.getEVT_CATE());
                            reworkScrapCostArray.setQty(array.getQty());
                            reworkScrapCostArray.setProdModel(array.getPROD_MODEL_ID());
                            reworkScrapCostArray.setOpeId(array.getOPE_ID());
                            reworkScrapCostArrayList.add(reworkScrapCostArray);
                        }
                        reworkScrapCostDetailService.saveArray(reworkScrapCostArrayList);
                    }
                }

                job.setStatus(ReworkScrapSyncJob.STATUS_COMP);
                reworkScrapSyncJobRepository.save(job);
            } else if(StringUtils.equalsIgnoreCase(job.getPlant(), PlantEnum.Cell.getPlant())) {
                job.setStatus("RUN");
                reworkScrapSyncJobRepository.save(job);

                if(StringUtils.isNoneEmpty(job.getProduct(), job.getTft(), job.getCf())) {
                    List<OracleReworkScrapCell> cellList = oracleService.getOracleReworkScrapCell(job.getInDate(), job.getOutDate(),
                            job.getProduct(), job.getTft(), job.getCf());
                    reworkScrapCostDetailService.deleteCell(job.getId());
                    if(cellList != null && cellList.size()>0) {
                        List<ReworkScrapCostCell> reworkScrapArrayList = new ArrayList<>();
                        for(OracleReworkScrapCell cell : cellList) {
                            ReworkScrapCostCell reworkScrapCostCell = new ReworkScrapCostCell(job);
                            reworkScrapCostCell.setFabDate(cell.getFAB_DATE());
                            reworkScrapCostCell.setProdId(cell.getPROD_ID());
                            reworkScrapCostCell.setAryProdId(cell.getPROD_ID());
                            reworkScrapCostCell.setProductType(cell.getPRODUCT_TYP());
                            reworkScrapCostCell.setNxOpeId(cell.getNX_OPE_ID());
                            reworkScrapCostCell.setEvtCate(cell.getEVT_CATE());
                            reworkScrapCostCell.setQty(cell.getQTY());
                            reworkScrapCostCell.setQty0800(cell.getQTY_0800());
                            reworkScrapCostCell.setProdModelId(cell.getPROD_MODEL_ID());
                            reworkScrapArrayList.add(reworkScrapCostCell);
                        }
                        reworkScrapCostDetailService.saveCell(reworkScrapArrayList);
                    }
                }

                job.setStatus(ReworkScrapSyncJob.STATUS_COMP);
                reworkScrapSyncJobRepository.save(job);
            } else if(StringUtils.equalsIgnoreCase(job.getPlant(), PlantEnum.Lcm.getPlant())) {
                job.setStatus("RUN");
                reworkScrapSyncJobRepository.save(job);

                if(StringUtils.isNotEmpty(job.getWo())) {
                    List<OracleReworkScrapLcm> lcmList = oracleService.getOracleReworkScrapLcm(job.getInDate(), job.getOutDate(),
                            job.getWo());
                    reworkScrapCostDetailService.deleteLcm(job.getId());
                    if(lcmList != null && lcmList.size()>0) {
                        List<ReworkScrapCostLcm> reworkScrapCostLcmArrayList = new ArrayList<>();
                        for(OracleReworkScrapLcm lcm : lcmList) {
                            ReworkScrapCostLcm reworkScrapCostLcm = new ReworkScrapCostLcm(job);
                            reworkScrapCostLcm.setFabDate(lcm.getFAB_DATE());
                            reworkScrapCostLcm.setProdId(lcm.getPROD_ID());
                            reworkScrapCostLcm.setWoId(lcm.getPROD_ID());
                            reworkScrapCostLcm.setCrOpeId(lcm.getCR_OPE_ID());
                            reworkScrapCostLcm.setEvtCate(lcm.getEVT_CATE());
                            reworkScrapCostLcm.setQty(lcm.getQTY());
                            reworkScrapCostLcm.setScrpMOpeId(lcm.getSCRP_M_OPE_ID());
                            reworkScrapCostLcm.setProdModelId(lcm.getPROD_MODEL_ID());
                            reworkScrapCostLcmArrayList.add(reworkScrapCostLcm);
                        }
                        reworkScrapCostDetailService.saveLcm(reworkScrapCostLcmArrayList);
                    }
                }

                job.setStatus(ReworkScrapSyncJob.STATUS_COMP);
                reworkScrapSyncJobRepository.save(job);
            }
        }
    }
}

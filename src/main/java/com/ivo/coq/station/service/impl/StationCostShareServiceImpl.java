package com.ivo.coq.station.service.impl;

import com.ivo.common.enums.PlantEnum;
import com.ivo.common.utils.DoubleUtil;
import com.ivo.common.utils.ExcelUtil;
import com.ivo.coq.station.entity.StationCostShare;
import com.ivo.coq.station.repository.StationCostShareRepository;
import com.ivo.coq.station.service.StationCostShareService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
@Service
@Slf4j
public class StationCostShareServiceImpl implements StationCostShareService {

    private StationCostShareRepository stationCostShareRepository;

    @Autowired
    public StationCostShareServiceImpl(StationCostShareRepository stationCostShareRepository) {
        this.stationCostShareRepository = stationCostShareRepository;
    }

    @Override
    public List<StationCostShare> getStationCostShare(String project) {
        return stationCostShareRepository.findByProject(project);
    }

    @Override
    public List<StationCostShare> getBeforeStation(String project, String factory, String station) {
        StationCostShare stationCostShare = getStationCostShare(project, factory, station);
        if(stationCostShare == null) return new ArrayList<>();
        else return stationCostShareRepository.getBeforeStation(project, factory, stationCostShare.getSort());
    }

    @Override
    public StationCostShare getStationCostShare(String project, String factory, String station) {
        return stationCostShareRepository.findFirstByProjectAndFactoryAndStation(project, factory, station);
    }

    @Override
    public List<StationCostShare> getStationCostShare(String project, String factory) {
        return stationCostShareRepository.findByProjectAndFactory(project, factory);
    }

    @Override
    public Double getScrapAmount(String project, String factory, String station) {
        StationCostShare stationCostShare = getStationCostShare(project, factory, station);
        if(stationCostShare == null) return null;
        else return stationCostShare.getTotal();
    }

    @Override
    public Double getReworkAmount(String project, String factory, String station) {
        List<StationCostShare> list = getBeforeStation(project, factory, station);
        Double amount = null;
        for(StationCostShare stationCostShare : list) {
            amount = DoubleUtil.sum(amount, stationCostShare.getTotal_());
        }
        return amount;
    }

    @Override
    public Double getProductAmount(String project, String factory) {
        List<StationCostShare> stationCostShareList = getStationCostShare(project, factory);
        Double amount = null;
        for(StationCostShare stationCostShare : stationCostShareList) {
            amount = DoubleUtil.sum(amount, stationCostShare.getTotal());
        }
        return amount;
    }

    @Override
    public void importStationCostShare(InputStream excel, String fileName) throws IOException {
        List<List<Object>> list;
        if (fileName.endsWith("xls")) {
            list =  ExcelUtil.readXlsFirstSheet(excel);

        }
        else if (fileName.endsWith("xlsx")) {
            list =  ExcelUtil.readXlsxFirstSheet(excel);
        }
        else {
            throw new IOException("文件类型错误");
        }

        String[] cols = {"project", "factory", "station", "description", "machine",
                "manpower", "ndirectMaterial", "repair", "energy", "depreciation", "other", "total",
                "manpower_", "ndirectMaterial_", "repair_", "energy_", "depreciation_", "other_", "total_"};

        // 前两行为标题，数据从第三行开始
        int rowInt = 2;
        int colInt = 0;
        List<StationCostShare> stationCostShareList = new ArrayList<>();
        for(; rowInt<list.size(); rowInt++) {
            List row = list.get(rowInt);
            HashMap<String, Object> map = new HashMap<>();
            for(; colInt<row.size(); colInt++) {
                map.put(cols[colInt], row.get(colInt));
            }
            StationCostShare stationCostShare = new StationCostShare();
            stationCostShare.setProject((String) map.get("project"));
            String factory = ((String) map.get("factory")).trim();
            if(StringUtils.containsIgnoreCase(factory, PlantEnum.Cell.getPlant())) {
                factory = PlantEnum.Cell.getPlant();
            } else if(StringUtils.containsIgnoreCase(factory, PlantEnum.Array.getPlant())) {
                factory = PlantEnum.Array.getPlant();
            } else if(StringUtils.containsIgnoreCase(factory, PlantEnum.Lcm.getPlant())) {
                factory = PlantEnum.Lcm.getPlant();
            }
            stationCostShare.setFactory(factory);
            if(map.get("station") instanceof String) {
                stationCostShare.setStation( (String) map.get("station") );
            } else {
                BigDecimal b = (BigDecimal) map.get("station");
                stationCostShare.setStation(b.toString());
            }
            stationCostShare.setDescription((String) map.get("description"));
            stationCostShare.setMachine((String) map.get("machine"));
            stationCostShare.setManpower( convertDouble(map.get("manpower")));
            stationCostShare.setNdirectMaterial( convertDouble(map.get("ndirectMaterial")) );
            stationCostShare.setRepair( convertDouble(map.get("repair")) );
            stationCostShare.setEnergy( convertDouble(map.get("energy")) );
            stationCostShare.setDepreciation( convertDouble(map.get("depreciation")) );
            stationCostShare.setOther( convertDouble(map.get("other")) );
            stationCostShare.setTotal( convertDouble(map.get("total")) );
            stationCostShare.setManpower_( convertDouble(map.get("manpower_")) );
            stationCostShare.setNdirectMaterial_( convertDouble(map.get("ndirectMaterial_")) );
            stationCostShare.setRepair_( convertDouble(map.get("repair_")) );
            stationCostShare.setEnergy_( convertDouble(map.get("energy")) );
            stationCostShare.setDepreciation_( convertDouble(map.get("depreciation_")) );
            stationCostShare.setOther_( convertDouble(map.get("other_")) );
            stationCostShare.setTotal_( convertDouble(map.get("total_")) );

            stationCostShare.setSort(rowInt-2);
            stationCostShareList.add(stationCostShare);
            colInt = 0;
        }

        if(stationCostShareList.size()>0) {
            String project = stationCostShareList.get(0).getProject();
            stationCostShareRepository.deleteAll(getStationCostShare(project));
            stationCostShareRepository.saveAll(stationCostShareList);
        }
    }

    private Double convertDouble(Object obj) {
        if(obj == null) return null;
        if(obj instanceof String) {
            if( ((String) obj).trim().equals("") ) {
                return null;
            } else {
                return Double.valueOf((String) obj);
            }
        } else if(obj instanceof BigDecimal) {
            BigDecimal b = (BigDecimal) obj;
            return b.doubleValue();
        }
        return null;
    }

    @Override
    public String matchStation(String project, String factory, String station) {
        if(StringUtils.isEmpty(station)) return null;
        // CELL站点只有三码时首部加0
        if(StringUtils.equalsIgnoreCase(factory, "Cell") && station.length()==3) {
            station = "0" + station;
        }
        // 匹配不到站点时，匹配前三码，还没有然后再前两码
        StationCostShare stationObj = stationCostShareRepository.findFirstByProjectAndFactoryAndStationLike(project, factory, station+"%");
        if(stationObj == null) {
            if(station.length()>3) {
                station = station.substring(0, 3);
            }
            stationObj = stationCostShareRepository.findFirstByProjectAndFactoryAndStationLike(project, factory, station+"%");
            if(stationObj == null) {
                if(station.length()>2) {
                    station = station.substring(0, 2);
                }
                stationObj = stationCostShareRepository.findFirstByProjectAndFactoryAndStationLike(project, factory, station+"%");
                if(stationObj == null) return null;
            }
        }
        return stationObj.getStation();
    }
}

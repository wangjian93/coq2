package com.ivo.coq.costCategory.oba.service;

import com.ivo.coq.costCategory.oba.entity.ObaCostDetail;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public interface ObaCostDetailService {

    List<ObaCostDetail> getObaCostDetail(String project);
}

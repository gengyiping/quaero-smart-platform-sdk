package com.quaero.quaerosmartplatform.controller;

import com.quaero.quaerosmartplatform.annotations.ResponseResult;
import com.quaero.quaerosmartplatform.service.MaterialPlanInfoService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 计划到料 1-1
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021/1/13 16:08
 */
@RestController
@ResponseResult
@Api(tags = "计划到料相关接口")
//@PreAuthorize("hasAuthority('AAA')")
@RequestMapping("/api/materialPlan")
public class MaterialPlanInfoController {

    private final MaterialPlanInfoService materialPlanInfoService;

    public MaterialPlanInfoController(MaterialPlanInfoService materialPlanInfoService){
        this.materialPlanInfoService = materialPlanInfoService;
    }


}

package com.quaero.quaerosmartplatform.Scheduled;

import com.quaero.quaerosmartplatform.service.MaterialPlanInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 物料计划到料 定时器
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021/2/7 16:48
 */
@Component
@Log4j2
public class MaterialPlan {

    private final MaterialPlanInfoService materialPlanInfoService;

    public MaterialPlan(MaterialPlanInfoService materialPlanInfoService) {
        this.materialPlanInfoService = materialPlanInfoService;
    }

    @Scheduled(cron = "0 0 10 ? * *")
    public void testTasks() {
        log.info("物料计划到料过期检测");
        materialPlanInfoService.materialPlanScheduled();
    }
}

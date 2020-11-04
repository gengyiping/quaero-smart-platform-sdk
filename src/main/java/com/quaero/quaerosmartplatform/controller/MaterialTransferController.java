package com.quaero.quaerosmartplatform.controller;


import com.quaero.quaerosmartplatform.annotations.ResponseResult;
import com.quaero.quaerosmartplatform.domain.entity.JxPda001;
import com.quaero.quaerosmartplatform.service.JxPda001Service;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  物料转移 7-1
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-03
 */
@RestController
@ResponseResult
@Api(tags = "物料转移相关接口")
@RequestMapping("/JxPda001")
public class MaterialTransferController {

    @Autowired
    private JxPda001Service jxPda001Service;


}


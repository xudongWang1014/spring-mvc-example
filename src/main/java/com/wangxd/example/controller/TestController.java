package com.wangxd.example.controller;

import com.wangxd.example.dao.BillHeadDao;
import com.wangxd.example.po.entity.BillHeadEntity;
import com.wangxd.example.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private BillHeadDao billHeadDao;
    @Autowired
    private RedisService redisService;


    @RequestMapping(value = "/dao.do")
    public Object testDao(@RequestParam(required = false) String headKey) throws Exception {

        logger.info("===========test==============");

        BillHeadEntity entity = billHeadDao.findByKey("key");

        System.out.println(entity == null ? "为空" : entity.getHeadKey());

        redisService.set("123","test1111", 10);

        System.out.println(redisService.get("123"));

        return "成功";
    }

}



package com.x.framework.provider.service;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.yryz.framework.core.elasticjob.Simple;
import org.springframework.stereotype.Service;

@Simple(cron="0 * * * * ?", description = "spring boot 测试任务")
@Service
public class TestSimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("TestSimpleJob");
    }
}

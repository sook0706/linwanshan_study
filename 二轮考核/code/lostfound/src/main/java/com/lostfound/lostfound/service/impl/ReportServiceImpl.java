package com.lostfound.lostfound.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lostfound.lostfound.entity.Report;
import com.lostfound.lostfound.mapper.ReportMapper;
import com.lostfound.lostfound.service.ReportService;
import org.springframework.stereotype.Service;

/**
 * 举报业务实现类
 * 具体处理举报相关的业务逻辑
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {
    // 基础CRUD已经自动实现，不用写代码
}
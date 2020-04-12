package com.robod.controller;

import com.alibaba.fastjson.JSONArray;
import com.robod.entity.Record;
import com.robod.entity.ResultInfo;
import com.robod.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Robod Lee
 * @date 2020/3/12 11:41
 * 用于添加记录，删除记录之类的
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @RequestMapping("/uploadRecords")
    public ResultInfo<String> uploadRecords(String phoneNumber , String recordsJson) {
        ResultInfo<String> resultInfo = null;
        List<Record> records = JSONArray.parseArray(recordsJson , Record.class);
        try {
            resultInfo = recordService.uploadRecords(phoneNumber,records);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo<>();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("内部错误");
        }
        return resultInfo;
    }

    @RequestMapping("/downloadRecords")
    public ResultInfo<List<Record>> downloadRecords(String phoneNumber) {
        ResultInfo<List<Record>> resultInfo = null;
        try {
            resultInfo = recordService.downloadRecords(phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo<>();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("内部错误");
        }
        return resultInfo;
    }

}

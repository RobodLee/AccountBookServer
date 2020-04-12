package com.robod.service.impl;

import com.robod.entity.Record;
import com.robod.entity.ResultInfo;
import com.robod.mapper.RecordMapper;
import com.robod.service.RecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 李迪
 * @date 2020/3/12 14:43
 */
@Service("recordService")
@Transactional(rollbackFor = Exception.class)
public class RecordServiceImpl implements RecordService {

    private final RecordMapper recordMapper;

    public RecordServiceImpl(RecordMapper recordMapper) {
        this.recordMapper = recordMapper;
    }

    @Override
    public ResultInfo<String> uploadRecords(String phoneNumber, List<Record> records) throws Exception {
        for (Record record : records) {
            int status = record.getStatus();
            if (status == 1) {  //添加数据
                System.out.println(record.toString());
                recordMapper.addRecord(phoneNumber, record);
            } else if (status == 2) {   //删除数据
                recordMapper.deleteRecord(record.getUuid());
            } else if (status == 3) {   //修改数据
                recordMapper.upgradeRecord(record);
            }
        }
        ResultInfo<String> resultInfo = new ResultInfo<>();
        resultInfo.setFlag(true);
        resultInfo.setData("同步成功");
        return resultInfo;
    }

    @Override
    public ResultInfo<List<Record>> downloadRecords(String phoneNumber) throws Exception {
        List<Record> records = recordMapper.findAllByPhoneNumber(phoneNumber);
        ResultInfo<List<Record>> resultInfo = new ResultInfo<>();
        resultInfo.setFlag(true);
        resultInfo.setData(records);
        return resultInfo;
    }
}

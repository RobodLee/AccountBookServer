package com.robod.service;

import com.github.pagehelper.PageInfo;
import com.robod.entity.Record;
import com.robod.entity.ResultInfo;

import java.util.List;

/**
 * @author 李迪
 * @date 2020/3/12 14:43
 */
public interface RecordService {

    /**
     * 向数据库提交数据
     * @param phoneNumber
     * @param records
     * @return
     * @throws Exception
     */
    public ResultInfo<String> uploadRecords(String phoneNumber, List<Record> records ) throws Exception;

    /**
     * 从服务器中查询对应用户的所有记录
     * @param phoneNumber
     * @return
     * @throws Exception
     */
    public ResultInfo<List<Record>> downloadRecords(String phoneNumber) throws Exception;

}

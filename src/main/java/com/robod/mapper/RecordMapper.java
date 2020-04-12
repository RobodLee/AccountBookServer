package com.robod.mapper;

import com.robod.entity.Record;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 李迪
 * @date 2020/3/12 14:43
 */
@Mapper
@Repository("recordMapper")
public interface RecordMapper {

    /**
     * 添加一条记录
     * @param phoneNumber
     * @param record
     * @throws  Exception
     */
    @Insert("insert into record(id,phoneNumber,category,content,money,status,date) " +
            "values (#{record.uuid},#{phoneNumber},#{record.category},#{record.content},#{record.money},0,#{record.date})")
    void addRecord(@Param("phoneNumber") String phoneNumber,@Param("record") Record record) throws Exception;

    /**
     * 根据手机号码查询该用户下所有的信息
     * @param phoneNumber
     * @throws Exception
     * @return
     */
    @Select("SELECT * FROM record WHERE phoneNumber = #{phoneNumber}")
    @Results({
            @Result(property = "uuid",column = "id",id = true),
            @Result(property = "category",column = "category"),
            @Result(property = "content",column = "content"),
            @Result(property = "money",column = "money"),
            @Result(property = "status",column = "status"),
            @Result(property = "date",column = "date")
    })
    List<Record> findAllByPhoneNumber(String phoneNumber) throws Exception;

    /**
     * 删除指定的记录
     * @param recordId
     * @throws Exception
     */
    @Delete("DELETE FROM record WHERE id = #{recordId}")
    void deleteRecord(@Param("recordId")String recordId) throws Exception;

    /**
     * 修改服务器中的记录
     * @param record
     * @throws Exception
     */
    @Update("UPDATE record SET category=#{category},content=#{content},money=#{money},status=#{status},date=#{dateString} WHERE id = #{uuid}")
    void upgradeRecord(Record record) throws Exception;
}

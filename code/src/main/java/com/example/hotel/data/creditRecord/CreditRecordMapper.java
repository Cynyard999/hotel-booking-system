package com.example.hotel.data.creditRecord;

import com.example.hotel.po.CreditRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Czh
 * @Date: 2020-05-27
 */
@Mapper
@Repository
public interface CreditRecordMapper {

    List<CreditRecord> getUserCreditRecord(@Param("userid") int userid);

    void addCreditRecord(CreditRecord creditRecord);
}

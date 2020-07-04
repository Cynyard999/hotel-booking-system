package com.example.hotel.bl.creditRecord;

import com.example.hotel.po.CreditRecord;
import com.example.hotel.vo.CreditRecordVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @Author: Czh
 * @Date: 2020-05-27
 */
public interface CreditRecordService {

    /**
     * 获取用户所有信用值变更记录
     * @Param userid 用户id值
     * @return 返回信用值变更记录列表
     */
    List<CreditRecord> getUserCreditRecord(int userid);

    /**
     * 添加信用值变更记录
     * @Param creditRecord 一条信用值变更记录信息
     * @return 返回添加结果，如果成功返回true，失败则返回失败信息
     */
    ResponseVO addCreditRecord(CreditRecordVO creditRecordVO);

}

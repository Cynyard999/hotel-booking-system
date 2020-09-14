package com.example.hotel.bl.creditRecord;

import com.example.hotel.po.CreditRecord;
import com.example.hotel.vo.CreditRecordVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface CreditRecordService {

    /**
     * 获取用户所有信用值变更记录
     * @Param userid
     * @return
     */
    List<CreditRecord> getUserCreditRecord(int userid);

    /**
     * 添加信用值变更记录
     * @Param creditRecord
     * @return
     */
    ResponseVO addCreditRecord(CreditRecordVO creditRecordVO);

}

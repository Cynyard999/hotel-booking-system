package com.example.hotel.blImpl.creditRecord;

import com.example.hotel.bl.creditRecord.CreditRecordService;
import com.example.hotel.data.creditRecord.CreditRecordMapper;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.vo.CreditRecordVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lxz
 * @Date: 2020-05-27
 */
@Service
public class CreditRecordServiceImpl implements CreditRecordService {
    private final static String ADD_ERROR = "添加信用值变更记录失败";

    @Autowired
    CreditRecordMapper creditRecordMapper;

    @Override
    public List<CreditRecord> getUserCreditRecord(int userid){
        return creditRecordMapper.getUserCreditRecord(userid);
    }

    @Override
    public ResponseVO addCreditRecord(CreditRecordVO creditRecordVO){
        //creditRecord,VO->PO
        CreditRecord creditRecord = new CreditRecord();
        BeanUtils.copyProperties(creditRecordVO,creditRecord);
        //添加信用值变更记录，如果成功便返回true，否则返回添加信用值变更记录失败
        try{
            creditRecordMapper.addCreditRecord(creditRecord);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(ADD_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
}

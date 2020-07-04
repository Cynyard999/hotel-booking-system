package com.example.hotel.blImpl.user;

import com.example.hotel.bl.creditRecord.CreditRecordService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.creditRecord.CreditRecordMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author: Qxy
 * @Date: 2020-05-25
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final static String UPDATE_ERROR = "修改失败";
    private final static String REGISTER_ERROR = "注册失败";
    private final static String CHARGE_ERROR = "此类型用户无法充值信用值";
    private final static String EMAIL_EXIST = "邮箱已被注册";
    private final static String PHONE_EXIST = "手机号已被注册";
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private CreditRecordService creditRecordService;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        //注册用户，检查邮箱和手机号是否已注册
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        user.setUserType(UserType.Client);
        user.setUserName(userVO.getUserName());
        if(checkEmail(user.getEmail())){return ResponseVO.buildSuccess(EMAIL_EXIST);}
        if(checkPhone(user.getPhoneNumber())){return ResponseVO.buildSuccess(PHONE_EXIST);}
        try {
            accountMapper.createNewAccount(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getEmail());
        if (user == null || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }

    @Override
    public UserVO getUserInfo(int id) {
        User user = accountMapper.getAccountById(id);
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }


    @Override
    public Vip getVipInfo(int id) {
        Vip vip = accountMapper.getVipInfoById(id);
        if (vip == null) {
            vip = new Vip();
            vip.setVipType(0);
        }
        return vip;
    }


    @Override
    public ResponseVO updateUserInfo(int id, String password, String username, String phonenumber) {
        try {
            accountMapper.updateAccount(id, password, username, phonenumber);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateUserCredit(int id, double credit){
        try{
            accountMapper.updateCredit(id, credit);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(CHARGE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateUserCreditByEmail(String email, double credit){
        //通过邮箱充值信用值，并记录信用值变更
        User user = accountMapper.getAccountByName(email);
        if(!user.getUserType().equals(UserType.Client)){
            return ResponseVO.buildFailure(CHARGE_ERROR);
        }
        accountMapper.updateCredit(user.getId(), credit);
        CreditRecordVO creditRecord = new CreditRecordVO();
        creditRecord.setUserId(user.getId());
        creditRecord.setChangeAction("信用值充值");
        creditRecord.setChangeValue(credit);
        creditRecord.setFinalValue(accountMapper.getAccountByName(email).getCredit());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        creditRecord.setChangeTime(sf.format(new Date(System.currentTimeMillis())));
        creditRecordService.addCreditRecord(creditRecord);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO registerPersonalVIP(VipPersonVO vipPersonVO) {
        //注册个人会员
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            Vip vip = new Vip();
            vip.setBirthday(vipPersonVO.getBirthday());
            vip.setRegisterDate(curdate);
            vip.setUserId(vipPersonVO.getUserId());
            vip.setVipType(1);
            accountMapper.registerPersonalVIP(vip);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(REGISTER_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO registerCompanyVIP(VipCompanyVO vipCompanyVO) {
        //注册企业会员
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            Vip vip = new Vip();
            vip.setCompanyName(vipCompanyVO.getCompanyName());
            vip.setRegisterDate(curdate);
            vip.setUserId(vipCompanyVO.getUserId());
            vip.setVipType(2);
            accountMapper.registerCompanyVIP(vip);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(REGISTER_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public boolean checkEmail(String email){
        return accountMapper.checkEmail(email);
    }

    @Override
    public boolean checkPhone(String phoneNumber){
        return accountMapper.checkPhone(phoneNumber);
    }
}

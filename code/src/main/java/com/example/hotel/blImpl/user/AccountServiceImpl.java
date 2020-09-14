package com.example.hotel.blImpl.user;

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
    private CreditRecordMapper creditRecordMapper;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        if(checkEmail(user.getEmail())){return ResponseVO.buildSuccess(EMAIL_EXIST);}
        if(checkPhone(user.getPhoneNumber())){return ResponseVO.buildSuccess(PHONE_EXIST);}
        try {
            accountMapper.createNewAccount(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(123);
    }

    @Override
    public User login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getEmail());

        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public User getUserInfo(int id) {
        User user = accountMapper.getAccountById(id);
        if (user == null) {
            return null;
        }
        return user;
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
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateUserCredit(int id, double credit){
        try{
            accountMapper.updateCredit(id, credit);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(CHARGE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateUserCreditByEmail(String email, double credit){
        User user = accountMapper.getAccountByName(email);
        if(!user.getUserType().equals(UserType.Client)){
            return ResponseVO.buildFailure(CHARGE_ERROR);
        }
        accountMapper.updateCredit(user.getId(), credit);
        CreditRecord creditRecord = new CreditRecord();
        creditRecord.setUserId(user.getId());
        creditRecord.setChangeAction("信用值充值");
        creditRecord.setChangeValue(credit);
        creditRecord.setFinalValue(accountMapper.getAccountByName(email).getCredit());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        creditRecord.setChangeTime(sf.format(new Date(System.currentTimeMillis())));
        creditRecordMapper.addCreditRecord(creditRecord);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO registerPersonalVIP(VipPersonVO vipPersonVO) {
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

package com.example.hotel.blImpl.admin;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class AdminServiceImpl implements AdminService {
    private final static String EMAIL_EXIST = "邮箱已被注册";
    private final static String PHONE_EXIST = "手机号已被注册";
    private final static String ACCOUNT_ADD_FAIL = "账号添加失败";
    private final static String UPDATE_FALSE = "更新失败";
    private final static String HOTEL_ADD_FAIL = "酒店添加失败";
    private final static String UPLOAD_FAIL = "图片上传失败";

    private final static String endpoint = "oss-cn-beijing.aliyuncs.com";
    private final static String DELETE_HOTEL_FAIL = "删除酒店失败";
    private final static String DELETE_USER_FAIL = "删除用户失败";

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    AccountService accountService;

    @Override
    public ResponseVO addUser(UserVO userVO) {
        //user,VO->PO
        User user = new User();
        user.setEmail(userVO.getEmail());
        user.setPassword(userVO.getPassword());
        user.setUserType(userVO.getUserType());
        user.setPhoneNumber(userVO.getPhoneNumber());
        user.setUserName(userVO.getUserName());
        //邮箱已存在
        if(accountService.checkEmail(userVO.getEmail())){return ResponseVO.buildFailure(EMAIL_EXIST);}
        //手机号已被注册
        if(accountService.checkPhone(userVO.getPhoneNumber())){return ResponseVO.buildFailure(PHONE_EXIST);}
        //添加用户，如果成功便返回对应用户id，否则返回用户添加失败
        try {
            adminMapper.addUser(user);
            return ResponseVO.buildSuccess(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(ACCOUNT_ADD_FAIL);
        }
    }

    @Override
    public List<UserVO> getUserList(String type) {
        UserVO tempUserVO;
        List<User> users = adminMapper.getUserList(type);
        List<UserVO> userVOS = new ArrayList<>();
        //user,PO->VO
        for (User user : users) {
            tempUserVO = new UserVO();
            tempUserVO.setId(user.getId());
            tempUserVO.setEmail(user.getEmail());
            tempUserVO.setPhoneNumber(user.getPhoneNumber());
            tempUserVO.setUserName(user.getUserName());
            tempUserVO.setUserType(user.getUserType());
            tempUserVO.setCredit(user.getCredit());
            userVOS.add(tempUserVO);
        }
        return userVOS;
    }

    @Override
    public ResponseVO updateUserInfo(UserVO userVO){
        //user,VO->PO
        User user = new User();
        user.setEmail(userVO.getEmail());
        user.setId(userVO.getId());
        user.setPhoneNumber(userVO.getPhoneNumber());
        user.setUserName(userVO.getUserName());
        user.setPassword(userVO.getPassword());
        //添加用户，成功则返回true，失败则返回更新失败
        try{
            adminMapper.updateUserInfo(user);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(UPDATE_FALSE);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO addHotel(HotelVO hotelVO){
        //hotel.VO->PO
        Hotel hotel = new Hotel();
        hotel.setRate(hotelVO.getRate());
        hotel.setAddress(hotelVO.getAddress());
        hotel.setBizRegion(hotelVO.getBizRegion());
        hotel.setDescription(hotelVO.getDescription());
        hotel.setHotelName(hotelVO.getHotelName());
        hotel.setManagerId(hotelVO.getManagerId());
        hotel.setPhoneNum(hotelVO.getPhoneNum());
        hotel.setHotelStar(hotelVO.getHotelStar());
        hotel.setDetail(hotelVO.getDetail());
        //添加酒店，成功则返回true，失败则返回酒店添加失败
        try{
            adminMapper.addHotel(hotel);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(HOTEL_ADD_FAIL);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateManagerOfHotel(HotelVO hotelVO){
        //hotel.VO->PO
        Hotel hotel = new Hotel();
        hotel.setId(hotelVO.getId());
        hotel.setManagerId(hotelVO.getManagerId());
        //更新相应酒店的工作人员，成功则返回true，失败则返回更新失败
        try{
            adminMapper.updateManagerOfHotel(hotel);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(UPDATE_FALSE);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public UserVO getUserInfo(int id){
        User user = adminMapper.getUserInfo(id);
        //user,PO->VO
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setEmail(user.getEmail());
        userVO.setCredit(user.getCredit());
        userVO.setPhoneNumber(user.getPhoneNumber());
        userVO.setUserName(user.getUserName());
        return userVO;
    }

    @Override
    public ResponseVO uploadFile(String hotelId, MultipartFile file){
        //将文件暂存在本地
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        String newFileName = "fixedName"+ suffix;
        File temp = new File("test");
        String path = temp.getAbsolutePath();
        path=path.substring(0,path.length()-4);
        File newFile = new File( path+newFileName);
        //上传到OSS存储，成功则返回true，失败则返回上传失败
        try {
            file.transferTo(newFile);
            uploadOSS(hotelId,newFile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(UPLOAD_FAIL);
        }
        //删除本地暂存文件
        newFile.delete();
        return ResponseVO.buildSuccess(true);

    }

    private void uploadOSS(String hotelId, File file){
        //调用OSS服务，上传到阿里云对象存储
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String fileKey = hotelId;
        ossClient.putObject(bucketName,fileKey,file);
    }

    @Override
    public ResponseVO deleteHotel(int hotelId){
        //首先检查对应酒店有无酒店工作人员
        Hotel hotel = adminMapper.getHotelInfo(hotelId);
        if(hotel.getManagerId()!= null){
            //若有则先删除酒店工作人员再删除酒店。成功则返回true，失败则返回删除酒店失败
            int managerId = hotel.getManagerId();
            try{
                adminMapper.deleteUser(managerId);
                adminMapper.deleteHotel(hotelId);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseVO.buildFailure(DELETE_HOTEL_FAIL);
            }
        }else {
            //若无则直接删除酒店，成功则返回true，失败则返回删除酒店失败
            try {
                adminMapper.deleteHotel(hotelId);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseVO.buildFailure(DELETE_HOTEL_FAIL);
            }
        }
        return ResponseVO.buildSuccess(true);
    }


    @Override
    public ResponseVO deleteUser(int userId){
        //删除用户，成功则返回true，失败则返回删除用户失败
        try{
            adminMapper.deleteUser(userId);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(DELETE_USER_FAIL);
        }
        return ResponseVO.buildSuccess(true);
    }

}

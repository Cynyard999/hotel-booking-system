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
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String UPDATE_FALSE = "更新失败";
    private final static String ADD_Fall = "酒店添加失败";
    private final static String EMAIL_EXIST = "邮箱已被注册";
    private final static String PHONE_EXIST = "手机号已被注册";
    private final static String accessKeyId = "LTAI4G4F2DHQ3qZGEK9xVn1D";
    private final static String accessKeySecret = "jFaOMD78Lxx0CMdXBUgQx5p5PR8tFZ";
    private final static String bucketName  = "hotel-booking-system";
    private final static String endpoint = "oss-cn-beijing.aliyuncs.com";
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    AccountService accountService;
    @Override
    public ResponseVO addUser(UserVO userVO) {
        User user = new User();
        user.setEmail(userVO.getEmail());
        user.setPassword(userVO.getPassword());
        user.setUserType(userVO.getUserType());
        user.setPhoneNumber(userVO.getPhoneNumber());
        user.setUserName(userVO.getUserName());
        if(accountService.checkEmail(userVO.getEmail())){return ResponseVO.buildFailure(EMAIL_EXIST);}
        if(accountService.checkPhone(userVO.getPhoneNumber())){return ResponseVO.buildFailure(PHONE_EXIST);}
        try {
            adminMapper.addUser(user);
//            System.out.println("用户id");
//            System.out.println(user.getId());
            return ResponseVO.buildSuccess(user.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }

    }

    @Override
    public List<UserVO> getUserList(String type) {
        UserVO tempUserVO = new UserVO();
        List<User> users = adminMapper.getUserList(type);
        List<UserVO> userVOS = new ArrayList<>();
        for(int i=0;i<users.size();i++){
            tempUserVO = new UserVO();
            tempUserVO.setId(users.get(i).getId());
            tempUserVO.setEmail(users.get(i).getEmail());
            tempUserVO.setPhoneNumber(users.get(i).getPhoneNumber());
            tempUserVO.setUserName(users.get(i).getUserName());
            tempUserVO.setUserType(users.get(i).getUserType());
            tempUserVO.setCredit(users.get(i).getCredit());
            userVOS.add(tempUserVO);
        }
        return userVOS;
    }

    @Override
    public ResponseVO updateUserInfo(UserVO userVO){
        User user = new User();
        user.setEmail(userVO.getEmail());
        user.setId(userVO.getId());
        user.setPhoneNumber(userVO.getPhoneNumber());
        user.setUserName(userVO.getUserName());
        user.setPassword(userVO.getPassword());
        try{
            adminMapper.updateUserInfo(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_FALSE);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    /**
     * 添加酒店
     * @param hotelVO
     * @return
     */
    public ResponseVO addHotel(HotelVO hotelVO){
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
        try{
            adminMapper.addHotel(hotel);
        }catch (Exception e){
            System.out.println("添加酒店有问题？");
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ADD_Fall);
        }
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public ResponseVO updateHotel(HotelVO hotelVO){
        Hotel hotel = new Hotel();
        hotel.setId(hotelVO.getId());
        hotel.setManagerId(hotelVO.getManagerId());
        try{
            adminMapper.updateHotel(hotel);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_FALSE);
        }
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public ResponseVO getUserInfo(int id){
        User user = adminMapper.getUserInfo(id);
        if(user==null)return ResponseVO.buildFailure("查询失败");
        UserVO result = new UserVO();
        result.setId(user.getId());
        result.setEmail(user.getEmail());
        result.setCredit(user.getCredit());
        result.setPhoneNumber(user.getPhoneNumber());
        result.setUserName(user.getUserName());
        return ResponseVO.buildSuccess(result);
    }

    @Override
    public ResponseVO uploadFile(String hotelId, MultipartFile file){
//        System.out.println("调了");
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        String newFileName = "fixedName"+ suffix;
//        System.out.println(newFileName);
        File temp = new File("test");

        String path = temp.getAbsolutePath();
        path=path.substring(0,path.length()-4);
        //System.out.println(path);
        System.out.println(hotelId);
        File newFile = new File( path+newFileName);
        try {
            file.transferTo(newFile);
            uploadOSS(hotelId,newFile);
            System.out.println("成功");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
        newFile.delete();
        return ResponseVO.buildSuccess("成功");

    }

    public void uploadOSS(String hotelId,File file){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String fileKey = hotelId;
        ossClient.putObject(bucketName,fileKey,file);

    }

    @Override
    public ResponseVO deleteHotel(int hotelId){
        Hotel hotel = adminMapper.getHotelInfo(hotelId);
        if(hotel.getManagerId()!= null){
            int managerId = hotel.getManagerId();
            try{
                adminMapper.deleteUser(managerId);
                adminMapper.deleteHotel(hotelId);
                return ResponseVO.buildSuccess();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else {
            try {
                adminMapper.deleteHotel(hotelId);
                return ResponseVO.buildSuccess();
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
        }
        return ResponseVO.buildFailure("删除酒店失败了");
    }


    @Override
    public ResponseVO deleteUser(int userId){
        try{
            adminMapper.deleteUser(userId);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
    return ResponseVO.buildFailure("删除用户失败");
    }


}

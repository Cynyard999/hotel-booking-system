package com.example.hotel.blImpl.favorite;

import com.example.hotel.bl.favorite.FavoriteService;
import com.example.hotel.data.favorite.FavoriteMapper;
import com.example.hotel.vo.HotelVO;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FavoriteServiceImplTest extends TestCase {

  @InjectMocks
  //这个必须要是一个类才能Inject不能是接口
  private FavoriteService favoriteService = new FavoriteServiceImpl();
  @Mock
  private FavoriteMapper favoriteMapper;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testFavorHotel() {
    doNothing().when(favoriteMapper).favorHotel(1,1);
    favoriteService.favorHotel(1,1);
    verify(favoriteMapper,times(1)).favorHotel(1,1);
  }

  @Test
  public void testDisfavorHotel() {
    doNothing().when(favoriteMapper).disfavorHotel(1,1);
    favoriteService.disfavorHotel(1,1);
    verify(favoriteMapper,times(1)).disfavorHotel(1,1);
  }

  @Test
  public void testRetrieveFavoriteList() {
    HotelVO hotelVO = new HotelVO();
    hotelVO.setHotelName("南大会议中心");
    hotelVO.setId(1);
    hotelVO.setPhoneNum("111");
    List<HotelVO> hotelVOList = new ArrayList<>();
    hotelVOList.add(hotelVO);
    when(favoriteMapper.retrieveFavoriteList(1)).thenReturn(hotelVOList);
    List<HotelVO> result = favoriteService.retrieveFavoriteList(1);
    assertNotNull(result);
    assertEquals(1,result.size());
    assertEquals("南大会议中心",result.get(0).getHotelName());
    assertEquals("111",result.get(0).getPhoneNum());
  }

  @Test
  public void testIsFavored() {
    when(favoriteMapper.isFavored(1,1)).thenReturn(1);
    when(favoriteMapper.isFavored(1,2)).thenReturn(0);
    int result1 = favoriteService.isFavored(1,1);
    int result2 =favoriteService.isFavored(1,2);
    assertEquals(1,result1);
    assertEquals(0,result2);
  }
}
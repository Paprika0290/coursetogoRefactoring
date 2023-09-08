package com.coursetogo.mapper.map;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coursetogo.dto.course.SearchKeywordDTO;
import com.coursetogo.dto.map.RestaurantDTO;

@Mapper
public interface RestaurantMapper {
//    @Select("SELECT 식품인증업소일련번호, 업소_명, 자치구_코드, 자치구_명, 업태_코드, 업태_명, 지도_Y좌표, 지도_X좌표, 전화번호, 도로명주소 FROM 음식점")
//    @ResultMap("restaurantResultMap")
	
    public List<RestaurantDTO> getRestaurantBySearchKeyword(SearchKeywordDTO searchKeyword);
    public List<RestaurantDTO> getAllRestaurant();
}

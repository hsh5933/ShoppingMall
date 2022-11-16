package com.shop.shop.repository;

import com.shop.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long>, QuerydslPredicateExecutor<Item>,ItemRepositoryCustom {
    //find+(엔티티이름)+By+변수명  엔티티이름 생략가능하다

    //상품명을가지고 상품을 검색하는 예제
    List<Item> findByItemNm(String itemNm);

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    //파라미터로 넘어온 price변수보다 값이 작은 상품데이터를 조회하는 쿼리
    List<Item> findByPriceLessThan(Integer price);

    //OrderBy속성명Desc : 내림차순
    //OrderBy속성명Asc : 올림차순
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    //query어노테이션을 이용하여 상품데이터를 조회한다
    // 상품 상세설명을 파라미터로 받아 해당내용을 포함하고있는
    // 데이터를 조회하고 가격은 높은순으로 조회한다.
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);


    //Query의 nativeQuery사용
    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc",nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);



}

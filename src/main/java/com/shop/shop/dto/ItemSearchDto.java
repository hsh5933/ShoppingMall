package com.shop.shop.dto;

import com.shop.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {
    //현재 시간과 상품등록일을 비교해서 상품 데이터를 조회
    private String searchDateType;

    //상품의 판매상태를 기준으로 상품데이터 조회
    private ItemSellStatus searchSellStatus;

    //상품을 조회할때 어떤 유형으로 조회할지 선택
    private String searchBy;

    //조회할 검색어 저장할 변수. searchBy가 itemNm일경우 상품명
    //createdBy일경우 상품 등록자 아이디를 기준으로 검색
    private String searchQuery ="";
}

package com.shop.shop.entity;

import com.shop.shop.constant.ItemSellStatus;
import com.shop.shop.dto.ItemFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity{
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //상품코드

    @Column(nullable = false, length = 50)
    private String itemNm; //상품명

    @Column(name = "price", nullable = false)
    private int price; //상품 가격

    @Column(nullable = false)
    private int stockNumber; //상품 재고수량

    @Lob
    /*사이즈가 큰 데이터를 저장하기위한 어노테이션
    * 문자형 대용량파일이나 이미지 사운드 비디오같은 데이터다룰때*/
    @Column(nullable = false)
    private String itemDetail; //상품 상세설명

    //enum클래스에 등록한 상품상태 가져옴
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //상품 판매 상태

//    private LocalDateTime regTime; //상품 등록시간
//
//    private LocalDateTime updateTime; //상품 수정시간

    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

}

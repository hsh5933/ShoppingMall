package com.shop.shop.controller;

import com.shop.shop.dto.ItemFormDto;
import com.shop.shop.dto.ItemSearchDto;
import com.shop.shop.entity.Item;
import com.shop.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    //상품등록페이지
    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto",new ItemFormDto());
        return "/item/itemForm";
    }

    //상품등록
    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList){
        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId()==null){
            model.addAttribute("errorMessage","첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try{
            itemService.saveItem(itemFormDto,itemImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage","상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }
        return "redirect:/";
    }

    //상품 수정페이지
    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){
        try{
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto",itemFormDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto",new ItemFormDto());
            return "item/itemForm";
        }
        return "item/itemForm";
    }

    //상품수정
    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto,
                             BindingResult bindingResult, @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList, Model model){

        //에러가있다면 itemForm페이지로 리턴
        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }
        //만약 첫번째이미지가 비어있거나 상품등록아이디가 없는경우
        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId()==null){
            model.addAttribute("errorMessage","첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try{
            itemService.updateItem(itemFormDto,itemImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage","상품 수정 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/"; //수정이끝나게되면 첫번째화면으로 리다이렉트
    }

    //value에 상품 관리화면 진입시 페이지 번호없는경우, 있는경우 2가지 매핑
    @GetMapping(value = {"/admin/items","/admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto,
                             @PathVariable("page") Optional<Integer> page, Model model){
        //페이징을위해 PageRequesst.of 메소드를 통해 pageable객체생성
        //첫번째파라미터 페이지번호, 두번째파라미터 가져올 데이터수
        //url경로에 페이지번호가 있으면 해당 페이지를 조회하도록 세팅
        //페이지 번호가 없으면 0페이지를 조회하도록함.
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get():0,3);

        //조회조건과 페이징 정보를 파라미터로 넘겨서 객체반환
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto,pageable);
        //조회한 상품 데이터 및 페이징 정보를 뷰에 전달
        model.addAttribute("items",items);
        //페이지 전환시 기존 검색조건을 유지한채 이동할수있게 뷰에 전달
        model.addAttribute("itemSearchDto",itemSearchDto);
        //상품관리 하단 메뉴에 보여줄 페이지 번호의 최대갯수
        model.addAttribute("maxPage",5);
        return "item/itemMng";
    }
}

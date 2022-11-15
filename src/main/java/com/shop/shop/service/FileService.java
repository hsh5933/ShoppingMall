package com.shop.shop.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName,
                             byte[] fileData) throws Exception{
        //서로 다른 개체들을 구별하기위해서 이름부여할때 사용
        //파일명 중복문제 해결
        UUID uuid = UUID.randomUUID();
        //파일의 확장자 추출
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        log.info(extension.toString());
        //uuid이름과 확장자 조합해서 저장될파일이름저장
        String savedFileName = uuid.toString()+extension;
        String fileUploadFullUrl = uploadPath+"/"+savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);
        if(deleteFile.exists()){
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        }else{
            log.info("파일이 존재하지 않습니다.");
        }
    }

}

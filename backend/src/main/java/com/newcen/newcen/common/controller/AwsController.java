package com.newcen.newcen.common.controller;


import com.newcen.newcen.common.service.AwsS3Service;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController     // controller + @ResponseBody --> JSON/XML 형태로 객체 데이터 반환 목적
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/aws")
public class AwsController {


    private final AwsS3Service awsS3Service;
    @GetMapping("/files/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String fileName) throws IOException {
        ByteArrayOutputStream downloadInputStream = awsS3Service.downloadFile(fileName);
        return ResponseEntity.ok()
                .contentType(contentType(fileName))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(downloadInputStream.toByteArray());
//        return awsS3Service.getObject(fileName);
    }

    private MediaType contentType(String filename) {
        String[] fileArrSplit = filename.split("\\.");
        String fileExtension = fileArrSplit[fileArrSplit.length - 1];
        switch (fileExtension) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

//    @ApiOperation(value = "Amazon S3에 업로드 된 파일을 삭제", notes = "Amazon S3에 업로드된 파일 삭제")
//    @DeleteMapping("/files/{fileName}")
//    public ResponseEntity<?> deleteFile(@ApiParam(value="파일 하나 삭제", required = true) @PathVariable String fileName) {
//        awsS3Service.deleteFile(fileName);
//        return ResponseEntity
//                .ok()
//                .body(true);
//    }
}

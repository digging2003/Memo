package com.digging.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileManager {

	public static final String FILE_UPLOAD_PATH = "E:\\kamii\\project\\upload\\memo";
	
	// 파일 저장 기능
	public static String saveFile(int userId, MultipartFile file) {
		
		if(file == null) {
			return null;
		}
		
		// 파일 이름 유지
		// 폴더를 생성해서 파일을 저장
		// 사용자 정보를 폴더 이름으로 사용 한다
		// 시간 정보를 포함
		// UNIX TIME : 1970년 1월 1일 0시 0분 0초 부터 흐른 시간을 milli second(1/1000) 단위로 표현한 값
		// ex) 5_2390458231
		
		String directoryName = "/" + userId + "_" + System.currentTimeMillis();
		
		// 디렉토리 (폴더) 만들기
		String directoryPath = FILE_UPLOAD_PATH + directoryName;
		
		File directory = new File(directoryPath);
		
		if(!directory.mkdir()) {
			// 디렉토리 생성 실패
			return null;
		}
		
		// 파일 저장
		String filePath = directoryPath + "/" + file.getOriginalFilename();
		
		try {
			byte[] bytes = file.getBytes();
			
			Path path = Paths.get(filePath);
			Files.write(path, bytes);
			
		} catch (IOException e) {

			e.printStackTrace();
			
			// 파일 저장 실패
			return null;
		}
		
		// 실제 파일 저장 위치와 url 경로를 매칭하는 규칙
		// E:\\kamii\\project\\upload\\memo
		// /images/ 
		
		// E:\\kamii\\project\\upload\\memo/5_2342341/test.png
		// /images/5_2342341/test.png
		
		return "/images" + directoryName + "/" + file.getOriginalFilename();
		
		
		
	}
	
}

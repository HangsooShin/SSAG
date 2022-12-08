package com.ssag.controller;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssag.model.ImageVo;
import com.ssag.model.UserVo;
import com.ssag.service.ImageService;
import com.ssag.service.UserService;

@Controller
public class ImageController {

	@Autowired
	public UserService userService;

	@Autowired
	public ImageService imageService;

	@GetMapping("/upload")
	public String imageInset() {
		return "imageUpload";
	}

	// 분석하기 - HashMap 사용 (JSON 리턴받기), 이미지 저장
	@PostMapping(value = "/upload")
	@ResponseBody
	public HashMap<String, Object> analysisImage(@RequestParam("file") MultipartFile file,
			@AuthenticationPrincipal UserVo userVo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		System.out.println("??username" + userVo.getUsername());
		// 로그인한 사용자의 username을 가져와서 id(int)를 얻는다.
		String username = userVo.getUsername();
		String id = userService.findById(username).getUsername();
		System.out.println("===========================" + username + id);
		System.out.println("=====================" + file);

		if (!file.isEmpty()) { // 업로드할 파일이 존재할 경우에만
//		        String sourceFileName = files.getOriginalFilename(); 
//		        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase(); 
			File destinationFile;
			String destinationFileName;
			// 경로
			String fileUrl = "E:/Project/images/trash/" + id + "/"; // 아이디 번호로 폴더 생성

			do {
				// 이미지 이름
				destinationFileName = RandomStringUtils.randomAlphanumeric(16) + "_" + id + ".png";
//		                    RandomStringUtils.randomAlphanumeric(16) + "_" + id + "." + sourceFileNameExtension; 
//	        	            RandomStringUtils.randomAlphanumeric(16) + "_" + id + ".png"; 
				destinationFile = new File(fileUrl + destinationFileName);
			} while (destinationFile.exists());

			// destinationFile 경로로 폴더 생성
			destinationFile.getParentFile().mkdirs();
			file.transferTo(destinationFile);

			String imgUrl = fileUrl + destinationFileName; // 이미지 full_path
			System.out.println(imgUrl);

//	            // Django로 데이터 보내기
			ImageVo response = imageService.getFirstTodoTest(imgUrl);
//	            System.out.println("=================================");
//	            System.out.println(response.getImage());
//	            System.out.println(response.getSmall_category());
//	            System.out.println(response.getBig());
//	            System.out.println(response.getQty());
//
			
//	            map.put("image", response.getImage());
//	            map.put("small_category", response.getSmall_category());
//	            map.put("big", response.getBig());
//	            map.put("qty", response.getQty());
//			map.put("image", response.getImage());
//			map.put("code", response.getCode());
//			map.put("path", response.getPath());

			
//		    

		}
		return map; // 스프링이 자동으로 JSON타입으로 반환해서 전달한다.
	}
}

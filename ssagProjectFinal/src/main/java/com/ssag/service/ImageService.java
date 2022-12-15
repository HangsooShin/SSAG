package com.ssag.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssag.model.ImageVo;

@Service
public class ImageService {

	private final WebClient webClient;

	public ImageService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:8090").build();
	}
	
	// 분석
	public ImageVo getFirstTodoTest(String imgUrl) {
		
		return this.webClient
				.get()
				.uri(uriBuilder -> uriBuilder
					.path("/noticeboard/response/")
					.queryParam("imgUrl", imgUrl)
		            .build())
				.retrieve()
				.bodyToMono(ImageVo.class)
				.block();
	}
	
	
}

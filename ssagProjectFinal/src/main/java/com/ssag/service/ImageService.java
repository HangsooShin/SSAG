package com.ssag.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssag.model.ImageVo;

@Service
public class ImageService {

	private final WebClient webClient;

	public ImageService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:8000").build();
	}
	
	// 분석
	public ImageVo dlprocess(String imgUrl) {
		return this.webClient
				.get()
				.uri(uriBuilder -> uriBuilder
					.path("/testDL/deeplearningprocess/")
					.queryParam("imgUrl", imgUrl)
		            .build())
				.retrieve()
				.bodyToMono(ImageVo.class)
				.block();
	}
	
	public ImageVo rcprocess(String imgUrl) {
		return this.webClient
				.get()
				.uri(uriBuilder -> uriBuilder
					.path("/testDL/reciptprocess/")
					.queryParam("imgUrl", imgUrl)
		            .build())
				.retrieve()
				.bodyToMono(ImageVo.class)
				.block();
		
	}
	
	public ImageVo bcprocess(String imgUrl) {
		return this.webClient
				.get()
				.uri(uriBuilder -> uriBuilder
					.path("/testDL/barcodeprocess/")
					.queryParam("imgUrl", imgUrl)
		            .build())
				.retrieve()
				.bodyToMono(ImageVo.class)
				.block();
	}
	
	
}

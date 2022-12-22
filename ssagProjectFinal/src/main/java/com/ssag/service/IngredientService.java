package com.ssag.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssag.dao.SearchDao;
import com.ssag.model.CookIngredientListVo;
import com.ssag.model.CookVo;
import com.ssag.model.IngredientVo;
import com.ssag.model.SimilarnameVo;

@Service
public class IngredientService {

	@Autowired
	private SearchDao searchDao;
	
	@Autowired
	private CookIngredientListVo cookIngredientListVo;
	
//	public HashMap<String, ArrayList<SimilarnameVo>> joinDic(String similar) {
//		HashMap<String, ArrayList<IngredientVo>> dic = new HashMap<String, ArrayList<IngredientVo>>();
//
//		List<SimilarnameVo> procedureList = searchDao.recipeProcedureCall(similar);
//		System.out.println("IngredientService KeyIdx : " + procedureList.get(0).getIngredientVo22());
//		// key
//		String key;
//		ArrayList<String> list = new ArrayList<String>();
////		dic.put(key,list)
//		key = procedureList.get(0).getCookVo22().getCookname();
//		for (int i = 0; i < procedureList.size(); i++) {
//			
//			// new list
//			// ArrayList<SimilarnameVo> list = new ArrayList<SimilarnameVo>();
//			if(key.equals(procedureList.get(i).getCookVo22().getCookname())) {
//				list.add(procedureList.get(i).getCookVo22().getCookname());
//				
//			}else {
//				// map 생성
//				key = procedureList.get(i).getCookVo22().getCookname());
//			}
////			list = dic.get(key);
////			list.add(procedureList.get(i).getIngredientVo22());
//			list.add(procedureList.get(i).getIngredientVo22().getIngredientname());
//			if (dic.containsKey(key)) {
//				// 이미 해당 key가 맵에 있을 때에는 value를 불러온 뒤 넣음
//				list = dic.get(key);
////				list.add(procedureList.get(i).getIngredientVo22());
//				list.add(procedureList.get(i).getIngredientVo22().getIngredientname());
//			} else {
//				// 없으면 새로운 리스트를 만들어서 입력
//				list = new ArrayList<IngredientVo>();
//				list.add(procedureList.get(i));
//			}
//			dic.put(key, list);
//		}
//
//		System.out.println("===========요리별 재료모음===========");
//		for (String key5 : dic.keySet()) {
//			System.out.println(key5 + " => " + dic.get(key5));
//		}
//		return dic;
//
//	}
	
	
	public List<CookIngredientListVo> joinDic(String similar) {
		
		
		
		List<CookIngredientListVo> list = new ArrayList<CookIngredientListVo>();
		
		List<SimilarnameVo> procedureList = searchDao.recipeProcedureCall(similar);
		
		CookIngredientListVo cookIngredientListVo22 = new CookIngredientListVo();
		
		String key = procedureList.get(0).getCookVo22().getCookname();
		cookIngredientListVo22.setCookVo(procedureList.get(0).getCookVo22());
		List<IngredientVo> IngreList = new ArrayList<IngredientVo>();
		
		for(int i=0; i<procedureList.size(); i++) {
			
			if(procedureList.get(i).getCookVo22().getCookname().equals(key)) {
				IngreList.add(procedureList.get(i).getIngredientVo22());
			}else {
				cookIngredientListVo22.setIngredientVoList(IngreList);
				list.add(cookIngredientListVo22);
				
				IngreList = new ArrayList<IngredientVo>();
				cookIngredientListVo22 = new CookIngredientListVo();
				key = procedureList.get(i).getCookVo22().getCookname();
				cookIngredientListVo22.setCookVo(procedureList.get(i).getCookVo22());
				IngreList.add(procedureList.get(i).getIngredientVo22());
			}
			System.out.println("===========요리별 재료모음===========");
				System.out.println("key5======================= + " + list);
//			}
		}
		cookIngredientListVo22.setIngredientVoList(IngreList);
		list.add(cookIngredientListVo22);
		return list;
	}
}
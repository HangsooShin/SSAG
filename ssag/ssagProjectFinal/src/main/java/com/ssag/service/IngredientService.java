package com.ssag.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssag.dao.SearchDao;
import com.ssag.model.CookIngredientListVo;
import com.ssag.model.CookbasketListVo;
import com.ssag.model.CookbasketVo;
import com.ssag.model.IngredientVo;
import com.ssag.model.IngredientbasketVo;
import com.ssag.model.SimilarnameVo;

@Service
public class IngredientService {

	@Autowired
	private SearchDao searchDao;
	
	@Autowired
	private CookIngredientListVo cookIngredientListVo;	
	
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
	
	public List<CookIngredientListVo> joinDic2(String similar) {
//		System.out.println("joinDic2 진입");
		// 최종 틀
		List<CookIngredientListVo> list = new ArrayList<CookIngredientListVo>();
		
		//가져오는 데이터
		List<SimilarnameVo> procedureList = searchDao.recipeSearchbyname(similar);
		
		//최종 데이터의 한 덩어리
		CookIngredientListVo cookIngredientListVo22 = new CookIngredientListVo();
		
		// 첫번째 덩어리의 키와 CookVo 셋팅, 덩어리에 넣을 리스트 기본 틀 작성 
		String key = procedureList.get(0).getCookVo22().getCookname();
		cookIngredientListVo22.setCookVo(procedureList.get(0).getCookVo22());
		List<IngredientVo> IngreList = new ArrayList<IngredientVo>();
		
		// 가져온 데이터를 한줄씩 분해
		for(int i=0; i<procedureList.size(); i++) {
			// 첫번째 덩어리 cookVo.name(key) 과 한줄의 cookname이 같으면 리스트에 재료 추가
			if(procedureList.get(i).getCookVo22().getCookname().equals(key)) {
				IngreList.add(procedureList.get(i).getIngredientVo22());
			
			// 아니면 여태 모은 리스트를 덩어리에 넣고 덩어리를 최종리스트에 추가
			// 그리고나서 새 리스트 틀 작성, 새덩어리 만들기, 현재 줄의 정보로 키 설정, 덩어리에 CookVo 셋팅, 재료리스트에 현재 재료 추가
			}else {
				cookIngredientListVo22.setIngredientVoList(IngreList);
				list.add(cookIngredientListVo22);
				
				IngreList = new ArrayList<IngredientVo>();
				cookIngredientListVo22 = new CookIngredientListVo();
				key = procedureList.get(i).getCookVo22().getCookname();
				
				cookIngredientListVo22.setCookVo(procedureList.get(i).getCookVo22());
				IngreList.add(procedureList.get(i).getIngredientVo22());
			}
//					System.out.println("===========요리별 재료모음===========" + list);
		}
		cookIngredientListVo22.setIngredientVoList(IngreList);
		list.add(cookIngredientListVo22);
//		System.out.println(list);
		return list;
	}
	List<IngredientbasketVo> ingredientbasketList = new ArrayList<IngredientbasketVo>();
	public List<IngredientbasketVo> updatecookcode(Integer cookquantityinbasket,Integer cookcode, Integer usercode) {
		System.out.println("updatecookcode"+searchDao.findbasket(usercode, cookcode));
		System.out.println("Cookcode : " +cookcode);
		System.out.println("Cookquantity : " +cookquantityinbasket);
		if (searchDao.findbasket(usercode, cookcode).isEmpty()) {
			System.out.println("신규생성");
			searchDao.getcookbasket(cookquantityinbasket, usercode, cookcode);
			
		} else {
			System.out.println("기존 추가");
			searchDao.updatecookbasket(cookquantityinbasket, usercode, cookcode);
		}
		return ingredientbasketList;
	}
	
	
	//장바구니
	public List<CookbasketListVo> cookbasket(Integer usercode) {
		//DB 데이터 가져오기
		System.out.println("날날것"+searchDao.cookbasketlist(usercode));
		List<CookbasketVo> cookbasketdata = searchDao.cookbasketlist(usercode);
		System.out.println("cookbasketdata" + cookbasketdata);
		
		//최종 결과물 틀
		List<CookbasketListVo> list = new ArrayList<CookbasketListVo>();
		
		//한 덩어리 틀
		CookbasketListVo cookbasketListVo = new CookbasketListVo();
		
		//기준키(cookname), 요리수량, CookVo, ingrelist 설정
		String key = cookbasketdata.get(0).getCookVo22().getCookname();
		cookbasketListVo.setCookquantityinbasket(cookbasketdata.get(0).getCookquantityinbasket());
		cookbasketListVo.setCookVo(cookbasketdata.get(0).getCookVo22());
		List<IngredientVo> ingrelist = new ArrayList<IngredientVo>();
		
		System.out.println("for문 진입직전");
		for (int i=0; i<cookbasketdata.size(); i++) {
			if(cookbasketdata.get(i).getCookVo22().getCookname().equals(key)) {
				ingrelist.add(cookbasketdata.get(i).getIngredientVo22());
				
				System.out.println(ingrelist);
			} else {
				cookbasketListVo.setIngredientVoList(ingrelist);
				list.add(cookbasketListVo);
				
				//다음덩어리 새로 설정, 키 설정, 수량 설정, CookVo설정, ingrelist 새로 추가
				cookbasketListVo = new CookbasketListVo();
				key = cookbasketdata.get(i).getCookVo22().getCookname();
				
				cookbasketListVo.setCookquantityinbasket(cookbasketdata.get(i).getCookquantityinbasket());
				cookbasketListVo.setCookVo(cookbasketdata.get(i).getCookVo22());
				ingrelist = new ArrayList<IngredientVo>();
				ingrelist.add(cookbasketdata.get(i).getIngredientVo22());
				
				System.out.println(ingrelist);
			}
		}
		//다하고나면 나머지 채워주기
		cookbasketListVo.setIngredientVoList(ingrelist);
		list.add(cookbasketListVo);
		
		System.out.println(list);
		return list;
	
	}
	
}
package com.ssag.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssag.dao.SearchDao;
import com.ssag.model.CookIngredientListVo;
import com.ssag.model.CookbasketListVo;
import com.ssag.model.CookbasketVo;
import com.ssag.model.IngredientVo;
import com.ssag.model.IngredientbasketVo;
import com.ssag.model.MerchandiseVo;
import com.ssag.model.SimilarnameVo;

@Service
public class IngredientService {

	@Autowired
	private SearchDao searchDao;


	public List<CookIngredientListVo> joinDic(String similar) {
		
		List<CookIngredientListVo> list = new ArrayList<CookIngredientListVo>();

		// 가져오는 데이터
		List<SimilarnameVo> procedureList = searchDao.recipeProcedureCall(similar);

		// 최종 데이터의 한 덩어리
		CookIngredientListVo cookIngredientListVo22 = new CookIngredientListVo();
		if(procedureList.size() !=0 ) {
		// 첫번째 덩어리의 키와 CookVo 셋팅, 덩어리에 넣을 리스트 기본 틀 작성
		String key = procedureList.get(0).getCookVo22().getCookname();
		cookIngredientListVo22.setCookVo(procedureList.get(0).getCookVo22());
		List<IngredientVo> IngreList = new ArrayList<IngredientVo>();

		// 가져온 데이터를 한줄씩 분해
		for (int i = 0; i < procedureList.size(); i++) {
			// 첫번째 덩어리 cookVo.name(key) 과 한줄의 cookname이 같으면 리스트에 재료 추가
			if (procedureList.get(i).getCookVo22().getCookname().equals(key)) {
				IngreList.add(procedureList.get(i).getIngredientVo22());

				// 아니면 여태 모은 리스트를 덩어리에 넣고 덩어리를 최종리스트에 추가
				// 그리고나서 새 리스트 틀 작성, 새덩어리 만들기, 현재 줄의 정보로 키 설정, 덩어리에 CookVo 셋팅, 재료리스트에 현재 재료 추가
			} else {
				cookIngredientListVo22.setIngredientVoList(IngreList);
				list.add(cookIngredientListVo22);

				IngreList = new ArrayList<IngredientVo>();
				cookIngredientListVo22 = new CookIngredientListVo();
				key = procedureList.get(i).getCookVo22().getCookname();

				cookIngredientListVo22.setCookVo(procedureList.get(i).getCookVo22());
				IngreList.add(procedureList.get(i).getIngredientVo22());
			}
//			System.out.println("===========요리별 재료모음===========" + list);
		}
		cookIngredientListVo22.setIngredientVoList(IngreList);
		list.add(cookIngredientListVo22);
//		System.out.println(list);
		}
		return list;
	}

	public List<CookIngredientListVo> joinDic2(String cookname) {
//		System.out.println("joinDic2 진입");
		// 최종 틀
		List<CookIngredientListVo> list = new ArrayList<CookIngredientListVo>();

		// 가져오는 데이터
		List<SimilarnameVo> procedureList = searchDao.recipeSearchbyname(cookname);
		System.out.println("ingredientservice 결과 : " + procedureList);
		// 최종 데이터의 한 덩어리
		CookIngredientListVo cookIngredientListVo22 = new CookIngredientListVo();

		// 첫번째 덩어리의 키와 CookVo 셋팅, 덩어리에 넣을 리스트 기본 틀 작성
		String key = procedureList.get(0).getCookVo22().getCookname();
		cookIngredientListVo22.setCookVo(procedureList.get(0).getCookVo22());
		List<IngredientVo> IngreList = new ArrayList<IngredientVo>();

		// 가져온 데이터를 한줄씩 분해
		for (int i = 0; i < procedureList.size(); i++) {
			// 첫번째 덩어리 cookVo.name(key) 과 한줄의 cookname이 같으면 리스트에 재료 추가
			if (procedureList.get(i).getCookVo22().getCookname().equals(key)) {
				IngreList.add(procedureList.get(i).getIngredientVo22());

				// 아니면 여태 모은 리스트를 덩어리에 넣고 덩어리를 최종리스트에 추가
				// 그리고나서 새 리스트 틀 작성, 새덩어리 만들기, 현재 줄의 정보로 키 설정, 덩어리에 CookVo 셋팅, 재료리스트에 현재 재료 추가
			} else {
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

	public List<IngredientbasketVo> updatecookcode(Integer cookquantityinbasket, Integer usercode, Integer cookcode) {
		System.out.println("updatecookcode" + searchDao.findbasket(usercode, cookcode));
		System.out.println("Cookcode : " + cookcode);
		System.out.println("Cookquantity : " + cookquantityinbasket);

		if (searchDao.findbasket(usercode, cookcode).isEmpty()) {
			System.out.println("신규생성");
			searchDao.getcookbasket(cookquantityinbasket, usercode, cookcode);

		} else {
			System.out.println("기존 추가");
			searchDao.updatecookbasket(cookquantityinbasket, usercode, cookcode);
		}
		return ingredientbasketList;
	}

	// 장바구니
	public List<CookbasketListVo> cookbasket(Integer usercode, String fridgecode) {
		// DB 데이터 가져오기
		List<CookbasketVo> cookbasketdata = searchDao.cookbasketlist(usercode, fridgecode);
//		System.out.println("cookbasketdata" + cookbasketdata);

		// 최종 결과물 틀
		List<CookbasketListVo> list = new ArrayList<CookbasketListVo>();

		// 한 덩어리 틀
		CookbasketListVo cookbasketListVo = new CookbasketListVo();

		// 기준키(cookname), 요리수량, CookVo, ingrelist 설정
		if (cookbasketdata.size() != 0) {
			String key = cookbasketdata.get(0).getCookVo().getCookname();

			cookbasketListVo.setCookquantityinbasket(cookbasketdata.get(0).getCookquantityinbasket());
			cookbasketListVo.setCookVo(cookbasketdata.get(0).getCookVo());
			List<IngredientVo> ingrelist = new ArrayList<IngredientVo>();

			System.out.println("for문 진입직전");
			for (int i = 0; i < cookbasketdata.size(); i++) {
				if (cookbasketdata.get(i).getCookVo().getCookname().equals(key)) {
					ingrelist.add(cookbasketdata.get(i).getIngredientVo());

					System.out.println("1.if"+ingrelist);
				} else {
					cookbasketListVo.setIngredientVoList(ingrelist);
					list.add(cookbasketListVo);

					// 다음덩어리 새로 설정, 키 설정, 수량 설정, CookVo설정, ingrelist 새로 추가
					cookbasketListVo = new CookbasketListVo();
					key = cookbasketdata.get(i).getCookVo().getCookname();

					cookbasketListVo.setCookquantityinbasket(cookbasketdata.get(i).getCookquantityinbasket());
					cookbasketListVo.setCookVo(cookbasketdata.get(i).getCookVo());
					ingrelist = new ArrayList<IngredientVo>();
					ingrelist.add(cookbasketdata.get(i).getIngredientVo());

					System.out.println("2.if"+ingrelist);
				}
			
			}
			// 다하고나면 나머지 채워주기
			cookbasketListVo.setIngredientVoList(ingrelist);
			list.add(cookbasketListVo);

		} else {
			System.out.println("장바구니 데이터 없음");
		}

		System.out.println("3.list"+list);
		return list;
	}

	public void updatecookbasket(Integer cookquantityinbasket, Integer usercode, Integer cookcode) {
		searchDao.updatecookbasket2(cookquantityinbasket, usercode, cookcode);
	}

	public void deletecookbasket(Integer usercode, Integer cookcode) {
		searchDao.deletecookbasket2(usercode, cookcode);
	}

	public List<IngredientVo> ingredientchecklist(Integer usercode, String fridgecode) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("usercode", usercode);
		data.put("fridgecode", fridgecode);
		List<IngredientVo> ingredientchecklist = searchDao.ingredientchecklist(data);
		return ingredientchecklist;
	}

	
	//price
	  public List<MerchandiseVo> ingredientprice(Integer usercode, String fridgecode) {
		  HashMap<String, Object> data = new HashMap<String, Object>();
		  data.put("usercode", usercode);
		  data.put("fridgecode", fridgecode);
		  List<MerchandiseVo> ingredientprice = searchDao.ingredientprice(data);

		  return ingredientprice;
	  }
	
	
}
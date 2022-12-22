const addIngredientBtn = document.getElementById("addIngredientBtn");



function addIngredientBtnHandler() {

  /* <![CDATA[ */
  let ingrelist = [[${ingredientList3}]];
  /* ]]>*/

  // event.preventDefault()
  // 루트 정해주기
  const rootDiv = document.getElementById("addForm");

  // 본격적인 폼 요소 작성
  const newAddForm = document.createElement("form");
  newAddForm.setAttribute("th:action", "@{/fridgebox}");

  // 나오는 내용들의 그리드를 잡아주는 부분(첫번째 col)
  const addDivClassCol = document.createElement("div");
  addDivClassCol.setAttribute("class", "col");

  // 실질적으로 유저들이 볼 수 있는 입력부분(첫번째 입력칸)
  const addDivClassInputGroup = document.createElement("div");
  addDivClassInputGroup.setAttribute("class", "input-group mb-3");

  // label 및 input 태그 작성부분
  const addLabel = document.createElement("label");
  addLabel.setAttribute("class", "input-group-text");
  // 여기에서 말하는 for 어트리뷰트는 참조할 라벨 가능 요소의 id 속성 값을 의미함.
  addLabel.setAttribute("for", "inputGroupFile01");
  // 라벨 안에 들어갈 텍스트 작성
  const productName = document.createTextNode("상품명");
  addLabel.appendChild(productName);

  // 아래의 input 태그 대신 select 태그를 만든다.
  const addSelect = document.createElement("select");
  // addSelect.setAttribute("id", "ingredientlist" + num);
  addSelect.setAttribute("class", "form-select form-select-sm");
  addSelect.setAttribute("size", "1");
  addSelect.setAttribute("aria-label", ".form-select-sm example");
  addSelect.setAttribute("name", "ingredientcode");

  // input 태그에 append할 option 태그를 만든다.
  // const addOption = document.createElement("option");
  // addOption.setAttribute("th:each", "m:${ingredientList3}");
  // addOption.setAttribute("th:value", "${m.ingredientcode}");
  // addOption.setAttribute("th:text", "${m.ingredientname}");

  // option만드는 부분에서 for문을 돌려서 만들어본다.
  for(let eachingre of ingrelist) {
    let addOption = document.createElement("option");
    addOption.value = eachingre.ingredientcode;
    addOption.innerText = eachingre.ingredientname;
    addSelect.append(addOption);
  }

  // 첫번째 col에서 첫번째 입력칸 요소 만들기 끝

  // 실질적으로 유저들이 볼 수 있는 입력부분(두번째 입력칸)
  const addDivClassInputGroup2 = document.createElement("div");
  addDivClassInputGroup2.setAttribute("class", "input-group mb-3");

  // label 및 input 태그 작성부분
  const addLabel2 = document.createElement("label");
  addLabel2.setAttribute("class", "input-group-text");
  // 여기에서 말하는 for 어트리뷰트는 참조할 라벨 가능 요소의 id 속성 값을 의미함.
  addLabel2.setAttribute("for", "inputGroupFile02");
  // 라벨 안에 들어갈 텍스트 작성
  const productAmount = document.createTextNode("수량");
  addLabel2.appendChild(productAmount);
  // input 태그 생성 및 속성 지정
  const addInput2 = document.createElement("input");
  addInput2.setAttribute("type", "text");
  addInput2.setAttribute("name", "ingredientquantityinfridgebox");
  addInput2.setAttribute("class", "form-control");
  addInput2.setAttribute("id", "inputGroupFile02");
  // 첫번째 col에서 두번째 입력칸 요소 만들기 끝

  // 나오는 내용들의 그리드를 잡아주는 부분(두번째 col)
  const addDivClassCol2 = document.createElement("div");
  addDivClassCol2.setAttribute("class", "col");

  // 실질적으로 유저들이 볼 수 있는 입력부분(세번째 입력칸)
  const addDivClassInputGroup3 = document.createElement("div");
  addDivClassInputGroup3.setAttribute("class", "input-group mb-3");

  // label 및 input 태그 작성부분
  const addLabel3 = document.createElement("label");
  addLabel3.setAttribute("class", "input-group-text");
  // 여기에서 말하는 for 어트리뷰트는 참조할 라벨 가능 요소의 id 속성 값을 의미함.
  addLabel3.setAttribute("for", "inputGroupFile03");
  // 라벨 안에 들어갈 텍스트 작성
  const productPosition = document.createTextNode("보관위치");
  addLabel3.appendChild(productPosition);

  // ==========================================================================> 양성아 여기서 부터 시작해!!!!
  const addSelect2 = document.createElement("select");
  addSelect2.setAttribute("class", "form-select form-select-sm");
  addSelect2.setAttribute("size", "1");
  addSelect2.setAttribute("aria-label", ".form-select-sm example");
  addSelect2.setAttribute("name", "storagecode");

  // 위의 select 에다가 더해줄 option태그 만들기
  const addOption1 = document.createElement("option");
  const addOption2 = document.createElement("option");
  const addOption3 = document.createElement("option");
  addOption1.setAttribute("value", "1");
  addOption1.innerText = "냉장";
  addOption2.setAttribute("value", "2");
  addOption2.innerText = "냉동";
  addOption3.setAttribute("value", "3");
  addOption3.innerText = "실온";

  // input 태그 생성 및 속성 지정
  // const addInput3 = document.createElement("input");
  // addInput3.setAttribute("type", "text");
  // addInput3.setAttribute("class", "form-control");
  // addInput3.setAttribute("id", "inputGroupFile03");
  // 첫번째 col에서 첫번째 입력칸 요소 만들기 끝

  // 실질적으로 유저들이 볼 수 있는 입력부분(두번째 입력칸)
  const addDivClassInputGroup4 = document.createElement("div");
  addDivClassInputGroup4.setAttribute("class", "input-group mb-3");

  // label 및 input 태그 작성부분
  const addLabel4 = document.createElement("label");
  addLabel4.setAttribute("class", "input-group-text");
  // 여기에서 말하는 for 어트리뷰트는 참조할 라벨 가능 요소의 id 속성 값을 의미함.
  addLabel4.setAttribute("for", "inputGroupFile04");
  // 라벨 안에 들어갈 텍스트 작성
  const productExpireDate = document.createTextNode("유통기한");
  addLabel4.appendChild(productExpireDate);
  // input 태그 생성 및 속성 지정
  const addInput4 = document.createElement("input");
  addInput4.setAttribute("type", "date");
  addInput4.setAttribute("value", "xxx");
  addInput4.setAttribute("min", "yyy");
  addInput4.setAttribute("max", "zzz");
  addInput4.setAttribute("class", "form-control input-large");
  addInput4.setAttribute("name", "expiredate");
  addInput4.setAttribute("id", "expiredate");
  addInput4.setAttribute("placeholder", "유통기한");
  addInput4.setAttribute("required", "");

  // 첫번째 col에서 첫번째 입력칸을 만드는 과정
  addDivClassInputGroup.appendChild(addLabel);
  addSelect.appendChild(addOption);
  addDivClassInputGroup.appendChild(addSelect);
  addDivClassCol.appendChild(addDivClassInputGroup);

  // 첫번째 col에서 두번째 입력칸을 만드는 과정
  addDivClassInputGroup2.appendChild(addLabel2);
  addDivClassInputGroup2.appendChild(addInput2);
  addDivClassCol.appendChild(addDivClassInputGroup2);

  // 두번째 col에서 세번째 입력칸을 만드는 과정
  addDivClassInputGroup3.appendChild(addLabel3);
  addSelect2.appendChild(addOption1);
  addSelect2.appendChild(addOption2);
  addSelect2.appendChild(addOption3);
  addDivClassInputGroup3.appendChild(addSelect2);
  addDivClassCol2.appendChild(addDivClassInputGroup3);

  // 두번째 col에서 네번째 입력칸을 만드는 과정
  addDivClassInputGroup4.appendChild(addLabel4);
  addDivClassInputGroup4.appendChild(addInput4);
  addDivClassCol2.appendChild(addDivClassInputGroup4);

  // form태그에 첫번째, 두번째 col append
  newAddForm.appendChild(addDivClassCol);
  newAddForm.appendChild(addDivClassCol2);

  // <hr>로 줄 그어줄 요소 만들기
  const addHr = document.createElement("hr");

  // 여기 아래 부분에는 루트div에 하나씩 붙이는 동작.
  rootDiv.appendChild(newAddForm);
  rootDiv.appendChild(addHr);
}

addIngredientBtn.addEventListener("click", addIngredientBtnHandler);

// 닫기 버튼 눌렀을 때 만들어진 요소 초기화(힘들거 같다 싶으면 그냥 페이지 리로드 시켜버려!!)
const $closeBtnIcon = document.getElementById("closeBtnIcon");
const $closeBtn = document.getElementById("closeBtn");

function closeBtnHandler() {}

$closeBtn.addEventListener("click", closeBtnHandler);
$closeBtnIcon.addEventListener("click", closeBtnHandler);
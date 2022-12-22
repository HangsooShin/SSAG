async function uploadImage(input, type) {
  let file = input.files[0]; // 선택된 파일 가져오기
  console.log("파일: ", file);
  let processtype = type;

  // 미리 만들어 놓은 div에 text(파일 이름) 추가
  let name = document.getElementById("fileName");
  name.textContent = file.name;

  const targetUrl = "/uploadimage";
  let formData = new FormData();
  formData.append("file", input.files[0]);
  formData.append("processtype", processtype);
  console.log("formData: ", formData);
  const options = {
    method: "post",
    body: formData,
  };

  try {
    let response = await fetch(targetUrl, options);
    let searchdata = await response.json();
    const $testBtn = document.getElementById("$testBtn");
    console.log("$testBtn: ", $testBtn);
    $testBtn.click();

    console.log("searchdata: ", searchdata);

    // const modal = document.getElementById("selftypingUploadModal");
    // console.log("modal1: ", modal);
    // modal.setAttribute("aria-hidden", "false");
    // console.log("modal2: ", modal);
    let inputcount = searchdata.response.codelist.length;
    for (let i = 0; i < inputcount; i++) {
      document.getElementsByClassName("value-" + searchdata.response.codelist[i])[i].selected = true;
      if (i != inputcount - 1) {
        addIngredientBtnHandler(num);
      }
    }
  } catch (err) {
    console.log("error: ", err);
    console.log("데이터 없음");
  }
}

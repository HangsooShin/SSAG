$(function () {
  $("#memoSubmitForm").on("submit", function () {
    memoSubmitHandler()
  })
})

function memoSubmitHandler() {
  let form = document.querySelector("#memoSubmitForm")
  let formData = new FormData(form)

  $.ajax({
    url: "/addArticle",
    type: "POST",
    data: formData,
    contentType: false,
    processData: false,
  }).done(function (data) {
    callback(data)
    $("#memo-table").load(location.href + " #memo-table")
  })
}

// 이 아래 부분은 jquery ajax를 활용한 진짜 비동기 방식을 구현하는 부분
// 이것도 실패
// const $form = document.getElementById("memoSubmitForm")

// function memoSubmitHandler(event) {
//   event.preventDefault()
//   // FormData 객체 생성
//   // let formData = document.querySelector("#memoSubmitForm")
//   let formData = document.querySelector("#memotext")

//   $.ajax({
//     type: "POST",
//     enctype: "multipart/form-data",
//     url: "/addArticle",
//     datatype: json,
//     data: formData,
//     processData: false,
//     contentType: false,
//     cache: false,
//     timeout: 600000,
//     success: function (data) {
//       console.log("성공 :", data)
//     },
//     error: function (error) {
//       console.log("에러: ", error)
//     },
//     complete: function () {
//       closeModal()
//       console.log(
//         "리로드 하는 부분: 여기는 도착 했니=================================>???"
//       )
//       // location.reload()
//       // 리로드하고 싶은 div 아이디값 적용 !!
//       $("#memo-table").load(location.href + " #memo-table")
//       // 두번째 아이디 적는 곳에 앞에 공백도 포함하기
//     },
//   })
// }

// const $form = document.getElementById("memoSubmitForm")
// const $input = document.getElementById("memoSubmitInput")
// const $btn = document.getElementById("memoSubmitBtn")

// function memoSubmitHandler(event) {
//   event.preventDefault()
//   console.log(
//     "memoSubmitHandler fetch method start=====================================>"
//   )
//   fetch("/addArticle", {
//     method: "post",
//     headers: {
//       "Content-type": "application/x-www-form-urlencoded",
//     },
//     body: new URLSearchParams({
//       memotext: $input.value,
//     }),
//   })
//     .then((req) => {
//       console.log("성공: ", req)
//     })
//     .catch((error) => {
//       console.error("실패: ", error)
//     })
//   window.location.reload()
// }

// $form.addEventListener("submit", memoSubmitHandler)

// 이 아래 부분은 실패작
// const $form = document.getElementById("memoSubmitForm")
// const $fetch = require("node-fetch")

// const memoSubmitHandler = async () => {
//   const $url = "/addArticle"

//   const response = await $fetch($url)
//   const data = await response.json()
//   return data
// }
// memoSubmitHandler().then(console.log).catch(console.error)

// $form.addEventListener("submit", memoSubmitHandler)

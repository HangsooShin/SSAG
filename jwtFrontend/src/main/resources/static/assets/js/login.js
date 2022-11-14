const username = document.getElementById("username"),
  password = document.getElementById("password")
// loginBtn = document.getElementById("loginBtn")
//   form = document.getElementById("submit-form")

const formData = document.getElementById("submit-form")

function login(event) {
  event.preventDefault()
  // const payload = new FormData(formData)
  const req = {
    username: username.value,
    password: password.value,
  }
  console.log(req)
  console.log(JSON.stringify(req))
  fetch("/login", {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(req),
  })
    .then((Response) => Response.text())
    .then((req) => {
      console.log("성공:", req)
    })
    .catch((error) => {
      console.error("실패:", error)
    })
  // console.log("payload:", payload)
  console.log("username, passowrd:", username, password)
  console.log("JSON.stringify(req):", JSON.stringify(req))
  console.log("Login data upload succeed")
}

formData.addEventListener("submit", login)

// let formData = document.getElementById("submit-form")

// fetch("https://httpbin.org/post", {
//   method: "POST",
//   cache: "no-cache",
//   body: formData, // body 부분에 폼데이터 변수를 할당
// })
//   .then((response) => response.json())
//   .then((data) => {
//     console.log(data)
//   })

// fetch("https://httpbin.org/post", {
//   method: "POST",
//   cache: "no-cache",
//   body: new URLSearchParams({
//     // 일반 객체를 fordata형식으로 변환해주는 클래스
//     aaa: "a1",
//     bbb: "b1",
//   }),
// })
//   .then((response) => response.json())
//   .then((data) => {
//     console.log(data)
//   })

// 이 아래에 있는 방식은 주소를 지정하고 거기에서 가지고 오는 방법이였다.
// let form = document.getElementById("submit-form")

// form.addEventListener("submit", (e) => {
//   e.preventDefault()
//   console.log(form)
//   const payload = new FormData(form)

//   console.log([...payload])

//   fetch("/login", {
//     method: "POST",
//     headers: {
//       "Content-Type": "appliaction/json",
//     },
//     body: JSON.stringify(payload),
//   })
//     .then((response) => response.json())
//     .then((data) => console.log(data))
//   console.log(payload, "여기가 데이터")
// })

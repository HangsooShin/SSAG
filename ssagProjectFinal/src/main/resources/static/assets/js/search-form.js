// const username = document.getElementById("username"),
//   password = document.getElementById("password")
// // loginBtn = document.getElementById("loginBtn")
// //   form = document.getElementById("submit-form")

// const formData = document.getElementById("submit-form")

// function login(event) {
//   event.preventDefault()
//   // const payload = new FormData(formData)
//   const req = {
//     username: username.value,
//     password: password.value,
//   }
//   console.log(req)
//   console.log(JSON.stringify(req))
//   fetch("/login", {
//     method: "POST",
//     headers: {
//       "Content-type": "application/json",
//     },
//     body: JSON.stringify(req),
//   })
//     .then((Response) => Response.text())
//     .then((req) => {
//       console.log("성공:", req)
//     })
//     .catch((error) => {
//       console.error("실패:", error)
//     })
//   // console.log("payload:", payload)
//   console.log("username, passowrd:", username, password)
//   console.log("JSON.stringify(req):", JSON.stringify(req))
//   console.log("Login data upload succeed")
// }

// formData.addEventListener("submit", login)

// const

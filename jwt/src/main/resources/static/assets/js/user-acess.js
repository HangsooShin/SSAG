function tokenAccess(event) {
  event.preventDefault()
  const $jwttoken = Response.headers.get("Authorization")
  const sessionToken = sessionStorage.getItem("wtw-token", $jwttoken)

  console.log(sessionToken)

  fetch("api/v1/user", {
    method: "GET",
    headers: {
      "Content-type": "application/json",
      "access-token": "Bearer " + sessionStorage.getItem("wtw-token"),
    },
  })
    .then((Response) => {
      console.log("성공:", Response)
    })
    .catch((error) => {
      console.error("실패:", error)
    })
}

document.addEventListener("DOMContentLoaded", tokenAccess)
// window.onload.tokenAccess()

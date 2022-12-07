const $searchForm = document.getElementById("search-form")
const $similar = document.getElementById("similar")

function indexSearchHandler(event) {
  event.preventDefault()

  fetch("/productkeyword", {
    method: "POST",
    headers: {
      "Content-type": "application/text",
    },
    body: $similar,
  })
    .then((req) => {
      console.log("성공", req)
    })
    .then((Response) => {
      console.log("Response 객체: ", Response)
    })
    .catch((error) => {
      console.error("실패:", error)
    })
}

$searchForm.addEventListener("submit", indexSearchHandler)

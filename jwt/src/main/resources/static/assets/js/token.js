let token = localStorage.getItem('wtw-token') || '';

fetch('http://localhost:8000/api/v1/user/', {
  headers: {
      'Authorization': token,
  }
})
.then(response => response.json())
.then(response => {
   console.log(response.data);
})
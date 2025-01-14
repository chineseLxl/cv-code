import axios from 'axios';
const http = axios.create({
  URL: "http://127.0.0.1:8081/",
  headers: {
	"token":localStorage.getItem("token")
  }
})

export {
	http
}
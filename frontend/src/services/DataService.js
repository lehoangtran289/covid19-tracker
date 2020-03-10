import axios from 'axios';

const url = "http://localhost:8080/api"


class DataService {
    getlist() {
        return axios.get(`${url}/list`);
    }
}

export default new DataService();
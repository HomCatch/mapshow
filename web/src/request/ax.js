import ax from 'axios';
import { correct_code_parse, normal_code_parse } from './code-parse';

const baseURL = '/api';

const http = ax.create({
    baseURL,
    timeout: 40000,
    withCredentials: true
})

// 请求拦截
http.interceptors.request.use(config => {
    config.headers.token = localStorage.getItem('token');
    config.headers['x-forwarded-for'] = window.returnCitySN.cip
    // ...
    return config
})

// 响应拦截
http.interceptors.response.use(res => {
    normal_code_parse(res.data.ret)
    return res.data
}, error => {
    // console.log(error.response.status)
    if (error.response) {
        correct_code_parse(error.response.status);
    }
    return Promise.reject(error)
})

export default http;
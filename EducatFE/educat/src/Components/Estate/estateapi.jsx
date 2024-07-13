import axios from 'axios';


const estateapi = axios.create({
    baseURL: 'http://localhost:8090/estate'
});

estateapi.interceptors.request.use(
    config => {
        if (!config.url.endsWith('/login')) {
            const token = localStorage.getItem('accessToken');
            if (token) {
                config.headers['Authorization'] = `Bearer ${token}`;
            }
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);



estateapi.interceptors.response.use(
    response => response,
    async error => {
        const originalRequest = error.config;
        if (error.response.status === 401 && !originalRequest._retry && originalRequest.url !== "/refresh-token") {
            originalRequest._retry = true;
            try {
                
                const rs = await axios.post('http://localhost:8090/estate/refresh-token', {}, {
                    withCredentials: true
                });
                const { accessToken } = rs.data;
                localStorage.clear();
                localStorage.setItem('accessToken', accessToken);
                estateapi.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
                originalRequest.headers['Authorization'] = `Bearer ${accessToken}`;
                return estateapi(originalRequest);
            } catch (refreshError) {
                
                console.error('Unable to refresh token', refreshError);
                return Promise.reject(refreshError);
                
            }
        }
        return Promise.reject(error);
    }
);

export default estateapi;



let dev = process.env.NODE_ENV === 'development';
const apiUrl = dev ? 'http://localhost:8888' : 'http://back:8080/';
// const apiUrl = 'http://localhost:8080';
const basePath = '/';
const fullBasePath = apiUrl;



export { apiUrl, basePath, fullBasePath}
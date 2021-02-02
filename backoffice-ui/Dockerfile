FROM node:12-alpine
WORKDIR /app
COPY __sapper__ __sapper__
COPY node_modules node_modules
COPY static/static static/static
COPY static/manifest.json static/manifest.json
COPY static/logo-192.png static/logo-192.png
COPY package.json package.json

EXPOSE 3000
CMD ["npm", "start"]
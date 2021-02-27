FROM node:12.11.1-alpine AS build
WORKDIR /usr/src/app
COPY package.json ./
RUN npm install
COPY . .
EXPOSE 4200
RUN npm run build
#
#FROM nginx:alpine
#COPY --from=build /usr/src/app/ /usr/share/nginx/html
#COPY nginx.conf /etc/nginx/conf.d/default.conf
#EXPOSE 80

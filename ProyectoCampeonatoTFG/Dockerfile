# Compilar Angular
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration production

# Fase 2: servir con Nginx
FROM nginx:alpine
COPY --from=build /app/dist/proyecto-campeonato-tfg/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

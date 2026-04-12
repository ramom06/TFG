# Etapa de compilación
FROM node:22 AS build
WORKDIR /app

#Copia dependencia e instala libreria
COPY package*.json ./
RUN npm install

#Copia resto
COPY . .

#Esto prepara el codigo
RUN npm run build -- --configuration=production

# Etapa de servidor
FROM nginx:alpine

#Coge los ficheros compilados y los lleva carpeta pública de nginx
COPY --from=build /app/dist/*/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

#Arranca nginx y lo mantiene ejecutandose
CMD ["nginx", "-g", "daemon off;"]

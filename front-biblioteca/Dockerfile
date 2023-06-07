# Base image
FROM node:14-alpine

# Establecer el directorio de trabajo dentro de la imagen
WORKDIR /app

# Copiar los archivos de configuración
COPY package.json .
COPY package-lock.json .
COPY vite.config.js .
# Instalar las dependencias
RUN npm ci 

# Copiar el resto de los archivos del proyecto
COPY . .

# Construir la aplicación

# Exponer el puerto en el que se ejecuta la aplicación
EXPOSE 5173

# Comando para ejecutar la aplicación
CMD ["npm","run","dev"]

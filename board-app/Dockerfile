FROM    node:22.13.1 AS builder
ARG     SPRINGBOOT_ADDRESS=192.168.0.221
ARG     SPRINGBOOT_PORT=9090
ENV     VITE_REST_API_HOST=${SPRINGBOOT_ADDRESS}
ENV     VITE_REST_API_PORT=${SPRINGBOOT_PORT}
WORKDIR /app
COPY    . .
RUN     npm install
RUN     npm run build

FROM    nginx
COPY    --from=builder /app/dist /usr/share/nginx/html
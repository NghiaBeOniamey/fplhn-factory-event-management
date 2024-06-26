# Stage 1: Build stage
FROM node:18 AS build

# Set the working directory to /app
WORKDIR /app

# Copy package.json and yarn.lock to the working directory
COPY package.json /app/package.json
COPY yarn.lock /app/yarn.lock

# Install dependencies
RUN yarn install

# Copy the entire project to the working directory
COPY . /app

# Build the project
RUN yarn build

# Stage 2: Production stage
FROM nginx:1.21.3-alpine

# Copy the built files from the build stage to the nginx html directory
COPY --from=build /app/build /usr/share/nginx/html

# Copy the nginx configuration file to the nginx conf directory
COPY /nginx/nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 3000
EXPOSE 3000

# Start nginx with the specified configuration
CMD ["nginx", "-g", "daemon off;"]
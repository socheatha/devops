# Homework: Add a sub domain for jenkins

## Created a new sub domain point to our server IP (A record)
```
jenkin.msswebcenter.com
```

## Add sample configuration of reverse proxy powered by Nginx (/etc/nginx/conf.d/conf.d)
```
server {
    listen 80;
    server_name jenkin.msswebcenter.com;

    location / {
        proxy_pass http://localhost:8080;

        proxy_set_header Host $http_host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

    }
}
```

## Adding HTTPs for the new sub domain
```
    sudo apt install certbot -y
    sudo apt install python3-certbot-nginx -y
    sudo certbot --nginx -d jenkin.msswebcenter.com
```

## Final result
```
server {
    listen 80;
    server_name jenkin.msswebcenter.com;

    location / {
        proxy_pass http://localhost:8080;

        proxy_set_header Host $http_host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

    }

    listen 443 ssl; # managed by Certbot
    ssl_certificate /etc/letsencrypt/live/jenkin.msswebcenter.com/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/jenkin.msswebcenter.com/privkey.pem; # managed by Certbot
    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot

}

server {
    if ($host = jenkin.msswebcenter.com) {
        return 301 https://$host$request_uri;
    } # managed by Certbot


    listen 80;
    server_name jenkin.msswebcenter.com;
    return 404; # managed by Certbot


}
```
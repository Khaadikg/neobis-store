<h3>Diagram v1</h3>
<img width="1367" alt="Диаграмма версии 2.0" src="https://github.com/Khaadikg/neobis-store/assets/126019047/a4b72c66-0898-490f-ad8a-b0b41294f5fb">

<h3>==> Docker <==</h3>

```
- DOCKERFILE
1.BUILD IMAGE ==> docker build -t neobis-store .
2.PULL POSTGRES ==> docker pull postgres
3.SET DATABASE ==>  docker run --name neobis -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=haadibolotbekov -e POSTGRES_DB=neobis -d postgres
4.RUN PROJECT ==> docker run -d -p 9090:9090 --name neobis-store --link neobis:postgres neobis-store

- DOCKER-COMPOSE
docker compose -f docker-compose.yml up  
```
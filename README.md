# [김정인] 원티드 프리온보딩 백엔드 인턴십 - 선발 과제

- 과제 수행 기간: 2023.08.15 ~ 2023.08.16
- 배포 주소: http://ec2-52-78-100-235.ap-northeast-2.compute.amazonaws.com:8080/

![architiecture](profile/architiecture.png)



## 애플리케이션 실행 방법(엔드포인트 호출 방법 포함)

- **http://ec2-52-78-100-235.ap-northeast-2.compute.amazonaws.com:8080/{endpoint}**

| API명            | Method | Endpoint          |
| ---------------- | ------ | ----------------- |
| 회원가입         | POST   | `api/user`        |
| 로그인           | POST   | `api/user/login`  |
| 게시글 생성      | POST   | `api/post`        |
| 게시글 조회      | GET    | `api/post`        |
| 특정 게시글 조회 | GET    | `api/post/detail` |
| 게시글 수정      | PATCH  | `api/post`        |
| 게시글 삭제      | DELETE | `api/post`        |



## 데이터베이스 테이블 구조

 ![erd](profile/erd.png)

User 테이블과 Post 테이블 연관관계를 제거하여 데이터베이스 수정을 용이하게 하였다.



## 구현한 API의 동작을 촬영한 데모 영상 링크 

https://youtu.be/rcvpXubFIT4



## 구현 방법 및 이유에 대한 간략한 설명

### ✔️ 회원가입 (POST `api/user`)

- 이메일과 비밀번호로 회원가입한다.
- 비밀번호는 암호화한다.
- 이메일 조건, 비밀번호 조건을 만족하는지 유효성 검사한다.



### ✔️ 로그인 (POST `api/user/login`)

- 가입된 정보를 확인하고, 로그인에 성공하면 jwt 토큰을 발급한다. 
- 발급한 토큰은 cookie에 저장하여 client에 제공한다. 
- 이메일과 비밀번호 유효성 검사를 한다.



### ✔️ 게시글 생성 (POST `api/post`)

- title, content를 받아 새로운 글을 작성한다.



### ✔️ 게시글 조회(GET `api/post`)

- page size와 게시글 수를 받아 게시글 목록을 조회한다.



### ✔️ 특정 게시글 조회(GET `api/post/detail`)

- 게시글 id로 특정 게시글을 조회한다.



### ✔️ 게시글 수정(PATCH `api/post`)

- Header로 토큰을 받아 사용자 정보를 확인한다.
- 등록된 사용자인지 확인하고, 등록되어있으면 게시글을 수정할 수 있다.



### ✔️ 게시글 삭제(DELETE `api/post`)

- Header로 토큰을 받아 사용자 정보를 확인한다.
- 등록된 사용자인지 확인하고, 등록되어있으면 게시글을 삭제할 수 있다



## API 명세(request/response 포함)

### ⚡️ 회원가입 (POST `api/user`)

#### request

> request body

```json
{
    "email": "july@gmail.com",
    "password": "qwer1234"
}
```



#### response

> success

```json
{
    "data": {
        "email": "july@gmail.com",
        "password": "$2a$10$nnZFVyMcSeJsbAMZ5qb0wOIxg2Z.7JkPXFxW6JB.6r.qbJ2pKjCUa"
    },
    "message": "새로 생성되었습니다.",
    "code": "resource.created"
}
```



> fail

**중복된 메일인 경우**

```json
{
    "code": "fail",
    "message": "중복된 이메일입니다."
}
```



**@이가 없는 경우**

```json
{
    "code": "fail",
    "message": "이메일에 '@'가 없습니다."
}
```



**비밀번호가 8글자 이하인 경우**

```json
{
    "email": "wnajsldkf@gmail.com",
    "password": "qwer"
}
```



### ⚡️ 로그인 (POST `api/user/login`)

#### request

> request body

```json
{
    "email": "mywnajsldkf1@gmail.com",
    "password": "qwer1234"
}
```



#### response

> success

```json
{
    "data": {
        "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoVG9rZW4iLCJlbWFpbCI6Im15d25hanNsZGtmMUBnbWFpbC5jb20iLCJpYXQiOjE2OTIxNzgxODUsImV4cCI6MTY5MjE5MjU4NX0.Y6tjtPgGfaI1MtEHl69QCuC1qMN9ax8Lim3t5TyIqoU",
        "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWZyZXNoVG9rZW4iLCJlbWFpbCI6Im15d25hanNsZGtmMUBnbWFpbC5jb20iLCJpYXQiOjE2OTIxNzgxODUsImV4cCI6MTY5MjIwNjk4NX0.wub1Mawqu8nde-fGUx9NoMKjaTd-8mtKDsNiR3uVNvc"
    },
    "message": "성공",
    "code": "success"
}
```



> fail

**가입되지 않은 사용자인 경우**

```json
{
    "code": "fail",
    "message": "가입되지 않은 사용자입니다."
}
```



**@이가 없는 경우**

```json
{
    "code": "fail",
    "message": "이메일에 '@'가 없습니다."
}
```



**비밀번호가 8글자 이하인 경우**

```json
{
    "email": "wnajsldkf@gmail.com",
    "password": "qwer"
}
```



### ⚡️ 게시글 생성 (POST `api/post`)

#### request

> request body

```json
{
    "title": "제목",
    "content": "내용",
    "userId": "mywnajsldkf@gmail.com"
}
```



#### response

> success

```json
{
    "data": {
        "title": "제목",
        "content": "내용",
        "userId": "mywnajsldkf@gmail.com"
    },
    "message": "새로 생성되었습니다.",
    "code": "resource.created"
}
```



### ⚡️ 게시글 조회(GET `api/post`)

#### request

> request params

`page=10&size=6`



#### response

> success

```json
{
    "data": {
        "content": [
            {
                "title": "titie66",
                "content": "content66",
                "userId": "user"
            },
            {
                "title": "titie67",
                "content": "content67",
                "userId": "user"
            },
            {
                "title": "titie68",
                "content": "content68",
                "userId": "user"
            },
            {
                "title": "titie69",
                "content": "content69",
                "userId": "user"
            },
            {
                "title": "titie70",
                "content": "content70",
                "userId": "user"
            },
            {
                "title": "titie71",
                "content": "content71",
                "userId": "user"
            }
        ],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 60,
            "pageNumber": 10,
            "pageSize": 6,
            "unpaged": false,
            "paged": true
        },
        "totalPages": 17,
        "totalElements": 97,
        "last": false,
        "size": 6,
        "number": 10,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "numberOfElements": 6,
        "first": false,
        "empty": false
    },
    "message": "성공",
    "code": "success"
}
```



### ⚡️ 특정 게시글 조회(GET `api/post/detail`)

#### request

> request params

`postId=40`



#### response

> success

```json
{
    "data": {
        "title": "titie40",
        "content": "content40",
        "userId": "user"
    },
    "message": "성공",
    "code": "success"
}
```



### ⚡️ 게시글 수정(PATCH `api/post`)

#### request

> request params

`postId=6`



> request body

```json
{
    "title": "타이틀 변경",
    "content": "hello~"
}
```



#### response

> success

```json
{
    "data": {
        "title": "타이틀 변경",
        "content": "content11",
        "userId": "mywnajsldkf@gmail.com"
    },
    "message": "성공",
    "code": "success"
}
```





### ⚡️ 게시글 삭제(DELETE `api/post`)

#### request

> request params

`postId=6`



#### response

> success

```json
{
    "data": {
        "title": "titie41",
        "content": "content41",
        "userId": "user"
    },
    "message": "삭제되었습니다.",
    "code": "resource.deleted"
}
```


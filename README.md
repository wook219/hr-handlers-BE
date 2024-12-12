## 👋인사잘해팀 : HR(human resources)
### 프로젝트 소개
![인사잘해](https://media.discordapp.net/attachments/1303967846027755560/1316651649569132595/2024-12-12_3.23.22.png?ex=675bd2e7&is=675a8167&hm=eaf69c79a2c4b9311af7fc45d8d98b58633597eed4e75d7c6e4225e7b45b4f91&=&format=webp&quality=lossless&width=883&height=295)
> 인사 및 근태 관리를 통합한 사내 HR ERP 프로그램!

본 프로젝트는 사내 인사 및 근태 관리를 효율화하기 위해 개발된 HR ERP 시스템입니다.
직원들은 사내 채팅, 휴가 신청, 출퇴근 기록, 게시판 활용, 급여 확인 등을 하나의 플랫폼에서 손쉽게 처리할 수 있습니다.
관리자들은 직원 정보 및 근태를 체계적으로 관리할 수 있어 업무 생산성이 향상됩니다.

[인사잘해](http://34.47.90.224:3000/)

**접속 가능한 계정**
- 이상해 사원
  - **ID** : 20241216
  - **PW** : 123123123
  

- 이종현 인사관리자
  - **ID** : 20241203
  - **PW** : 123123123

---

### API 문서
**Swagger-UI** : http://34.47.90.224:8080/swagger-ui/index.html

---

### 개발 기간
- 2024.11.18 ~ 2024.12.13

---

### 팀원 소개
| 이름 (Name) | 역할 (Role)  | 담당 도메인 (Domain) | 
  |-----------|------------|-----------------|
| 정현        | 팀장 (Leader) | 사원              | 
| 한현        | 팀원 (Member) | 메신저             | 
| 이서율       | 팀원 (Member) | 게시판             |  
| 송진욱       | 팀원 (Member) | 근태/휴가/일정        | 
| 김준수       | 팀원 (Member) | 급여              | 

---

### 기술 스택
- **Frontend** : React, Bootstrap, HTML, CSS, Javascript
- **Backend** : Java, JPA, Spring Boot, Spring Security, JWT, Websocket, STOMP, MyBatis, QueryDSL
- **Storage** : AWS S3, MariaDB
- **Deployment**: GCP, Nginx

---

### 와이어프레임
![와이어프레임](https://github.com/user-attachments/assets/794f6b45-ce24-4aec-a9ec-80a0aacc2d6e)

---

### ERD
![ERD](https://github.com/user-attachments/assets/ef15cc51-089b-45a7-b704-e8ffe7cad939)

---

### 📌주요 기능
##### 홈
![홈](https://media.discordapp.net/attachments/1316699693832601613/1316700008166461450/image.png?ex=675bfff1&is=675aae71&hm=0adaa3ea23a303901df6b38866b1fa0d5df0b9c23d42898568160dcf27bfd0d1&=&format=webp&quality=lossless&width=883&height=361)

##### 사원
![사원1](https://media.discordapp.net/attachments/1303967846027755560/1316653551958884372/2024-12-12_3.31.04.png?ex=675bd4ad&is=675a832d&hm=bb4c6d2416ea6ea8fed568d12de1c97d7e284c0b01fe84127940037f9eda6129&=&format=webp&quality=lossless&width=883&height=378)
![사원2](https://media.discordapp.net/attachments/1303967846027755560/1316653666513715260/2024-12-12_3.31.32.png?ex=675bd4c8&is=675a8348&hm=5558dd05dd3f2844aca4f3e66a26e47d903d7ec6fdae263d448d5687c163d0e3&=&format=webp&quality=lossless&width=883&height=376)

##### 근태
![근태](https://media.discordapp.net/attachments/1303967846027755560/1316692937412968478/image.png?ex=675bf95b&is=675aa7db&hm=2f2ab7c707ad6cd514b3aeb8a521f83cc6cc653c1b1fa97a8d6558eb5598ba30&=&format=webp&quality=lossless&width=883&height=327)

##### 일정
![일정](https://media.discordapp.net/attachments/1303967846027755560/1316654033364193342/2024-12-12_3.32.47.png?ex=675bd51f&is=675a839f&hm=e6a016e42eb3c24abe36df5a402aa88c4abdee1c63952e008bd3e78aaae95938&=&format=webp&quality=lossless&width=883&height=376)

##### 휴가
![휴가](https://media.discordapp.net/attachments/1303967846027755560/1316654169418891305/2024-12-12_3.33.31.png?ex=675bd540&is=675a83c0&hm=0baf978978c086b3669b4c7620d5cd8d1466acd86ae9c59917a949fdc2526098&=&format=webp&quality=lossless&width=687&height=292)

##### 게시판
![게시판1](https://media.discordapp.net/attachments/1303967846027755560/1316654396351844413/image.png?ex=675bd576&is=675a83f6&hm=b89e72862da7cbe35442f8ba10afd3b95ac85542219091c96078c7c132393eb6&=&format=webp&quality=lossless&width=883&height=350)
![게시판2](https://media.discordapp.net/attachments/1303967846027755560/1316654496767545362/2024-12-12_3.34.47.png?ex=675bd58e&is=675a840e&hm=882f1c6df907a3ff951cdca801453931d8473347a57c0f545b05c09d7af1546b&=&format=webp&quality=lossless&width=687&height=291)
![공지사항](https://media.discordapp.net/attachments/1303967846027755560/1316654498856439848/image.png?ex=675bd58e&is=675a840e&hm=c16f941b45118d2eb402ffff5667d043f21f6eaee6d4b8493899e4dd4fa659e1&=&format=webp&quality=lossless&width=883&height=350)

##### 메신저
![메신저](https://media.discordapp.net/attachments/1303967846027755560/1316654277682397194/image.png?ex=675bd55a&is=675a83da&hm=18476049bfc25391d722b069a7fb7b7aadfbe1b1a4f079f63aa5260b1a4f1987&=&format=webp&quality=lossless&width=883&height=345)

##### 급여
![급여1](https://media.discordapp.net/attachments/1303967846027755560/1316654564434378793/image.png?ex=675bd59e&is=675a841e&hm=601ce5e1bf32d77ebaa1c0b9aa7aa19e0be6c1cbc4f1ce886cdc1a10ef0f3e8a&=&format=webp&quality=lossless&width=883&height=378)
![급여2](https://media.discordapp.net/attachments/1303967846027755560/1316654776527749151/image.png?ex=675bd5d0&is=675a8450&hm=74922d8caddb2946f3ebd1d79d20f315b734746bbec7e51ce49cddefbbf40d2f&=&format=webp&quality=lossless&width=883&height=370)

---

### 🚨트러블슈팅
#### 출근/퇴근 시간이 서버시간과 다르게 9시간전으로 저장되는 이슈 
**시도해본 것**
1. Nginx 서버 시간 UTC -> KST 타임존 변경 
  
   - 서버 시간은 변경 되었지만, 동일한 문제 발생

2. 데이터 베이스 시간 변경 
   - 변경 시 오류가 없던 다른 도메인 시간대가 9시간 전으로 변경 되는 다른 문제 발생

**해결 방법**

시간대를 저장하는 Service 부분에 명시적으로 한국시간을 설정해주는 코드로 변경
`LocalDateTime.now(ZoneId.of("Asia/Seoul"))`

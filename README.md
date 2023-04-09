# 실내 공간 3D 종합 데이터를 활용한 가상공간 구축

<div align="center">
<img src="./img/welcomePage.JPG">
</div>

<br>

<div align="center">

|김동언|김찬규|신우섭| 이기성  |
|:---:|:---:|:---:|:---:|
|- 3D |- 팀장 <br> - Front-end|- Back-end|- AI |

</div>

---

# 🔨 Stacks <a name = "stacks"></a>

<div>

#### Back-end

<img alt="Java" src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white"/>
  <img alt="Spring Boot" src ="https://img.shields.io/badge/Spring Boot-6DB33F.svg?&style=for-the-badge&logo=Spring Boot&logoColor=white"/>
<img alt="AWS" src ="https://img.shields.io/badge/AWS-232F3E.svg?&style=for-the-badge&logo=Amazon AWS&logoColor=white"/>
<img alt="MySQL" src ="https://img.shields.io/badge/MySQL-4479A1.svg?&style=for-the-badge&logo=MySQL&logoColor=white"/>
<img alt="Redis" src ="https://img.shields.io/badge/Redis-DC382D.svg?&style=for-the-badge&logo=Redis&logoColor=white"/>
<img alt="Spring Security" src ="https://img.shields.io/badge/Spring Security-6DB33F.svg?&style=for-the-badge&logo=Spring Security&logoColor=white"/>

<br>

#### Front-end

<img alt="JavaScript" src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white">
  <img alt="React" src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white">
<img alt="Redux" src="https://img.shields.io/badge/Redux-764ABC?style=for-the-badge&logo=Redux&logoColor=white">
<img alt="Three.js" src="https://img.shields.io/badge/Three.js-000000?style=for-the-badge&logo=Three.js&logoColor=white">
<img alt="Google Maps" src="https://img.shields.io/badge/Google Maps-4285F4?style=for-the-badge&logo=Google Maps&logoColor=white">

<br>

#### AI, Modeling

<img alt="Python" src ="https://img.shields.io/badge/Python-3178C6.svg?&style=for-the-badge&logo=Python&logoColor=white"/>
<img alt="Anaconda" src ="https://img.shields.io/badge/Anaconda-44A833.svg?&style=for-the-badge&logo=Anaconda&logoColor=white"/>
<img alt="PyTorch" src ="https://img.shields.io/badge/PyTorch-EE4C2C.svg?&style=for-the-badge&logo=PyTorch&logoColor=white"/>

<br>

#### Tools

<img alt="Visual Studio Code" src ="https://img.shields.io/badge/Visual Studio Code-007ACC.svg?&style=for-the-badge&logo=Visual Studio Code&logoColor=white"/>
<img alt="IntelliJ IDEA" src ="https://img.shields.io/badge/IntelliJ IDEA-000000.svg?&style=for-the-badge&logo=IntelliJ IDEA&logoColor=white"/>
<img alt="GitHub" src ="https://img.shields.io/badge/GitHub-181717.svg?&style=for-the-badge&logo=GitHub&logoColor=white"/>
<img alt="Notion" src ="https://img.shields.io/badge/Notion-000000.svg?&style=for-the-badge&logo=Notion&logoColor=white"/>

</div>

<br>

---

# 💎 목적  <a name = "goal"></a>

<details>
   <summary> 내용 보기 (👈 Click) </summary>
<br/>

1. 시공간 제약없이 웹 페이지를 통해 실내 공간을 3D로 확인해 사진과 시공간의 한계를 극복

2. 가구의 배치를 가능하게 하여 입주 전 인테리어 구상에 도움


</details>
<br>

---

# 🎤 요구사항  <a name = "needs"></a>

<details>
   <summary> 내용 보기 (👈 Click) </summary>
<br/>

|   요구사항   |            요구사항 설명            |
|:--------:|:-----------------------------:|
|   회원가입   |     업로드를 위해서는 회원가입을 해야 한다     |
| 회원정보 조회  |  회원가입 한 유저는 자신의 정보를 조회할 수 있다  |
| 회원정보 수정  |  회원가입 한 유저는 자신의 정보를 수정할 수 있다  |
|   회원탈퇴   |    회원가입 한 유저는 회원탈퇴를 할 수 있다    |
|   로그인    |    방을 업로드 하기 위해 로그인을 해야 한다    |
|   로그아웃   |    로그인 상태에서 로그아웃 할 수 있다    |
|  파일 업로드  |    사용자가 새로운 3D 파일을 업로드 할 수 있다    |
| 검토 파일 저장 |    관리자는 업로드 된 파일을 검토하고 변환해 DB에 저장할 수 있다    |
|  빈 방 확인  |    사용자가 기존의 방에서 가구를 모두 제외시킨 빈 방의 구조를 확인할 수 있다    |
|    검색    |    지도에서 원하는 지역 근처의 방을 탐색할 수 있다    |
| 등록된 방 확인 |    사용자가 모든 가구가 배치되어 있는 기존의 방을 3D로 확인할 수 있다    |
|  가구 배치   |    사용자는 가구를 배치해 볼 수 있다    |

</details>
<br>

---

# 🛠 아키텍처  <a name = "structure"></a>

<details>
   <summary> 내용 보기 (👈 Click) </summary>
<br>

<div align="center">
 <img src="./img/structure.JPG" alt="structure">
</div>

- Spring Boot 2.7.3
- AWS EC2 / S3
    - 3D 데이터는 JSON 형식의 파일이며 용량이 커 S3 스토리지에 저장
    - DB에는 S3의 객체 접근 URL을 저장하는 방식 채택
- MySQL + Redis

</details>
<br>

---

# 🔍 클래스 다이어그램  <a name = "class"></a>

<details>
   <summary> 내용 보기 (👈 Click) </summary>
<br/>

<div align="center">
 <img src="./img/class.png" alt="class">
</div>

- 관계 정보

|    이름    |   유형   | 관련 클래스 |   연관 관계   |             설명             |
|:--------:|:------:|:------:|:---------:|:--------------------------:|
| Location | 단방향 연관 |  Room  | 1:1 연관 관계 | 한 개의 방은 한 개의 위치 정보만 가질 수 있다 |
|   User   | 양방향 연관 |  Room  | 1:N 연관 관계 | 유저는 여러 개의 방 정보를 가질 수 있다 |

</details>
<br>

---

# ⚒️ 주요 기능  <a name = "function"></a>

<details>
   <summary> 내용 보기 (👈 Click) </summary>
<br>

<div align="center">
 <img src="./img/function.png" alt="function">
</div>

- 지도
    - 지도 내 검색을 통해 원하는 지역의 방을 찾아볼 수 있음
    - 등록된 방을 핀으로 지도에 표시
    - 핀 선택시 3D로 방을 확인할 수 있음
    - 기존의 방 보기 / 방의 구조만 보기 중 선택
        - 가구를 배치해 볼 수 있어 사전 인테리어 구상에 도움
        - 가구의 배치 초기화 기능 제공
- 회원가입
    - 방 등록을 원한다면 회원가입 필요
    - 요구되는 정보 : 아이디, 패스워드, 이름, 닉네임, 휴대폰 번호, 상호명
- 로그인
    - 개인정보 수정 기능 제공
    - .PTS 형식의 파일 업로드 기능 제공
    - 회원탈퇴시 등록한 방 정보 모두 삭제

</details>
<br>

---

# 📋 API 문서  <a name = "api"></a>

<details>
   <summary> 내용 보기 (👈 Click) </summary>
<br/>

[API 문서](https://wooseobee.gitbook.io/capstone3d-api-docs/)

</details>
<br>

---

### [User Api]
------------------

1. 회원가입
2. 로그인
3. 토큰 재발급
4. 회원정보수정
5. 회원탈퇴

### [Room Api]
------------------

1. 모든 방 조회
2. 선택한 방 조회
3. 모든 가구 조회

### [Upload Api]
------------------

1. 유저 방 업로드
2. 관리자 방 업로드

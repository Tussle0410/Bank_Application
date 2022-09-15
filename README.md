# Bank_Application(Study Project)

회원관리, 송출금, 내역관리 등등 기능을 가진 가상 은행 어플리케이션


# Function

* 회원 기능
   - 회원가입, 로그인, 아이디 찾기, 비밀번호 찾기

* 은행 기능
   - 송금, 거래 내역 기록
   - 펀드, 대출, 계좌 서비스의 종류 안내
   - 사용자 자산 종합 정보

## DataBase Using Way

1. WampServer를 이용하여 Mysql 형태에 데이터베이스를 형성한다.

      (A database is formed in the form of Mysql using WampServer.)

2. 테이블을 형성한다.(Bank_Mysql.txt에 작성해놓았습니다.)

      (Form a table. (I wrote it in Bank_Mysql.txt))

3. cmd(명령어 : ipconfig)를 통해 인터넷 IP를 얻은 후 databaseIP 클래스에 자신의 아이피주소 변경하기

      (After obtaining an Internet IP through cmd, change your IP address in the database IP class.)
  
4. App에 사용되는 .php들을 "wamp64/www/bank/"에 넣어주시면 됩니다.

      (Put the .php files used in the app into "wamp64/www/bank/")



## DataBase Entity-Relation Diagram(E-R)

![릴레이션테이블](https://user-images.githubusercontent.com/69793388/139723410-bc46dcf8-585b-46f7-ad1a-b5618c89f353.png)

## Using Screen
<img width="228" alt="1" src="https://user-images.githubusercontent.com/69793388/180497546-544c663b-b933-4974-b06f-500eba3e56f5.png">
<img width="225" alt="2" src="https://user-images.githubusercontent.com/69793388/180497548-7ab9316b-e376-45a0-9498-fbf980e3114a.png">
<img width="228" alt="3" src="https://user-images.githubusercontent.com/69793388/180497550-17a27fa6-14d0-48a3-9691-f3e5d99e2cfc.png">
<img width="226" alt="4" src="https://user-images.githubusercontent.com/69793388/180497556-2a59797f-045e-4d82-be96-adfda3176864.png">
<img width="226" alt="5" src="https://user-images.githubusercontent.com/69793388/180497558-6de8c686-b2f1-4030-b06e-b6bc82f8cae6.png">
<img width="229" alt="6" src="https://user-images.githubusercontent.com/69793388/180497562-0a40be20-c4a9-4ae7-b225-e927f65fb037.png">



## Studying Info
* MVC, LayoutDesign(ViewPager2, Switch, BottomNavigation..)
* OkHttp, AsyncTask
* MPAndroidChart
* Android LifeCycle
* Activity, Fragment, Adapter
...

## 정보

이메일 – cksgud410@naver.com


[참고 블로그(https://webnautes.tistory.com/828)]

## 출처
MPAndroidChart (https://github.com/PhilJay/MPAndroidChart)

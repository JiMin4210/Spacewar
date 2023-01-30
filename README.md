# SpaceWar App + Device Driver
## 👋 프로젝트 소개
> **Device Driver를 이용한 안드로이드 슈팅 게임**
- **프로젝트 기간** : 2022.04 ~ 2022.06
- [APP 제작 과정 시행 착오.txt](https://github.com/JiMin4210/App_dev_driver/blob/main/App%EA%B4%80%EB%A0%A8%20%EC%8B%9C%ED%96%89%EC%B0%A9%EC%98%A4.txt)
- [Device Driver 제작 과정 시행 착오.txt](https://github.com/JiMin4210/App_dev_driver/blob/main/Dev_Driver%EC%8B%9C%ED%96%89%EC%B0%A9%EC%98%A4.txt)

## :movie_camera: 동작 영상
### 동작 영상
- [SpaceWar 동작영상 Youtube](https://www.youtube.com/watch?v=vcuzwI079nE)

## :books: 얻은 역량
- Device Driver 제작 능력
- Cross Compile에 대한 이해
- Kernel 영역, User 영역에 대한 차이 이해
- Android App에 대한 이해와 제작 능력 (Activity, intent, XML)
- NDK, JNI의 필요성에 대한 이해
- MPU(ACHRO-i.MX6Q) 활용 능력
- 객체지향 프로그래밍 능력
- Linux 활용 능력
- 역할 분담을 통한 협력의 중요성에 대한 이해

## :pushpin: 기대효과
- **Shooting Game App**과 **Device Driver**의 연결을 통해 사용자들이 **좀 더 실감나는 게임**을 즐길 수 있게 해준다.

## 🔧 C - Device Driver 기획
- **JOYSTICK** - 게임 내 캐릭터 8방향 제어 
- **SWITCH** - 공격 버튼, 난이도 제어 버튼, 상점 이용 버튼
- **FND** - 점수 출력
- **LCD** - 플레이 시간, 현재 능력치, 잡은 몬스터 수 출력
- **DOT** - 5가지 캐릭터 표정 출력
- **BUZZER** - 총소리 출력
- **MOTOR** - 대형 몬스터(BOSS) 출현시 회전

## 🎮 JAVA - Game 기획
### Game 요소
- **8방향 캐릭터** 구현
- 프레임별 이미지 교체로 몬스터의 **역동적인 움직임 구현**
- 몬스터는 벽에 튕기는 일반적인 이동이 아닌 시시각각 다른 **랜덤 좌표 이동**으로 구현
- 레이지 모드를 추가함으로써 일정시간 몬스터가 잡히지 않을 경우 캐릭터의 실시간 좌표를 기반으로 **점점 빠르게 직접 추적**하도록 구현
- 몬스터마다 **특성을 고려**해 체력, 이동속도를 다르게 구현
- 캐릭터의 공격력, 체력에 영향을 미치는 **아이템 구현**
- 게임당 한 번 쓸 수 있는 **스킬 구현** (switch 조작을 통해 전 맵 몬스터 제거)
- 점수를 모아 총알의 모양을 변경 시킬 수 있는 **상점 구현**

### Game 진행 순서
<img src="https://user-images.githubusercontent.com/90883534/215096347-cd91cca1-8e09-4340-b156-42549c9eec2b.png" width="700" height="400"/>

## 🔍 완성 이미지
<img src="https://user-images.githubusercontent.com/90883534/215483856-5d0ce913-7a34-4459-acf4-f6472540bace.png" width="600" height="400"/>


## 🔨 사용 보드
- **ACHRO-i.MX6Q**

## ⚡ 사용 기술
- **JNI** : JAVA에서 C/C++ 라이브러리 호출을 위함

## 📝 사용 언어
- **JAVA** : Android App 제작
- **C** : Device Driver 제작

## 🔆 개발 환경
- **Android Studio** : App 제작
- **Linux** : Device Driver Module 등록, Device Driver Compile

## 🎵 기타
### Game에 이용된 그림
<img src="https://user-images.githubusercontent.com/90883534/215177827-a2ba6813-60ce-41c3-b336-7ad8df82dbac.png" width="700" height="400"/>







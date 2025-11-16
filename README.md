# WoowaCollection

우아한테크코스 프리코스 미션들을 모아놓은 Android 애플리케이션입니다.

## 📋 프로젝트 소개

이 프로젝트는 우아한테크코스 프리코스에서 진행된 여러 미션들을 하나의 앱으로 통합한 컬렉션입니다. Jetpack Compose를 활용하여 Android UI를 구현하고, MVVM 아키텍처 패턴을 적용하여 클린 코드 원칙을 준수합니다.

## 🎮 포함된 서비스

### 1. 계산기
문자열로 입력된 숫자들을 다양한 구분자로 분리하여 합을 계산하는 계산기입니다.

**주요 기능:**
* 기본 구분자 지원: 쉼표(,), 콜론( : )
* 커스텀 구분자 지원: `//구분자\n숫자들` 형식
* 계산 기록 저장 (최근 10개)
* 입력값 검증 및 에러 메시지 표시


**사용 예시:**
* `1,2:3` → 결과: 6
* `//;\n1;2;3` → 결과: 6

> 참고: https://github.com/woowacourse-precourse/kotlin-calculator-8

### 2. 레이싱카
여러 대의 자동차가 경주를 펼치는 게임입니다. 각 자동차는 랜덤한 값에 따라 이동하며, 가장 멀리 이동한 자동차가 우승합니다.

**주요 기능:**
* 자동차 이름 입력 (최대 10개, 쉼표로 구분)
* 시도 횟수 입력 (최대 20회)
* 자동 진행 기능 (각 라운드마다 1초 간격으로 자동 실행)
* 실시간 진행 상황 표시
* 경기 기록 조회 기능
* 최종 우승자 표시

> 참고: https://github.com/woowacourse-precourse/kotlin-racingcar-8


### 3. 로또
로또 번호를 구매하고 당첨 번호를 입력하여 당첨 결과를 확인하는 게임입니다.

**주요 기능:**
* 구입 금액 입력 (1,000원 단위, 최대 10,000원)
* 자동 로또 번호 생성
* 당첨 번호 및 보너스 번호 입력
* 각 로또별 당첨 등수 표시
* 수익률 계산 (쓴 돈, 얻은 돈, 수익률)
* 당첨 통계 표시
> 참고: https://github.com/woowacourse-precourse/kotlin-lotto-8





## 🚀 실행 방법

1. 프로젝트 클론
```bash
git clone [repository-url]
cd WoowaCollection
```

2. Android Studio에서 프로젝트 열기

3. Gradle Sync 실행

4. 앱 실행 (에뮬레이터 또는 실제 기기)

## 📝 기능 구현 목록

### 프로젝트 기본 설정

- [x] 초기 준비 및 디렉토리 설정

### 계산기 기능

- [x] 계산기 기본 구조 구현
- [x] 구분자 파싱 로직 구현 (기본 구분자, 커스텀 구분자)
- [x] 숫자 추출 및 검증 로직 구현
- [x] 계산 로직 구현
- [x] 입력값 처리 및 상태 관리
- [x] 계산 기록 저장 기능
- [x] 에러 메시지 처리
- [x] 계산기 화면 UI 구현
- [x] 입력 필드 및 버튼 구현
- [x] 계산 결과 표시
- [x] 계산 기록 표시

### 레이싱카 게임 기능

- [x] 레이싱카 게임 기본 구조 구현
- [x] 자동차 도메인 모델 구현
- [x] 자동차 이동 로직 구현
- [x] 게임 진행 로직 구현
- [x] 우승자 결정 로직 구현
- [x] 입력값 검증 로직 구현
- [x] 자동차 개수 및 시도 횟수 제한 검증
- [x] 게임 상태 관리
- [x] 자동 진행 기능 구현
- [x] 라운드별 진행 상황 업데이트
- [x] 게임 기록 관리
- [x] 레이싱카 화면 UI 구현
- [x] 게임 설정 화면 구현
- [x] 게임 진행 화면 구현
- [x] 실행 결과 및 경기 기록 표시
- [x] 우승자 표시

### 로또 게임 기능

- [x] 로또 게임 기본 구조 구현
- [x] 로또 도메인 모델 구현
- [x] 로또 번호 검증 로직 구현
- [x] 랜덤 로또 번호 생성 기능
- [x] 당첨 등수 결정 로직 구현
- [ ] 수익률 계산 로직 구현
- [ ] 입력값 검증 로직 구현
- [ ] 구입 금액 제한 검증
- [ ] 로또 구매 기능 구현
- [ ] 당첨 번호 입력 및 검증 기능
- [ ] 보너스 번호 입력 및 검증 기능
- [ ] 게임 상태 관리
- [ ] 로또 화면 UI 구현
- [ ] 구입 금액 입력 화면 구현
- [ ] 로또 번호 표시 및 애니메이션
- [ ] 당첨 번호 입력 화면 구현
- [ ] 결과 화면 구현
- [ ] 당첨 번호 및 보너스 번호 표시
- [ ] 각 로또별 당첨 등수 표시
- [ ] 맞춘 번호 강조 표시
- [ ] 당첨 통계 표시
- [ ] 수익률 계산 결과 표시



## 🏗 아키텍처 설계

### MVVM 패턴 적용

```
┌─────────────┐
│    View     │  (Compose UI)
│  (Screen)   │
└──────┬──────┘
       │ observes
       ▼
┌─────────────┐
│  ViewModel  │  (StateFlow)
│  (UiState)  │
└──────┬──────┘
       │ uses
       ▼
┌─────────────┐
│   Domain    │  (Business Logic)
│  (Models)   │
└─────────────┘
```

### 상태 관리

* `StateFlow`를 활용한 단방향 데이터 흐름
* ViewModel에서 UI 상태를 관리
* Domain Layer에서 비즈니스 로직 처리

### 의존성 방향

```
UI Layer → ViewModel Layer → Domain Layer
```

각 레이어는 상위 레이어에만 의존하며, 하위 레이어는 독립적으로 동작합니다.



## 📄 라이선스

이 프로젝트는 우아한테크코스 프리코스 미션을 기반으로 제작되었습니다.

## 📂 프로젝트 구조

```
WoowaCollection/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── java/com/woowacourse/woowacollection/
│       │   │   ├── MainActivity.kt
│       │   │   ├── navigation/
│       │   │   │   └── NavigationRoutes.kt
│       │   │   ├── calculator/
│       │   │   │   ├── domain/
│       │   │   │   │   ├── Calculator.kt
│       │   │   │   │   └── Constants.kt
│       │   │   │   └── ui/
│       │   │   │       ├── CalculatorScreen.kt
│       │   │   │       └── CalculatorViewModel.kt
│       │   │   ├── racingcar/
│       │   │   │   ├── domain/
│       │   │   │   │   ├── Car.kt
│       │   │   │   │   ├── Game.kt
│       │   │   │   │   ├── Constants.kt
│       │   │   │   │   ├── ExceptionMessage.kt
│       │   │   │   │   └── GameValidator.kt
│       │   │   │   └── ui/
│       │   │   │       ├── RacingCarScreen.kt
│       │   │   │       └── RacingCarViewModel.kt
│       │   │   ├── lotto/
│       │   │   │   ├── domain/
│       │   │   │   │   ├── Lotto.kt
│       │   │   │   │   ├── LottoGame.kt
│       │   │   │   │   ├── LottoGenerator.kt
│       │   │   │   │   ├── LottoRank.kt
│       │   │   │   │   ├── RankDecider.kt
│       │   │   │   │   ├── ProfitCalculator.kt
│       │   │   │   │   ├── InputValidator.kt
│       │   │   │   │   ├── Constants.kt
│       │   │   │   │   └── ExceptionMessage.kt
│       │   │   │   └── ui/
│       │   │   │       ├── LottoScreen.kt
│       │   │   │       └── LottoViewModel.kt
│       │   │   └── ui/
│       │   │       └── theme/
│       │   │           ├── Color.kt
│       │   │           ├── Theme.kt
│       │   │           └── Type.kt
│       │   └── res/
│       │       ├── drawable/
│       │       │   └── logo.png
│       │       └── values/
│       └── test/
└── gradle/
    └── libs.versions.toml
```


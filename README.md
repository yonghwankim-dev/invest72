# 📋 요구사항 분석

## 1. 기능 요구사항

### 1.1 투자 수익 계산기 (정기 적금형)

#### 📥 입력

- 월 투자 금액 (예: 100,000원)
- 연 수익률 (퍼센트, 예: 5% → `5`로 입력)
- 투자 기간 (단위: 월 또는 년)
- 복리 방식: 단리 또는 복리
- 과세 유형: 비과세 / 일반 과세(15.4%) / 세금 우대

#### 📤 출력

- **원금**: 총 납입 금액
- **이자**: 투자 기간 동안 발생한 총 이자 (세전 기준)
- **세금**:
    - 비과세: 0원
    - 일반과세: 이자의 15.4%
    - 세금우대: 이자의 N%
- **총 수익 금액**: 원금 + 세후 이자

#### 🧾 예시 (비과세)

- 입력:
    - 월 투자 금액: 100,000원
    - 연 수익률: 5%
    - 투자 기간: 10년
    - 과세 유형: 비과세
- 출력:
    - 원금: 12,000,000원
    - 이자: 35,929,289원
    - 세금: 0원
    - 총 수익 금액: 155,929,289원

#### 🧾 예시 (일반 과세)

- 입력:
    - 월 투자 금액: 100,000원
    - 연 수익률: 5%
    - 투자 기간: 10년
    - 과세 유형: 일반 과세
- 출력:
    - 원금: 12,000,000원
    - 세전 이자: 35,929,289원
    - 세금(15.4%): -5,533,110원
    - 총 수익 금액: 150,396,178원

---

### 1.2 예금 계산기 (정기 예금형)

#### 📥 입력

- 예치 금액
- 연 수익률
- 투자 기간 (월 또는 년)
- 복리 방식: 단리 또는 복리
- 과세 유형: 비과세 / 일반 과세 / 세금 우대

#### 📤 출력

- 원금: 예치 금액
- 이자: 투자 기간 동안 발생한 총 이자 (세전)
- 세금: 과세 유형에 따른 이자 과세
- 총 수익 금액: 원금 + 세후 이자

### 1.3 월별 투자 상태 변화 출력 기능

- 월별로 원금, 이자, 세금, 총 수익 금액을 출력
- 투자 기간 동안 매월 누적된 원금, 이자, 세금, 총 수익 금액을 계산하여 출력
- 예시 출력:
    - 1개월: 원금 100,000원, 이자 416원, 세금 0원, 총 수익 금액 100,416원
    - 2개월: 원금 200,000원, 이자 833원, 세금 0원, 총 수익 금액 200,833원

## 2. 비기능 요구사항

- 금액의 소수점은 버림 처리하며, 정수(원 단위)로 출력해야 한다.

---

## 3. 제약사항

### 3.1 월 투자 금액

- 0원 이상

### 3.2 연 수익률

- 0.00 이상 ~ 0.99 이하 (0% 이상 99%)
- 입력 시 소수로 입력 (예: 5% → `0.05`)

### 3.3 투자 기간

- 최소: 0개월
- 최대: 999개월
- 단위: 월 또는 년

### 3.4 과세 유형

- 선택지: 비과세 (기본값), 일반 과세 (15.4%), 세금 우대

### 3.5 세금우대 퍼센트

- 범위: 0.00 ~ 0.99 (0% ~ 99%)

## 실행 방법

```shell
java -jar invest72.jar
```

### 실행 과정

```shell
투자 유형을 입력하세요 (예금 or 적금): 적금
💰 투자 기간 단위와 금액을 한 줄로 입력해주세요.

📝 형식:
[단위] [투자금액]

📌 단위 예시:
- "월" → 적금 (매월 납입 금액)
- "년" → 적금 (매년 납입 금액)

📌 예시 입력:
- 월 1000000
- 년 5000000

👉 입력: 
년 12000000
기간 종류를 입력하세요 (월 or 년): 년
기간을 입력하세요 (숫자): 1
이자 방식을 입력하세요 (단리 or 복리): 복리
이자율을 입력하세요 (%): 5
과세 유형을 입력하세요 (일반과세, 비과세, 세금우대): 일반과세
세율을 입력하세요 (세금우대형일 경우 %, 아니면 0): 15.4
원금 합계: 12,000,000원
세전 이자: 330,017원
이자 과세: -50,823원
세후 수령액: 12,279,194원
```

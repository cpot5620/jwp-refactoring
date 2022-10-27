# 키친포스

## 요구 사항

## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |

## 기능 요구 사항

1. 상품(Product)
    1. 등록하기
        - 이름과 가격을 입력받는다.
        - 이름, 가격은 null일 수 없다.
        - 가격은 0보다 작을 수 없다.
    2. 전체 조회


2. 메뉴 그룹(Menu Group)
    1. 등록하기
        - 이름을 입력받는다.
        - 이름은 null일 수 없다.
    2. 전체 조회


3. 메뉴(Menu)
    1. 등록하기
        - 이름, 가격, 속한 메뉴 그룹 id, 구성 상품들의 id와 갯수를 입력받는다.
        - 이름, 가격, 상품 갯수는 null일 수 없다.
        - 가격은 0보다 작을 수 없다.
        - 속한 메뉴 그룹이 존재하지 않으면 안된다.
        - 구성 상품들이 존재하지 않으면 안된다.
        - 메뉴의 가격이 구성 상품들의 총액보다 비싸면 안된다.
    2. 전체 조회


4. 테이블(Table)
    1. 등록하기
        - 손님 수와 테이블이 비어있는지 여부를 입력받는다.
        - 손님 수와 테이블의 사용유무는 null일 수 없다.
    2. 전체 조회
    3. 테이블 사용 유무 변경
    4. 이용 손님 수 변경


5. 테이블 그룹핑(Table Group)
    1. 등록하기
        - 그룹화할 테이블들의 id를 입력받는다.
        - 그룹화할 테이블들이 존재하지 않으면 안된다.
        - 그룹화할 테이블들이 null이거나 2개 미만이면 안된다.
        - 그룹화할 테이블 중에 비지 않은 테이블이 있으면 안된다.
    2. 그룹화 제거
        - 이미 주문이 진행 중이면 삭제할 수 없다.


6. 주문(Order)
    1. 등록하기
        - 주문 받은 테이블 id와 메뉴,갯수를 입력받는다.
        - 주문받은 메뉴가 존재하지 않으면 안된다.
        - 없는 메뉴는 주문받을 수 없다.
        - 주문 받은 테이블이 존재하지 않으면 안된다.
    2. 전체 조회
    3. 주문 상태 변경
        - 주문 id와 상태를 입력받는다.
        - 존재하지 않는 주문을 변경할 수 없다.
        - 이미 완료된 주문을 또 완료 상태로 변경 요청할 수 없다.

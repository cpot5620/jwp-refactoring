# 키친포스

## 요구 사항

### 상품

- 상품 이름과 가격으로 상품을 등록할 수 있다.

- 상품의 가격은 0원 이상이다.
- 상품의 이름은 1글자 이상이다.

### 메뉴

- 메뉴를 메뉴 그룹에 등록할 수 있다.
- 상품과 수량을 가지는 메뉴 상품을 지정한 이름과 가격으로 메뉴에 등록할 수 있다.

- 메뉴의 가격은 0원 이상이다.
- 메뉴의 이름은 1글자 이상이다.
- 메뉴는 1개 이상의 메뉴 상품을 가진다.
- 메뉴 상품의 수량은 1개 이상이다.

### 테이블

- 빈 테이블을 등록할 수 있다.
- 주문 테이블로 등록할 수 있다.
- 단체 테이블을 지정할 수 있다.
    - 단체 지정된 테이블은 주문 테이블로 변경된다.

- 빈 테이블은 손님 수를 추가할 수 없다.
- 주문 테이블은 손님 수를 변경할 수 있다.
- 단체 지정을 통해 개별 주문 테이블을 그룹화할 수 있다.
- 빈 테이블을 단체 지정할 수 있다.
- 이미 단체 지정된 테이블은 다른 테이블과 그룹화할 수 없다.
- 단체 지정된 테이블은 빈 테이블로 변경할 수 없다.
- 주문이 등록되거나 조리 중인 테이블은 빈 테이블로 변경할 수 없다.

### 주문

- 주문 가능한 테이블에 주문을 등록할 수 있다.
- 주문 상태를 조리, 완료로 변경할 수 있다.

- 빈 테이블에 주문을 등록할 수 없다.
- 완료된 주문은 변경할 수 없다.

## 용어 사전

| 한글명      | 영문명              | 설명                            |
|----------|------------------|-------------------------------|
| 상품       | product          | 메뉴를 관리하는 기준이 되는 데이터           |
| 메뉴 그룹    | menu group       | 메뉴 묶음, 분류                     |
| 메뉴       | menu             | 메뉴 그룹에 속하는 실제 주문 가능 단위        |
| 메뉴 상품    | menu product     | 메뉴에 속하는 수량이 있는 상품             |
| 금액       | amount           | 가격 * 수량                       |
| 주문 테이블   | order table      | 매장에서 주문이 발생하는 영역              |
| 빈 테이블    | empty table      | 주문을 등록할 수 없는 주문 테이블           |
| 주문       | order            | 매장에서 발생하는 주문                  |
| 주문 상태    | order status     | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정    | table group      | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목    | order line item  | 주문에 속하는 수량이 있는 메뉴             |
| 매장 식사    | eat in           | 포장하지 않고 매장에서 식사하는 것           |

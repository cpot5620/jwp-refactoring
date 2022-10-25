# 키친포스

## 요구 사항

### 상품
- 이름과 가격으로 상품을 생성할 수 있다. 
  - 가격은 0원 보다 같거나 커야한다.
- 상품 목록을 조회할 수 있다. 

### 메뉴 그룹
- 이름으로 메뉴 그룹을 생성할 수 있다. 
- 메뉴 그룹 목록을 조회할 수 있다. 

### 메뉴
- 이름, 가격, 메뉴 그룹 id, list(상품 id, 상품 수량)로 메뉴를 생성할 수 있다. 
  - 가격은 0원 보다 같거나 커야한다.
  - 해당 id의 메뉴 그룹이 있어야 한다.
  - 해당 id의 상품이 있어야한다.
  - 각 상품의 금액을 더한 값 보다 메뉴의 가격이 같거나 작아야한다.
- 메뉴 목록을 조회할 수 있다. 

### 테이블
- 방문한 손님 수와 비었는지 여부로 테이블을 생성할 수 있다. 
- 테이블 목록을 조회할 수 있다. 
- 특정 id의 테이블의 비었는지 여부를 변경할 수 있다. 
  - 해당 id의 테이블이 있어야한다.
  - 테이블에 이미 지정된 단체 지정이 있으면 안된다.
  - 테이블의 주문 상태는 조리, 식사 중 하나여야 한다.
- 특정 id의 테이블의 방문한 손님 수를 변경할 수 있다. 
  - 방문한 손님 수는 0보다 같거나 커야한다.
  - 해당 id의 테이블이 있어야한다.

### 단체 지정
- 주문 테이블의 id 목록으로 단체 지정을 생성한다.
  - 주문 테이블의 id는 2개 이상이어야 한다.
- 특정 id의 단체 지정을 삭제할 수 있다. 
  - 해당 id의 단체 지정이 있어야 한다.
  - 단체 지정의 주문 상태는 조리, 식사 중 하나여야 한다.

### 주문
- 주분 테이블 id, 주문 항목 리스트(메뉴 id, 수량)로 주문을 생성할 수 있다. 
  - 주문 항목 리스트는 비어있으면 안된다.
  - 해당 id의 주문 테이블이 있어야한다.
  - 주문의 상태는 조리로 시작된다.
- 주문 목록을 조회할 수 있다. 
- 특정 id의 주문의 상태를 변경할 수 있다.
  - 상태에는 조리, 식사, 계산 완료이 있다.
  - 해당 id의 주문이 있어야한다.
  - 해당 id의 주문 상태가 이미 계산 완료면 안된다.

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

## 클래스 다이어그램

```uml
MenuGroup "1" -- "*" Menu
Menu "1" -- "*" MenuProduct
MenuProduct "*" -- "1" Product

TableGroup "1" -- "*" OrderTable
OrderTable "1" -- "*" Order
Order "1" -- "*" OrderLineItem
OrderLineTiem "*" -- "1" Menu
```

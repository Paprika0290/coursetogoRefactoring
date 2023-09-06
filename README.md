# coursetogoRefactoring
coursetogo 프로젝트를 리팩토링하고 있습니다.

![image](https://github.com/Paprika0290/coursetogoRefactoring/assets/59499235/03033efa-6ed0-44f0-aaf2-7cc27a43b317)
<c:forEach>내에서 axios로 userPhoto를 바꾸어주려 했으나, <c:forEach>는 비동기 처리를 기다려주지 않기 때문에 반복문 내에서 비동기처리가 불가함.
controller에서 작업후 List를 넘겨주는 방식을 사용해야 할 듯

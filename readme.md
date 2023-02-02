

# 깃 전략

## 깃 플로우 전략

![0](https://user-images.githubusercontent.com/82326116/211334754-02dfd431-ae00-45ca-982c-70ddde5efe27.png)



## **Git-flow 전략 간단하게 살펴보기**

Git-flow를 사용했을 때 작업을 어떻게 하는지 살펴보기 전에 먼저 Git-flow에 대해서 간단히 살펴보겠습니다.Git-flow에는 5가지 종류의 브랜치가 존재합니다. 항상 유지되는 메인 브랜치들(master, develop)과 일정 기간 동안만 유지되는 보조 브랜치들(feature, release, hotfix)이 있습니다.

- master : 제품으로 출시될 수 있는 브랜치
- develop : 다음 출시 버전을 개발하는 브랜치
- feat : 기능을 개발하는 브랜치
- fix : 출시 버전에서 발생한 버그를 수정 하는 브랜치

Git Flow에서 가장 중요한 점은, **브런치의 역할에 따라 무조건 지정된 브랜치에만 머지해야한다는 점** 입니다. 기능을 구현한 브랜치는 무조건 develop 브랜치에 머지해야 하고, develop 브랜치에 있는 기능들을 출시(배포)할 때에만 master 브랜치에 머지해야 한다는 것입니다. Git Flow를 사용하면서 개발할 때엔 다음과 같은 단계를 거쳐 머지합니다.





- {브랜치}/{백엔드 or 프론트엔드}/{개발 내용}
- ex 백엔드 로그인 개발시
  - git branch -c feat/BE/login
- 프론트 로그인 개발시
  - git branch -c feat/FE/login



## 유저 플로우

1. 백엔드에서 로그인 기능을 개발하려고 한다.
2. `git branch feat/BE/login` 명령어로 깃 브랜치를 만든다.
3. `git switch feat/BE/login` 해당 브랜치로 이동한다.
4. 작업을 한다.
5. `git add 파일명` 깃 add를 한다.
6. `git commit -m 'feat : 로그인 기능 구현 완료'` 또는 `'fix : 로그인 기능 수정'` 
   - feat의 경우 생성하거나 삭제 시,
   - fix의 경우 수정사항이 있을 시
7. `git push origin feat/BE/login` 해당 브랜치에 푸쉬를 한다.
8. 머지 리퀘스트를 기다린다.
9. `git switch develop` 머지가 완료되면 develop 브랜치로 이동한다.
10. `git pull origin develop` 머지된 develop 브랜치를 풀받는다.
11. `git branch -d feat/BE/login` 사용했던 브랜치를 로컬에서 삭제한다.
12. `git push origin --delete feat/BE/login` 사용했던 브랜치를 원격 레파지토리에서 삭제한다.
13. develop 브랜치에서 다시 하위 브랜치로 분기한다.



 

11

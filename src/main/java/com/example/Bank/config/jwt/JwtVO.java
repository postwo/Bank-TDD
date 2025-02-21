package com.example.Bank.config.jwt;

/*1. SECRET은 노출되면 안된다 (환경변수로 등록해서 쓰는게 좋다 ) 하지만 지금은 연습이여서 이렇게 작성
  2. 리플래시 토큰(ex) 일주일이 지난 엑세스 토큰을 다시 재생성 해주는 역할 == 자동 로그인) 지금은 하지 않는다
* */
public interface JwtVO { // 프론트는 서버 공개키 필요 없다

    // Hs256(대칭키) = 대칭키를 프론트 서버 두군다가지고 있어야 하면 사용하면 안되지만 지금은 서버에서만 키를 가지고 있으면 되기 떄문에 대칭키를 사용
    public static final String SECRET = "메타코딩";

    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7 ;//만료시간 = 초 분 1시 24시 7일

    public static final String TOKEN_PREFIX = "Bearer "; // 한칸 꼭 띄우기

    public static final String HEADER = "Authorization";
}

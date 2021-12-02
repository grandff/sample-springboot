package com.kjm.test.convert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듬
@NoArgsConstructor  // 파라미터가 없는 기본 생성자 생성
@ToString
@Getter
@Setter
public class TestHello {
    private String name;
    private String gender;    
}

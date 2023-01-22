package com.practice.chatapiserver.util;

// 페이징에 필요한 데이터를 제공 및 가공 해주는 클래스
public class Paging {
    public final static int perPage = 20;

    public static int getLimit(){
        return Paging.perPage;
    }
    public static int getOffset(int pageNum){
        return Paging.perPage * pageNum;
    }
}

package com.hjh.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//分页对象 包含一个对象集合和页码集合
@Data
public class PaginationDTO<E> {
    List<E> list;
    List<Integer> pageList;
    int pageSize;
    //当前页
    int currentPage;
    //总页数
    int pageCount;
    boolean hasPrevious = true;
    boolean hasNext = true;

    public PaginationDTO(List list, int currentPage,int pageCount,int pageSize){
        this.list = list;
        this.currentPage = currentPage;
        this.pageCount = pageCount;
        this.pageSize = pageSize;
        pageList = new ArrayList<>();
        for (int i =1; i<= pageCount; i++){
            this.pageList.add(i);
        }

        if (currentPage >= pageCount){
            this.hasNext = false;
        }

        if (currentPage <= 1){
            this.hasPrevious = false;
        }

    }

}

package com.capstone.grocery.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    private Integer pageIndex;
    private Integer pageSize;
    private Long totalPages;
    private Long total;
}

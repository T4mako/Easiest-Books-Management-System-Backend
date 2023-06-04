package com.t4mako.bookmanagesystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author T4mako
 * @date 2023/5/28 10:44
 */
@Data
public class Lend {
    private String id;
    private Integer stuid;
    private Integer bookid;
    private LocalDateTime btime;
    private LocalDateTime rtime;
    private String bookName;
}

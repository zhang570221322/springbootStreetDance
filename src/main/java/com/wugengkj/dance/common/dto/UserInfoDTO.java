package com.wugengkj.dance.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wugengkj.dance.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author leaf
 * <p>date: 2018-05-11 17:18</p>
 * <p>version: 1.0</p>
 */
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoDTO {
    private String name;
    private String sex;
    private Integer age;
    private String phone;
    private String qq;
    private String avatar;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validTime;
    private Ticket ticket;
}

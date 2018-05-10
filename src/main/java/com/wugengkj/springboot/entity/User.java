package com.wugengkj.springboot.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户
 *
 * @author leaf
 * <p>date: 2017-12-28 21:35</p>
 */
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @TableId(type = IdType.INPUT)
    private String openId;
    private String name;
    private String sex;
    private Integer age;
    private String phone;
    private String qq;
    private Integer status;
    private Long ticketId;
    private Date createTime;
    private Date postTime;
}

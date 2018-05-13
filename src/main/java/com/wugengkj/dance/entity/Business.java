package com.wugengkj.dance.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 商家信息
 *
 * @author leaf
 * <p>date: 2018-05-08 11:15</p>
 * <p>version: 1.0</p>
 */
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Business {
    @TableId
    private Long id;
    private String name;
    @JsonIgnore
    private String appid;
    private String content;
    private String detail;
    private String regular;
    private String address;
    private String ticketAddress;
    private Integer totalTicket;
    private Integer surplusTicket;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}

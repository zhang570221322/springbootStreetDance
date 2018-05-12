package com.wugengkj.dance.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author leaf
 * <p>date: 2018-05-12 16:02</p>
 * <p>version: 1.0</p>
 */
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Star {
    @TableId
    private Long id;
    private String name;
    private String content;
    private String blank1;
    private String blank2;
    private String blank3;
    private String blank4;
    private String blank5;
    private String blank6;
    private String blank7;
    private String blank8;
}

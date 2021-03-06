package com.zfk.springboot_jsp_shiro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Perm {

    /**
     * 权限对应着资源
     */

    private String id;
    private String name;
    private String url;

}

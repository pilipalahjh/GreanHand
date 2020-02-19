package com.hjh.community.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(type= IdType.AUTO)
    int id;
    @TableField
    String name;
    @TableField("account_no")
    String accountNo;
    @TableField("token")
    String token;
    @TableField("gmt_create")
    long gmtCreate;
    @TableField("gmt_modify")
    long gmtModify;
}

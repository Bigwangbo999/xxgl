package com.ruoyi.job.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

/**
 * 创建实体类
 */
@Entity
@Table(name="syuser")
@Data
public class SyUserPo implements UserDetails {

    /**
     * id
     */
    @Id
    @NotNull
    private Long id;

    /**
     * 年龄
     */
    private Long age;

    /**
     * 创建时间
     */
    private Date createdatatime;

    /**
     * 登录名
     */
    @NotNull
    private String loginname;

    /**
     * 照片
     */
    private String photo;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 性别
     */
    private String sex;

    /**
     * 更新时间
     */
    private Date updatedatetime;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门id
     */
    private String userempid;

    /**
     * 组织id
     */
    private String userorgid;

    /**
     *
     */
    private String userdpid;

    /**
     * 机构编码
     */
    private String orgcode;

    /**
     * 是否离职(0:在职 1:离职)
     */
    private String is_leave;

    /**
     * 主要角色
     */
    private String main_role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

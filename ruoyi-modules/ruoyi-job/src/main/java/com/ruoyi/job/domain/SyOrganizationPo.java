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
@Table(name="syorganization")
@Data
public class SyOrganizationPo implements UserDetails {
    /**
     * id
     */
    @Id
    @NotNull
    private String id;

    /**
     * 地址
     */
    private String address;

    /**
     * 编码
     */
    private String code;

    /**
     * 创建时间
     */
    private Date createdatetime;

    /**
     * 图标
     */
    private String iconcls;

    /**
     * 用户名
     */
    private String name;

    /**
     * 序号
     */
    private Long seq;

    /**
     * 更新时间
     */
    private Date updatedatetime;

    /**
     * 单位id
     */
    private String syorganization_id;

    /**
     * 级别 1,0
     */
    private Long levelid;


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

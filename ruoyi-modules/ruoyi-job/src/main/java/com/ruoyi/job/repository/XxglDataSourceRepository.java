package com.ruoyi.job.repository;

import com.ruoyi.job.domain.SyOrganizationPo;
import com.ruoyi.job.domain.SyUserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class XxglDataSourceRepository {

    private final JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public XxglDataSourceRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    /**
     * 根据账号查询用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    public SyUserPo findById(Number id){
        RowMapper<SyUserPo> rowMapper =new BeanPropertyRowMapper<>(SyUserPo.class);
        return jdbcTemplate.queryForObject("select * from syuser WHERE id = ?",rowMapper,id);
    }

    /**
     * 删除sys_user表全部信息
     */
    public void deleteAllBack() {
        this.jdbcTemplate.update("delete from syuser");
    }

    /**
     * 删除syorganization表全部信息
     */
    public void deleteAllOrganizationBack() {
        this.jdbcTemplate.update("delete from syorganization");
    }


    /**
     * 保存用户
     *
     * @param syUserPo 用户实体
     */
    public void saveSyUser(SyUserPo syUserPo) {
        this.entityManager.persist(syUserPo);
    }

    /**
     * 保存用户
     *
     * @param syOrganizationPo 用户实体
     */
    public void saveSyorganization(SyOrganizationPo syOrganizationPo) {
        this.entityManager.persist(syOrganizationPo);
    }
}

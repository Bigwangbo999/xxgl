package com.ruoyi.job.repository;

import com.ruoyi.job.domain.SyOrganizationPo;
import com.ruoyi.job.domain.SyUserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class SsheDataSourceRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SsheDataSourceRepository(@Qualifier("ssheDataSourceJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<SyUserPo> findAllUserIncludeAdmin() {
        RowMapper<SyUserPo> rowMapper=new BeanPropertyRowMapper<>(SyUserPo.class);
        return jdbcTemplate.query("select * from syuser",rowMapper);
    }

    public List<SyOrganizationPo> findAllOrganizationIncludeAdmin(){
        RowMapper<SyOrganizationPo> rowMapper=new BeanPropertyRowMapper<>(SyOrganizationPo.class);
        return jdbcTemplate.query("select * from syorganization",rowMapper);
    }

}
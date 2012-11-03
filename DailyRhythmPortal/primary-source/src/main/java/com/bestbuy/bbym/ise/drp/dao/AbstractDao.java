package com.bestbuy.bbym.ise.drp.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 
 * @author a904002
 */
@Repository
public class AbstractDao {

    @Autowired
    private DataSource ds;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;

    private boolean useNextSequenceOnCreate = true;

    public DataSource getDs() {
	return ds;
    }

    public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
    }

    public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
	return namedJdbcTemplate;
    }

    public void setNamedJdbcTemplate(NamedParameterJdbcTemplate namedJdbcTemplate) {
	this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUseNextSequenceOnCreate() {
	return useNextSequenceOnCreate;
    }

    public void setUseNextSequenceOnCreate(boolean useNextSequenceOnCreate) {
	this.useNextSequenceOnCreate = useNextSequenceOnCreate;
    }

    public PlatformTransactionManager getTransactionManager() {
	return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
	this.transactionManager = transactionManager;
    }
}

package com.bestbuy.bbym.ise.drp.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.bestbuy.bbym.ise.util.jdbc.DatabaseScript;

/**
 * Base class for all DAO tests.
 */
public abstract class BaseDaoTest {

    protected static EmbeddedDatabase db;
    private static EmbeddedDatabaseBuilder builder;

    /**
     * Creates the database.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	builder = new EmbeddedDatabaseBuilder();
	builder.setType(EmbeddedDatabaseType.H2);
	builder.addScript("ise_db_scripts/create_drp_ise_db.sql");
	builder.addScript("ise_db_scripts/oracle_functions.sql");
	builder.addScript("ise_db_scripts/create_gsp_cancel.sql");
	builder.addScript("ise_db_scripts/create_ise_loanerPhone.sql");
	builder.addScript("ise_db_scripts/create_tradein_device_details.sql");
	builder.addScript("ise_db_scripts/create_triage_details.sql");
	builder.addScript("ise_db_scripts/create_tradein.sql");
	//builder.addScript("ise_db_scripts/create_retExch_details.sql");
	builder.addScript("ise_db_scripts/create_config_details.sql");
	builder.addScript("ise_db_scripts/create_scoreboard_details.sql");
	builder.addScript("ise_db_scripts/create_recsheet_gsp_details.sql");
	db = builder.build();
    }

    /**
     * Drops the database.
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
	DatabaseScript.execute("ise_db_scripts/drop_drp_ise_db.sql", db);
	DatabaseScript.execute("ise_db_scripts/drop_gsp_cancel.sql", db);
	DatabaseScript.execute("ise_db_scripts/drop_tradein_device_details.sql", db);
	DatabaseScript.execute("ise_db_scripts/drop_triage_details.sql", db);
	DatabaseScript.execute("ise_db_scripts/drop_ise_schema.sql", db);
	db.shutdown();
    }

    /**
     * Clears the data from all tables.
     */
    @After
    public void tearDown() throws Exception {
	DatabaseScript.execute("ise_db_scripts/clean_drp_ise_db.sql", db);
	DatabaseScript.execute("ise_db_scripts/clean_gsp_cancel.sql", db);
	DatabaseScript.execute("ise_db_scripts/clean_ise_loanerPhone.sql", db);
	DatabaseScript.execute("ise_db_scripts/clean_tradein_device_details.sql", db);
	DatabaseScript.execute("ise_db_scripts/clean_triage_details.sql", db);
	DatabaseScript.execute("ise_db_scripts/clean_scoreboard_details.sql", db);
    }

}

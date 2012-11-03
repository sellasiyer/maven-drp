package com.bestbuy.bbym.ise.drp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.exception.DataAccessException;

@Repository("essentialsDao")
public class EssentialsDaoImpl extends AbstractDao implements EssentialsDao {

    @Override
    public Map<Long, String> getEssentials(long recommendationId) throws DataAccessException {
	final HashMap<Long, String> preferences = new HashMap<Long, String>();
	String sql = "SELECT * FROM BST_ISE_SCH01.recommendation_essential re" + " WHERE re.recommendation_id = ?";

	JdbcTemplate tplSelect = this.getJdbcTemplate();

	tplSelect.query(sql, new Object[] {recommendationId }, new RowMapper<Map<Long, String>>() {

	    @Override
	    public Map<Long, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long id = new Long(rs.getLong("recommendation_esential_typ_id"));
		String value = rs.getString("comments");
		preferences.put(id, value);
		return null;

	    }
	});

	return preferences;
    }

    @Override
    public void saveEssentials(Map<Long, String> preferences, final Long recommendationId, final User employee)
	    throws DataAccessException {

	this.clearEssentials(recommendationId);
	for(final Map.Entry<Long, String> preference: preferences.entrySet()){
	    this.insertOneEssential(preference.getKey(), preference.getValue(), recommendationId, employee);
	}
    }

    private void insertOneEssential(final Long recEssId, final String value, final long recommendationId,
	    final User employee) throws DataAccessException {
	JdbcTemplate tplInsert = this.getJdbcTemplate();
	final String sql = "INSERT INTO BST_ISE_SCH01.recommendation_essential "
		+ "( recommendation_essential_id, recommendation_id, recommendation_esential_typ_id, comments, created_by, created_on)"
		+ " VALUES ( BST_ISE_SCH01.seq_recommendation_essential.nextval, ?, " // recId
		+ " ?, ?, ?,  CURRENT_TIMESTAMP)"; //  comments, userId

	tplInsert.update(new PreparedStatementCreator() {

	    @Override
	    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement(sql, new String[] {"recommendation_essential_id" });
		int i = 0;
		ps.setLong(++i, recommendationId); // recommendation_essential.recommendation_id
		ps.setLong(++i, recEssId); // recommendation_essential_id
		ps.setString(++i, value); // recommendation_essential.comments
		ps.setString(++i, employee.getUserId()); // created_by
		return ps;
	    }
	});
    }

    private void clearEssentials(long recommendationId) throws DataAccessException {
	String sql = "DELETE FROM BST_ISE_SCH01.recommendation_essential re"
		+ " WHERE re.recommendation_id = :recommendation_id";
	NamedParameterJdbcTemplate tplDelete = this.getNamedJdbcTemplate();
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("recommendation_id", recommendationId);

	tplDelete.update(sql, params);
    }
}

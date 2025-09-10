package deptDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class DeptDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public DeptDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource); 
	}
	
	public void insert(Dept dept) {
		jdbcTemplate.update("insert into dept (deptno,dname,loc) values (?,?,?)",dept.getDeptno(),dept.getDname(),dept.getLoc());
		
	}
	
	public Dept select(int deptno) {
		List<Dept> results = jdbcTemplate.query("select * from dept where deptno = ?", new RowMapper<Dept>() {

			@Override
			public Dept mapRow(ResultSet rs, int rowNum) throws SQLException {
				Dept dept = new Dept(rs.getInt("deptno"),rs.getString("dname"),rs.getString("loc"));
				return dept;
			}		
		},deptno);
		return results.isEmpty() ? null : results.get(0);
		
	}
	
	public List<Dept> selectAll(){
		List<Dept> results = jdbcTemplate.query("select * from dept",new RowMapper<Dept>() {

			@Override
			public Dept mapRow(ResultSet rs, int rowNum) throws SQLException {
				Dept dept = new Dept(rs.getInt("deptno"),rs.getString("dname"),rs.getString("loc"));
				return dept;
			}
		
		});
		return results;
	}
	
}

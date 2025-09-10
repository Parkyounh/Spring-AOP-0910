package chap08;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class MemberDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public MemberDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void insert(Member member) {
		jdbcTemplate.update("insert into member (email,name,password) values (?,?,?,?)",member.getEmail(),member.getName(),member.getPassword(),LocalDateTime.now());
	}
	
	public void insert2(Member member) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update((con)->{
			
			PreparedStatement pstmt = con.prepareStatement("insert into member(email,password,name,regdate) value(?,?,?,?)",new String[] {"id"});
			pstmt.setString(1,member.getEmail());
			pstmt.setString(2,member.getPassword());
			pstmt.setString(3,member.getName());
			pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegdate()));
			return pstmt;
				
		},keyHolder);
		
//		jdbcTemplate.update(new PreparedStatementCreator() {
//
//			@Override
//			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//				PreparedStatement pstmt = con.prepareStatement("insert into member(email,password,name,regdate) value(?,?,?,?)",new String[] {"id"});
//				pstmt.setString(1,member.getEmail());
//				pstmt.setString(2,member.getPassword());
//				pstmt.setString(3,member.getName());
//				pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegdate()));
//				return pstmt;
//				
//			}
//			
//		},keyHolder);
		Number keyValue = keyHolder.getKey();
		member.setId(keyValue.longValue());
	}
	
	public Member selectByEmail1(String email) {
		List<Member> results = jdbcTemplate.query("select * from member where email = ?", new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member mem = new Member(rs.getLong("id"),rs.getString("email"),rs.getString("password"),rs.getString("name"),rs.getTimestamp("regdate").toLocalDateTime());
				return mem;
			}
			
		},email);
		return results.isEmpty() ? null : results.get(0);
	}
	
	public Member selectByEmail2(String email) {
		return jdbcTemplate.queryForObject("select * from member where email = ?", new BeanPropertyRowMapper<>(Member.class),email);
	}
	
	
	
	public Member selectByEmail3(String email) {
		List<Member> results = jdbcTemplate.query("select * from member where email = ?", (ResultSet rs, int rowNum) ->{

			
				Member mem = new Member(rs.getLong("id"),rs.getString("email"),rs.getString("password"),rs.getString("name"),rs.getTimestamp("regdate").toLocalDateTime());
				return mem;
			}
			
		,email);
		return results.isEmpty() ? null : results.get(0);
	}	
	
	public List<Member> selectAll(){
		return jdbcTemplate.query("select * from member",(ResultSet rs, int rowNum) ->{

			
			Member mem = new Member(rs.getLong("id"),rs.getString("email"),rs.getString("password"),rs.getString("name"),rs.getTimestamp("regdate")==null? null:rs.getTimestamp("regdate").toLocalDateTime());
			return mem;
		});
	}
	
	
	
	public int count() {
		return jdbcTemplate.queryForObject("select count(*) from member",Integer.class);
	}
}

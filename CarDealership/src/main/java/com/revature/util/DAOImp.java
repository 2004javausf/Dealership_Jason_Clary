package com.revature.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOImp {
	//GlobAL Variable for Connection
	public static ConnFactory cf=ConnFactory.getInstance();
	Connection conn= cf.getConnection();

	
	public void createPowers(String powerName) throws SQLException {
	//Prepared Statement- pre compiled - compiled in Java/ Statement is complied in sql
		
		String sql= "INSERT INTO POWERS VALUES (POWSEQ.NEXTVAL,?)";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1, powerName);
		ps.executeUpdate();
	}
}

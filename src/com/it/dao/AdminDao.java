package com.it.dao;

import com.it.enitiy.Admin;
import com.kaishengit.tools.BeanRowMapper;
import com.kaishengit.tools.DBHelper;

public class AdminDao {
	DBHelper<Admin> db = new DBHelper<>(Conf.DB_URL);
	BeanRowMapper<Admin> be = new BeanRowMapper<>(Admin.class);

	public boolean find(String name, String pwd) {
		String sql = "select * from admin where name=? and password=?";
		Admin admin = db.queryOne(sql, be, name, pwd);
		return admin != null;
	}

}

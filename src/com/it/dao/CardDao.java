package com.it.dao;

import com.it.enitiy.Admin;
import com.it.enitiy.Book;
import com.it.enitiy.Card;
import com.kaishengit.tools.BeanRowMapper;
import com.kaishengit.tools.DBHelper;

public class CardDao {
	DBHelper<Card> db = new DBHelper<>(Conf.DB_URL);
	BeanRowMapper<Card> be = new BeanRowMapper<>(Card.class);

	public boolean addCard(Card card) {
		String sql = "INSERT into card(`code`,`name`,`tel`) VALUES(?,?,?)";
		int result = db.doUpdate(sql, card.getCode(), card.getName(), card.getTel());
		return result == 1;
	}
	
	public Card findOne(String coad) {
		String sql = "select * from card where code=?";
		return db.queryOne(sql, be,coad);
	}
}

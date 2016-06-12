package com.it.dao;

import java.util.List;

import com.it.enitiy.Book;
import com.it.enitiy.Card;
import com.kaishengit.tools.BeanRowMapper;
import com.kaishengit.tools.DBHelper;

public class BookDao {
	DBHelper<Book> db = new DBHelper<>(Conf.DB_URL);
	BeanRowMapper<Book> be = new BeanRowMapper<>(Book.class);
	
	public boolean addBook(Book book){
		String sql = "INSERT into book(`code`,`title`,`author`,`publishing`,`total`,`count`) "
				+ "VALUES(?,?,?,?,?,?)";
		int res = db.doUpdate(sql, book.getCode(),book.getTitle(),book.getAuthor(),
				book.getPublishing(),book.getTotal(),book.getCount());
		return res==1;
	}
	
	public boolean modifyBook(Book book){
		String sql = "update book set title=?,author=?,publishing=?,total=?,count=? where code=?";
		int res = db.doUpdate(sql, book.getTitle(),book.getAuthor(),
				book.getPublishing(),book.getTotal(),book.getCount(),book.getCode());
		return res==1;
	}
	
	public boolean removeBook(String code){
		String sql = "delete from book where code=?";
		int res = db.doUpdate(sql, code);
		return res==1;
	}
	
	public List<Book> findBook(String str){
		String sql = "select * from book where title like '%"+str+"%' "
				+ "or author like '%"+str+"%' or publishing like '%"+str+"%'";
		return db.queryList(sql, be);
	}
	
	public List<Book> allBooks(){
		String sql = "select * from book";
		return db.queryList(sql, be);
	}

	public Book findOne(String code) {
		String sql = "select * from book where code=?";
		return db.queryOne(sql, be,code);
	}

	public boolean saveCount(Book b){
		String sql = "update book set count=? where code=?";
		return db.doUpdate(sql, b.getCount(),b.getCode())==1;
	}

	public List<Book> findBooksByCard(String ccode) {
		String sql = "SELECT book.* from book INNER JOIN borrow on borrow.bid=book.id INNER JOIN card on card.id=borrow.cid WHERE card.`code`=?";
		return db.queryList(sql, be, ccode);
	}
	
}

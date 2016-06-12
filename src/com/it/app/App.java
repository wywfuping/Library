package com.it.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.it.dao.AdminDao;
import com.it.dao.BookDao;
import com.it.dao.BorrowDao;
import com.it.dao.CardDao;
import com.it.enitiy.Book;
import com.it.enitiy.Borrow;
import com.it.enitiy.Card;

public class App {
	AdminDao admindao = new AdminDao();
	BookDao bookdao = new BookDao();
	BorrowDao borrowdao = new BorrowDao();
	CardDao carddao = new CardDao();

	Scanner sc = new Scanner(System.in);

	public void start() {
		Login();
		while (true) {

			System.out.println("====图书管理====");
			System.out.println("1.借书");
			System.out.println("2.还书");
			System.out.println("3.添加新书");
			System.out.println("4.修改书籍");
			System.out.println("5.删除书籍");
			System.out.println("6.查看所有书籍");
			System.out.println("7.查看指定书籍");
			System.out.println("8.新建图书证");
			System.out.println("9.退出系统");
			System.out.println("===============");
			System.out.print("请选择：");
			String input = sc.next();
			if (input.equals("1")) {
				borrowBook();
			} else if (input.equals("2")) {
				reBook();
			} else if (input.equals("3")) {
				newBook();
			} else if (input.equals("4")) {
				updateBook();
			} else if (input.equals("5")) {
				deleteBook();
			} else if (input.equals("6")) {
				showAllBook();
			} else if (input.equals("7")) {
				findOneBook();
			} else if (input.equals("8")) {
				newCard();
			} else if (input.equals("9")) {
				System.out.println("退出成功!");
				System.exit(1);
			} else {
				System.out.println("选择错误，请重选!");
			}
		}
	}

	private void reBook() {
		String ccode = getStr("图书证号：");

		List<Book> list = bookdao.findBooksByCard(ccode);

		System.out.println("编号\t书名\t作者\t出版社\t总数\t剩余数量");
		System.out.println("--------------------------------------------------");
		for (Book book : list) {
			System.out.println(book);
		}

		String bcode = getStr("书籍编号：");
		Borrow bor = new Borrow();
		Book b = bookdao.findOne(bcode);
		Card c = carddao.findOne(ccode);

		bor.setBid(b.getId());
		bor.setCid(c.getId());

		Borrow bo = borrowdao.findOne(bor);
		System.out.println(bo.getBtime());

		Date now = new Date();
		long day = ((now.getTime() - bo.getBtime().getTime()) / 1000 / 60 / 60 / 24 + 1);
		SimpleDateFormat df = new SimpleDateFormat("yyyy年M月d日");
		System.out.println("借书日期：" + df.format(bo.getBtime()) + "\t借阅天数：" + day);
		if (borrowdao.remove(bo.getId())) {
			System.out.println("还书成功！");
			b.setCount(b.getCount() + 1);
			bookdao.saveCount(b);
		}

	}

	private void borrowBook() {
		String bcode = getStr("请输入书籍编号：");
		Book b = bookdao.findOne(bcode);
		if (b == null || b.getCount() <= 0) {
			System.out.println("书籍不存在或已被借完！");
			return;
		}

		String ccode = getStr("请输入图书证号：");
		Card c = carddao.findOne(ccode);
		if (c == null) {
			System.out.println("图书证不可用！");
			return;
		}
		Borrow bor = new Borrow();
		bor.setBid(b.getId());
		bor.setCid(c.getId());
		if (borrowdao.add(bor)) {
			System.out.println("借阅成功！");
			b.setCount(b.getCount()  - 1);
			bookdao.saveCount(b);
		} else {
			System.out.println("借阅失败！");
		}

	}

	private void updateBook() {
		Book book = new Book();
		book.setCode(getStr("请输入要修改的书籍编号："));
		book.setTitle(getStr("修改书名为："));
		book.setAuthor(getStr("修改作者为："));
		book.setPublishing(getStr("修改出版社为："));
		book.setTotal(getInt("修改总数为："));
		book.setCount(book.getTotal());

		if (bookdao.modifyBook(book)) {
			System.out.println("修改成功！");
		} else {
			System.out.println("修改失败！");
		}

	}

	private void deleteBook() {
		String code = getStr("请选择所要删除书籍的编号：");
		if (bookdao.removeBook(code)) {
			System.out.println("删除成功");
		} else {
			System.out.println("删除失败");
		}
	}

	private void findOneBook() {
		List<Book> list = bookdao.findBook(getStr("请输入查询条件："));

		System.out.println("编号\t书名\t作者\t出版社\t总数\t剩余数量");
		System.out.println("--------------------------------------------------");
		for (Book book : list) {
			System.out.println(book);
		}
	}

	private void showAllBook() {
		List<Book> list = bookdao.allBooks();

		System.out.println("编号\t书名\t作者\t出版社\t总数\t剩余数量");
		System.out.println("--------------------------------------------------");
		for (Book book : list) {
			System.out.println(book);
		}

	}

	private void newBook() {
		Book book = new Book();
		book.setCode(getStr("编号："));
		book.setTitle(getStr("书名："));
		book.setAuthor(getStr("作者："));
		book.setPublishing(getStr("出版社："));
		book.setTotal(getInt("总数："));
		book.setCount(book.getTotal());

		if (bookdao.addBook(book)) {
			System.out.println("添加成功！");
		} else {
			System.out.println("添加失败！");
			newBook();
		}
	}

	private void newCard() {
		Card card = new Card();
		card.setCode(getStr("编号："));
		card.setName(getStr("姓名："));
		card.setTel(getStr("电话："));

		if (carddao.addCard(card)) {
			System.out.println("新建成功！");
		} else {
			System.out.println("新建失败！");
			newCard();
		}
	}

	private void Login() {
		String name = getStr("请输入用户名：");
		String pwd = getStr("请输入密码：");

		if (admindao.find(name, pwd)) {
			System.out.println("登录成功！");
		} else {
			System.out.println("登录失败！");
			Login();
		}
	}

	private String getStr(String msg) {
		System.out.print(msg);
		return sc.next();
	}

	private int getInt(String msg) {
		System.out.print(msg);
		if (sc.hasNextInt()) {
			return sc.nextInt();
		} else {
			System.out.println(sc.nextInt() + "不是数字，请重新输入！");
			return getInt(msg);
		}
	}
}

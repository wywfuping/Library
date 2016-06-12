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

			System.out.println("====ͼ�����====");
			System.out.println("1.����");
			System.out.println("2.����");
			System.out.println("3.�������");
			System.out.println("4.�޸��鼮");
			System.out.println("5.ɾ���鼮");
			System.out.println("6.�鿴�����鼮");
			System.out.println("7.�鿴ָ���鼮");
			System.out.println("8.�½�ͼ��֤");
			System.out.println("9.�˳�ϵͳ");
			System.out.println("===============");
			System.out.print("��ѡ��");
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
				System.out.println("�˳��ɹ�!");
				System.exit(1);
			} else {
				System.out.println("ѡ���������ѡ!");
			}
		}
	}

	private void reBook() {
		String ccode = getStr("ͼ��֤�ţ�");

		List<Book> list = bookdao.findBooksByCard(ccode);

		System.out.println("���\t����\t����\t������\t����\tʣ������");
		System.out.println("--------------------------------------------------");
		for (Book book : list) {
			System.out.println(book);
		}

		String bcode = getStr("�鼮��ţ�");
		Borrow bor = new Borrow();
		Book b = bookdao.findOne(bcode);
		Card c = carddao.findOne(ccode);

		bor.setBid(b.getId());
		bor.setCid(c.getId());

		Borrow bo = borrowdao.findOne(bor);
		System.out.println(bo.getBtime());

		Date now = new Date();
		long day = ((now.getTime() - bo.getBtime().getTime()) / 1000 / 60 / 60 / 24 + 1);
		SimpleDateFormat df = new SimpleDateFormat("yyyy��M��d��");
		System.out.println("�������ڣ�" + df.format(bo.getBtime()) + "\t����������" + day);
		if (borrowdao.remove(bo.getId())) {
			System.out.println("����ɹ���");
			b.setCount(b.getCount() + 1);
			bookdao.saveCount(b);
		}

	}

	private void borrowBook() {
		String bcode = getStr("�������鼮��ţ�");
		Book b = bookdao.findOne(bcode);
		if (b == null || b.getCount() <= 0) {
			System.out.println("�鼮�����ڻ��ѱ����꣡");
			return;
		}

		String ccode = getStr("������ͼ��֤�ţ�");
		Card c = carddao.findOne(ccode);
		if (c == null) {
			System.out.println("ͼ��֤�����ã�");
			return;
		}
		Borrow bor = new Borrow();
		bor.setBid(b.getId());
		bor.setCid(c.getId());
		if (borrowdao.add(bor)) {
			System.out.println("���ĳɹ���");
			b.setCount(b.getCount()  - 1);
			bookdao.saveCount(b);
		} else {
			System.out.println("����ʧ�ܣ�");
		}

	}

	private void updateBook() {
		Book book = new Book();
		book.setCode(getStr("������Ҫ�޸ĵ��鼮��ţ�"));
		book.setTitle(getStr("�޸�����Ϊ��"));
		book.setAuthor(getStr("�޸�����Ϊ��"));
		book.setPublishing(getStr("�޸ĳ�����Ϊ��"));
		book.setTotal(getInt("�޸�����Ϊ��"));
		book.setCount(book.getTotal());

		if (bookdao.modifyBook(book)) {
			System.out.println("�޸ĳɹ���");
		} else {
			System.out.println("�޸�ʧ�ܣ�");
		}

	}

	private void deleteBook() {
		String code = getStr("��ѡ����Ҫɾ���鼮�ı�ţ�");
		if (bookdao.removeBook(code)) {
			System.out.println("ɾ���ɹ�");
		} else {
			System.out.println("ɾ��ʧ��");
		}
	}

	private void findOneBook() {
		List<Book> list = bookdao.findBook(getStr("�������ѯ������"));

		System.out.println("���\t����\t����\t������\t����\tʣ������");
		System.out.println("--------------------------------------------------");
		for (Book book : list) {
			System.out.println(book);
		}
	}

	private void showAllBook() {
		List<Book> list = bookdao.allBooks();

		System.out.println("���\t����\t����\t������\t����\tʣ������");
		System.out.println("--------------------------------------------------");
		for (Book book : list) {
			System.out.println(book);
		}

	}

	private void newBook() {
		Book book = new Book();
		book.setCode(getStr("��ţ�"));
		book.setTitle(getStr("������"));
		book.setAuthor(getStr("���ߣ�"));
		book.setPublishing(getStr("�����磺"));
		book.setTotal(getInt("������"));
		book.setCount(book.getTotal());

		if (bookdao.addBook(book)) {
			System.out.println("��ӳɹ���");
		} else {
			System.out.println("���ʧ�ܣ�");
			newBook();
		}
	}

	private void newCard() {
		Card card = new Card();
		card.setCode(getStr("��ţ�"));
		card.setName(getStr("������"));
		card.setTel(getStr("�绰��"));

		if (carddao.addCard(card)) {
			System.out.println("�½��ɹ���");
		} else {
			System.out.println("�½�ʧ�ܣ�");
			newCard();
		}
	}

	private void Login() {
		String name = getStr("�������û�����");
		String pwd = getStr("���������룺");

		if (admindao.find(name, pwd)) {
			System.out.println("��¼�ɹ���");
		} else {
			System.out.println("��¼ʧ�ܣ�");
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
			System.out.println(sc.nextInt() + "�������֣����������룡");
			return getInt(msg);
		}
	}
}

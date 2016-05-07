package res;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

//Простой веб-браузер
public class Main extends JFrame implements HyperlinkListener {

	/**
	 * Version 0.1
	 */
	private static final long serialVersionUID = 1L;

	private JPanel panelIcon;
	// кнопки, переключающие страницы
	private JButton backButton, forwardButton;

	// То, куда мы будем писать веб-страницы
	private JTextField locationTextField;

	// дисплей для веб-страниц
	private JEditorPane displayEditorPane;

	// Список страниц
	@SuppressWarnings("rawtypes")
	private ArrayList pageList = new ArrayList();

	// Конструктор браузера
	public Main() {
		// Установка названия для фрейма
		super("Orandetta");
		// установка дизайна по дизайну самой ОС компьютера
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}

		// <---Установка значка для фрейма--->
		ImageIcon i10 = new ImageIcon(getClass().getResource("Browser.jpg"));
		Image im10 = i10.getImage();

		super.setIconImage(im10);
		// <---Установка значка для фрейма--->

		// Размеры
		setSize(740, 580);
		// закрытие окон
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				actionExit();

			}

		});

		URL imgBan = getClass().getResource("Баннер2.jpg");
		ImageIcon imgIco = new ImageIcon(imgBan);
		JLabel labelBan = new JLabel();
		labelBan.setIcon(imgIco);

		setLocationRelativeTo(null);// для отображения браузера по середине
									// экрана

		// Меню в котором будет выход и прочее.
		JMenuBar menuBar = new JMenuBar();

		JMenu gitMenu = new JMenu("Проект");
		gitMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		JMenuItem gitMenuItem = new JMenuItem("Ссылка на проект GitHub");
		gitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent aeGit) {

				JFrame frameGit = new JFrame("Ссылка на GitHub");
				frameGit.setSize(450, 100);
				frameGit.setLocationRelativeTo(null);
				frameGit.setLayout(new BorderLayout());

				JPanel panelForGit = new JPanel();
				GridLayout layoutForGit = new GridLayout(1, 1);
				panelForGit.setLayout(layoutForGit);

				JTextArea textAreaForGit = new JTextArea();
				Font BigFontTR = new Font("TimesRoman", Font.BOLD, 17);
				textAreaForGit.setFont(BigFontTR);
				textAreaForGit.setText("https://github.com/ilyaVoznesensky/Browser.git");

				panelForGit.add(textAreaForGit);

				// доделать ссылку
				frameGit.add(panelForGit, BorderLayout.CENTER);
				frameGit.setVisible(true);

			}
		});
		gitMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JMenu fileNewBrowser = new JMenu("Копия");
		fileNewBrowser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		JMenuItem fileMenuNewBrowser = new JMenuItem("Открыть браузер в новом окне");
		fileMenuNewBrowser.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main SecondPage = new Main();
				SecondPage.show();
			}
		});
		fileMenuNewBrowser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fileNewBrowser.add(fileMenuNewBrowser);

		JMenu fileMenu = new JMenu("Выход");
		fileMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem fileExitMenuItem = new JMenuItem("Выход из программы", KeyEvent.VK_X);
		fileExitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);// закрытие
			}

		});
		fileExitMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// добавление компонентов
		gitMenu.add(gitMenuItem);
		fileMenu.add(fileExitMenuItem);
		menuBar.add(gitMenu);
		menuBar.add(fileNewBrowser);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		// Прикрутка кнопок к панели

		JPanel buttonPanel = new JPanel();// панель для кнопок

		backButton = new JButton("<");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));// установка
																				// курсора
																				// при
																				// наведении
		backButton.addActionListener(new ActionListener() {// функция к кнопке
															// ("назад")

			public void actionPerformed(ActionEvent e) {

				actionBack();// переход на перд.стр.

			}

		});

		backButton.setEnabled(false);

		buttonPanel.add(backButton);

		forwardButton = new JButton(">");

		forwardButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				actionForward();// переход на след. стр.

			}

		});

		forwardButton.setEnabled(false);
		forwardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		buttonPanel.add(forwardButton);

		locationTextField = new JTextField(35);// создание текстового поля для
												// поиска

		locationTextField.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					actionGo();// функция собственно для поиска

				}

			}

		});

		buttonPanel.add(locationTextField);

		JButton goButton = new JButton("Искать");
		goButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		goButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

			}

		});

		buttonPanel.add(goButton);

		buttonPanel.add(labelBan);

		// Настройка отображения страницы.

		displayEditorPane = new JEditorPane();

		displayEditorPane.setContentType("text/html");

		displayEditorPane.setEditable(false);

		displayEditorPane.addHyperlinkListener(this);

		/**
		 * getContentPane == this т.к. мы для класса использовали extends JFrame
		 *
		 * по идеи пользоваться getContentPane правильнее для таких проектов
		 */

		getContentPane().setLayout(new BorderLayout());// добавление на фрем
														// BorderLayout

		getContentPane().add(buttonPanel, BorderLayout.NORTH);// дисплей для
																// кнопок и
																// поиска

		getContentPane().add(new JScrollPane(displayEditorPane), BorderLayout.CENTER);// дисплей
																						// отображения
																						// страниц

	}

	// Выход из проги

	private void actionExit() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	// Вернуться на страницу, которая была перед текущей страницей

	private void actionBack() {

		URL currentUrl = displayEditorPane.getPage();

		int pageIndex = pageList.indexOf(currentUrl.toString());

		try {
			showPage(new URL((String) pageList.get(pageIndex - 1)), false);

		} catch (Exception e) {
		}

	}

	// Вернуться на страницу, которая была после текущей

	private void actionForward() {

		URL currentUrl = displayEditorPane.getPage();

		int pageIndex = pageList.indexOf(currentUrl.toString());

		try {
			showPage(new URL((String) pageList.get(pageIndex + 1)), false);

		} catch (Exception e) {
		}

	}

	// Загрузить и показать страницу, указанную в текстовом поле "Искать"

	private void actionGo() {

		URL verifiedUrl = verifyUrl(locationTextField.getText());

		if (verifiedUrl != null) {

			showPage(verifiedUrl, true);// если в текст. поле что-то написано то
										// вызываем метод showPage

		} else {
			showError("Неправильная ссылка");
		}

	}

	// Добавление исключений

	private void showError(String errorMessage) {

		JOptionPane.showMessageDialog(this, errorMessage,

				"Ошибка", JOptionPane.ERROR_MESSAGE);

	}

	// Проверка URL-адресса.

	private URL verifyUrl(String url) {

		// Разрешить URL-адрес http
		if (!url.toLowerCase().startsWith("http://")) {
			return null;
		}
		URL verifiedUrl = null;
		try {
			verifiedUrl = new URL(url);
		} catch (Exception e) {
			return null;
		}

		return verifiedUrl;

	}

	/*
	 * Показать указанную страницу и добавить их в список страниц при наличии.
	 */
	private void showPage(URL pageUrl, boolean addToList) {

		// Добавление курсора, который при загрузке будет в виде песочных часов
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		try {
			// Получить URL-Адрес страницы отображающегося в данный момент.
			URL currentUrl = displayEditorPane.getPage();
			// Загрузить и отобразить указанную страницу.
			displayEditorPane.setPage(pageUrl);

			// Получить URL-адрес новой страницы
			URL newUrl = displayEditorPane.getPage();

			// Добавить страницу в список
			if (addToList) {
				int listSize = pageList.size();
				if (listSize > 0) {
					int pageIndex = pageList.indexOf(currentUrl.toString());
					if (pageIndex < listSize - 1) {
						for (int i = listSize - 1; i > pageIndex; i--) {
							pageList.remove(i);

						}

					}

				}

				pageList.add(newUrl.toString());// преобразование адреса в
												// строку
			}

			// Собственно, добавление на текстфилд адреса текущей страницы
			locationTextField.setText(newUrl.toString());

			// Обновление кнопок при изменении страницы
			updateButtons();
		} catch (Exception e) {
			// Отображение ошибки
			showError("Не удалось загрузить страницу");
		} finally {
			// Возвращение обычного курсора
			setCursor(Cursor.getDefaultCursor());
		}

	}

	/* Обновление кнопок назад и вперед */

	private void updateButtons() {

		if (pageList.size() < 2) {
			backButton.setEnabled(false);
			forwardButton.setEnabled(false);

		} else {
			URL currentUrl = displayEditorPane.getPage();
			int pageIndex = pageList.indexOf(currentUrl.toString());
			backButton.setEnabled(pageIndex > 0);
			forwardButton.setEnabled(pageIndex < (pageList.size() - 1));

		}

	}

	// Обработка гиперссылки при нажатии.

	public void hyperlinkUpdate(HyperlinkEvent event) {

		HyperlinkEvent.EventType eventType = event.getEventType();
		if (eventType == HyperlinkEvent.EventType.ACTIVATED) {
			if (event instanceof HTMLFrameHyperlinkEvent) {
				HTMLFrameHyperlinkEvent linkEvent = (HTMLFrameHyperlinkEvent) event;
				HTMLDocument document = (HTMLDocument) displayEditorPane.getDocument();
				document.processHTMLFrameHyperlinkEvent(linkEvent);
			} else {
				showPage(event.getURL(), true);
			}
		}
	}

	// Страт браузера

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		/**
		 * Orandetta
		 */
		Main browser = new Main();
		browser.show();// отображение
	}

}

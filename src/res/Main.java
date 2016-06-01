package res;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
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

public class Main extends JFrame implements HyperlinkListener {

	/**
	 * Created by Ilya 07.05.2016
	 * 454 line`s P.S. Good job :D
	 * Version 0.2
	 */
	private static final long serialVersionUID = 1L;

	private JPanel panelIcon;
	private JButton backButton, forwardButton;
	private JTextField locationTextField;
	private JEditorPane displayEditorPane;


	@SuppressWarnings("rawtypes")
	private ArrayList pageList = new ArrayList();


	public Main() {
		super("Orandetta");
		/*
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}*/

		ImageIcon i10 = new ImageIcon(getClass().getResource("Browser.jpg"));
		Image im10 = i10.getImage();

		super.setIconImage(im10);

		setSize(740, 580);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				actionExit();

			}

		});

		URL imgBan = getClass().getResource("Banner1.jpg");
		ImageIcon imgIco = new ImageIcon(imgBan);
		JLabel labelBan = new JLabel();
		labelBan.setIcon(imgIco);

		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();

		JMenu gitMenu = new JMenu("| Link on Github |");
		gitMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		JMenuItem gitMenuItem = new JMenuItem("GitHub");
		gitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent aeGit) {

				JFrame frameGit = new JFrame("Link GitHub");
				frameGit.setSize(450, 100);
				frameGit.setLocationRelativeTo(null);
				frameGit.setLayout(new BorderLayout());

				JPanel panelForGit = new JPanel();
				GridLayout layoutForGit = new GridLayout(1, 1);
				panelForGit.setLayout(layoutForGit);

				JTextArea textAreaForGit = new JTextArea();
				Font BigFontTR = new Font("TimesRoman", Font.BOLD, 17);
				textAreaForGit.setFont(BigFontTR);
				textAreaForGit.setText("https://github.com/ilyaVoznesensky/JavaBrowser");

				panelForGit.add(textAreaForGit);

				frameGit.add(panelForGit, BorderLayout.CENTER);
				frameGit.pack();
				frameGit.setVisible(true);

			}
		});
		gitMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JMenu fileNewBrowser = new JMenu("| Copy Browser |");
		fileNewBrowser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		JMenuItem fileMenuNewBrowser = new JMenuItem("Add second browser");
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

		JMenu fileMenu = new JMenu("| Exit |");
		fileMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem fileExitMenuItem = new JMenuItem("To exit the program", KeyEvent.VK_X);
		fileExitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		fileExitMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		gitMenu.add(gitMenuItem);
		fileMenu.add(fileExitMenuItem);
		menuBar.add(gitMenu);
		menuBar.add(fileNewBrowser);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);


		JPanel buttonPanel = new JPanel();

		backButton = new JButton("<");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				actionBack();

			}

		});

		backButton.setEnabled(false);

		buttonPanel.add(backButton);

		forwardButton = new JButton(">");

		forwardButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				actionForward();

			}

		});

		forwardButton.setEnabled(false);
		forwardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		buttonPanel.add(forwardButton);

		locationTextField = new JTextField(35);

		locationTextField.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					actionGo();

				}

			}

		});

		buttonPanel.add(locationTextField);

		JButton goButton = new JButton("Search");
		goButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		goButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				actionGo();
			}

		});

		buttonPanel.add(goButton);

		buttonPanel.add(labelBan);


		displayEditorPane = new JEditorPane();

		displayEditorPane.setContentType("text/html");

		displayEditorPane.setEditable(false);

		displayEditorPane.addHyperlinkListener(this);

		/**
		 * getContentPane == this, but get... it`s upgrade version and good tool
		 */

		getContentPane().setLayout(new BorderLayout());

		getContentPane().add(buttonPanel, BorderLayout.NORTH);

		getContentPane().add(new JScrollPane(displayEditorPane), BorderLayout.CENTER);

	}

	private void actionExit() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}


	private void actionBack() {

		URL currentUrl = displayEditorPane.getPage();

		int pageIndex = pageList.indexOf(currentUrl.toString());

		try {
			showPage(new URL((String) pageList.get(pageIndex - 1)), false);

		} catch (Exception e) {
		}

	}


	private void actionForward() {

		URL currentUrl = displayEditorPane.getPage();

		int pageIndex = pageList.indexOf(currentUrl.toString());

		try {
			showPage(new URL((String) pageList.get(pageIndex + 1)), false);

		} catch (Exception e) {
		}

	}


	private void actionGo() {

		URL verifiedUrl = verifyUrl(locationTextField.getText());

		if (verifiedUrl != null) {

			showPage(verifiedUrl, true);//showPage

		} else {
			showError("Error");
		}

	}

	private void showError(String errorMessage) {

		JOptionPane.showMessageDialog(this, errorMessage,

				"Error", JOptionPane.ERROR_MESSAGE);

	}

	private URL verifyUrl(String url) {

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

	@SuppressWarnings("unchecked")
	private void showPage(URL pageUrl, boolean addToList) {

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		try {
		
			URL currentUrl = displayEditorPane.getPage();
			
			displayEditorPane.setPage(pageUrl);

			URL newUrl = displayEditorPane.getPage();

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

				pageList.add(newUrl.toString());
			}

		
			locationTextField.setText(newUrl.toString());

			updateButtons();
		} catch (Exception e) {
			showError("Error");
		} finally {
			setCursor(Cursor.getDefaultCursor());
		}

	}

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


	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		/**
		 * Orandetta
		 */
		Main browser = new Main();
		browser.show();
	}

}

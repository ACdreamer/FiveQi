import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 *������������࣬����������
 */
public class StartChessJFrame extends JFrame {
	/**
	 * ���췽��StartChessJFrame(),���������ܡ���ť��˵���
	 * ������MyItemListener(ActionEvent e)��������ť
	 * ������main(String[] args)
	 */
	private static final long serialVersionUID = 1L;

	private ChessBoard chessBoard;// ������--������
	private JPanel toolbar;// һ������������
	private JButton startButton, backButton, exitButton;// ��ť��ʵ�֣�ͨ�� Action �����ð�ť��������һ���̶ȵĿ���

	private JMenuBar menuBar;// �˵�����ʵ�֣��� JMenu ������ӵ��˵����Թ���˵�
	private JMenu sysMenu;// �˵��ĸ�ʵ����һ������ JMenuItem �ĵ������ڣ��û�ѡ�� JMenuBar �ϵ���ʱ����ʾ�� JMenuItem
	private JMenuItem startMenuItem, exitMenuItem, backMenuItem;// �˵��е����ʵ�֣��˵��������λ���б��еİ�ť

	// ���¿�ʼ���˳����ͻ���˵���
	public StartChessJFrame() {
		setTitle("������");// ���ñ���
		chessBoard = new ChessBoard();// ����һ������
		Container contentPane = getContentPane();// ��ӵ������е��������һ���б���
		contentPane.add(chessBoard);// ������׷�ӵ���������β��
		chessBoard.setOpaque(true);// ������߽��ڵ���������

		// ��������Ӳ˵�
		menuBar = new JMenuBar();// ��ʼ���˵���
		sysMenu = new JMenu("�˵�");// ��ʼ���˵�
		// ��ʼ���˵���
		startMenuItem = new JMenuItem("���¿�ʼ");
		exitMenuItem = new JMenuItem("�˳�");
		backMenuItem = new JMenuItem("����");
		// �������˵�����ӵ��˵���
		sysMenu.add(startMenuItem);
		sysMenu.add(exitMenuItem);
		sysMenu.add(backMenuItem);
		// ��ʼ����ť�¼��������ڲ���
		MyItemListener lis = new MyItemListener();
		// �������˵�ע�ᵽ�¼���������
		startMenuItem.addActionListener(lis);
		backMenuItem.addActionListener(lis);
		exitMenuItem.addActionListener(lis);
		menuBar.add(sysMenu);// ��ϵͳ�˵���ӵ��˵�����
		setJMenuBar(menuBar);// ��menuBar����Ϊ�˵���

		toolbar = new JPanel();// �������ʵ����
		// ������ť��ʼ��
		startButton = new JButton("���¿�ʼ");
		exitButton = new JButton("�˳�");
		backButton = new JButton("����");
		// ��������尴ť��FlowLayout����
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		// ��������ť��ӵ��������
		toolbar.add(startButton);
		toolbar.add(exitButton);
		toolbar.add(backButton);
		// ��������ťע������¼�
		startButton.addActionListener(lis);
		exitButton.addActionListener(lis);
		backButton.addActionListener(lis);
		
		add(toolbar, BorderLayout.SOUTH);// ��������岼�ֵ����桱�Ϸ���Ҳ�����·�
		add(chessBoard);// ����������ӵ�������
		// ���ý���ر��¼�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();// ����Ӧ��С����������������ʹ����ʾ�������������С�ռ䡣
	}
	
	//�˵��밴ť�ļ�����
	private class MyItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();// ����¼�Դ
			if (obj == startMenuItem || obj == startButton) {
				// ���¿�ʼ
				System.out.println("���¿�ʼ");
				chessBoard.restartGame();
			}
			else if (obj == exitMenuItem || obj == exitButton){
				// �˳�
				System.exit(0);
			}	
			else if (obj == backMenuItem || obj == backButton) {
				// ����
				System.out.println("����");
				chessBoard.goback();
			}
		}
	}

	//main
	public static void main(String[] args) {
		StartChessJFrame f = new StartChessJFrame();// ���������
		f.setVisible(true);// ��ʾ�����

	}
}

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

/**
 * ������--������
 */

public class ChessBoard extends JPanel implements MouseListener,MouseMotionListener {
	/**
	 * ���췽�� ChessBoard()
	 * ���Ʒ��� paintComponent(Graphics g)
	 * ��갴�� mousePressed(MouseEvent e)
	 * �������� findChess(int x, int y):boolean
	 * �Ƿ�ʤ�� isWin():boolean
	 * ���¿�ʼ restartGame()
	 * ���� goback()
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int MARGIN = 30;// �߾�
	public static final int GRID_SPAN = 35;// ������
	public static final int ROWS = 15;// ��������
	public static final int COLS = 20;// ��������
	public static final int BLACK_CHESS = 1;
	public static final int WHITE_CHESS = 2;
	public static final int NO_CHESS = 0;
	
	Point[] chessList = new Point[(ROWS + 1) * (COLS + 1)];// ��ʼÿ������Ԫ��Ϊnull
	int board[][] = new int[COLS+1][ROWS+1];
	boolean isBlack = true;// Ĭ�Ͽ�ʼ�Ǻ�����
	boolean gameOver = false;// ��Ϸ�Ƿ����
	int chessCount;// ��ǰ�������ӵĸ���

	// ����
	public ChessBoard() {
		setBackground(Color.white);// ���ñ���ɫΪ��ɫ
		addMouseListener(this);// ���ָ�������������
		addMouseMotionListener(this);// ���ָ��������ƶ�������
	}

	// ����
	public void paintComponent(Graphics g) {
		super.paintComponent(g);// ������
		Color colortemp;
		for (int i = 0; i <= ROWS; i++) {// ������
			g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLS * GRID_SPAN, MARGIN + i * GRID_SPAN);
		}
		for (int i = 0; i <= COLS; i++) {// ������
			g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + ROWS * GRID_SPAN);
		}

		// ������
		for (int i = 0; i < chessCount; i++) {
			// ���񽻲��x��y����
			int xPos = chessList[i].getX() * GRID_SPAN + MARGIN;
			int yPos = chessList[i].getY() * GRID_SPAN + MARGIN;
			colortemp = chessList[i].getColor();
			g.setColor(colortemp);// ������ɫ
			// ʹ��Բ�η�����ɫ����ģʽ���ĳһ��״
			if (colortemp == Color.black) {
				RadialGradientPaint paint = new RadialGradientPaint(
						xPos - Point.DIAMETER / 2 + 25, 
						yPos - Point.DIAMETER / 2 + 10,
						20,
						new float[] { 0f, 1f },
						new Color[] { Color.WHITE, Color.BLACK });
				((Graphics2D) g).setPaint(paint);
				((Graphics2D) g).setRenderingHint(
						RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D) g).setRenderingHint(
						RenderingHints.KEY_ALPHA_INTERPOLATION,
						RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

			} 
			else if (colortemp == Color.white) {
				RadialGradientPaint paint = new RadialGradientPaint(
						xPos - Point.DIAMETER / 2 + 25,
						yPos - Point.DIAMETER / 2 + 10,
						70,
						new float[] { 0f, 1f },
						new Color[] { Color.WHITE, Color.BLACK });
				((Graphics2D) g).setPaint(paint);
				((Graphics2D) g).setRenderingHint(
						RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D) g).setRenderingHint(
						RenderingHints.KEY_ALPHA_INTERPOLATION,
						RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

			}

			Ellipse2D e = new Ellipse2D.Float(
					xPos - Point.DIAMETER / 2,
					yPos - Point.DIAMETER / 2,
					34,
					35);
			((Graphics2D) g).fill(e);
			
			// ������һ�����ӵĺ���ο�
			if (i == chessCount - 1) {
				g.setColor(Color.red);
				g.drawRect(xPos - Point.DIAMETER / 2, yPos - Point.DIAMETER / 2, 34, 35);
			}
		}
	}
	
	// �����������в��Ҳ���������Ϊx��y��ɫΪcolor�����Ӷ���
	// �ж�ʤ��ʱ��Ҫ
	private boolean getChess(int xIndex, int yIndex, int color) {
		if (board[xIndex][yIndex] == color) return true;
		return false;
	}
	
	// �ж��Ƿ�ʤ��
	private boolean isWin(int xIndex,int yIndex) {
		int continueCount = 1;// �������ӵĸ���
		int c = isBlack ? BLACK_CHESS : WHITE_CHESS;// ������ɫ
		
		// ����Ѱ��
		for (int x = xIndex - 1; x >= 0; x--) {
			if (getChess(x, yIndex, c)) continueCount++;
			else break;
		}
		// ����Ѱ��
		for (int x = xIndex + 1; x <= COLS; x++) {
			if (getChess(x, yIndex, c)) continueCount++;
			else break;
		}
		if (continueCount >= 5) return true;
		else continueCount = 1;

		// ����Ѱ��
		for (int y = yIndex - 1; y >= 0; y--) {
			if (getChess(xIndex, y, c)) continueCount++;
			else break;
		}
		// ����Ѱ��
		for (int y = yIndex + 1; y <= ROWS; y++) {
			if (getChess(xIndex, y, c)) continueCount++;
			else break;
		}
		if (continueCount >= 5) return true;
		else continueCount = 1;

		// ����Ѱ��
		for (int x = xIndex + 1, y = yIndex - 1; y >= 0 && x <= COLS; x++, y--) {
			if (getChess(x, y, c)) continueCount++;
			else break;
		}
		// ����Ѱ��
		for (int x = xIndex - 1, y = yIndex + 1; x >= 0 && y <= ROWS; x--, y++) {
			if (getChess(x, y, c)) continueCount++;
			else break;
		}
		if (continueCount >= 5) return true;
		else continueCount = 1;

		// ����Ѱ��
		for (int x = xIndex - 1, y = yIndex - 1; x >= 0 && y >= 0; x--, y--) {
			if (getChess(x, y, c)) continueCount++;
			else break;
		}
		// ����Ѱ��
		for (int x = xIndex + 1, y = yIndex + 1; x <= COLS && y <= ROWS; x++, y++) {
			if (getChess(x, y, c)) continueCount++;
			else break;
		}
		if (continueCount >= 5) return true;
		else continueCount = 1;

		return false;
	}

	// �������
	public void restartGame() {	
		for (int i = 0; i < chessList.length; i++) {
			chessList[i] = null;
		}
		for (int i = 0; i <= COLS; i++ ) {
			for (int j = 0; j <= ROWS; j++ ) {
				board[i][j] = NO_CHESS;
			}
		}
		// �ָ���Ϸ��صı���ֵ
		isBlack = true;
		gameOver = false; // ��Ϸ�Ƿ����
		chessCount = 0; // ��ǰ�������Ӹ���
		repaint();
	}

	// ����
	public void goback() {
		if (chessCount == 0) return;
		int x=chessList[chessCount - 1].getX();
		int y=chessList[chessCount - 1].getY();
		board[x][y]=NO_CHESS; // �ÿ�����
		chessList[chessCount - 1] = null; // ��������
		chessCount--;
		isBlack = !isBlack;
		repaint();
	}

	// ����Dimension
	public Dimension getPreferredSize() {
		return new Dimension(MARGIN * 2 + GRID_SPAN * COLS, MARGIN * 2 + GRID_SPAN * ROWS);
	}

	// �����������в����Ƿ�������Ϊx��y�����Ӵ���
	// ��갴��ʱ��Ҫ
	private boolean findChess(int x, int y) {
		for (Point c : chessList) {
			if (c != null && c.getX() == x && c.getY() == y)
				return true;
		}
		return false;
	}
	
	// �ܷ��������
	private boolean canPush(int x, int y){
		if (x < 0 || x > COLS || y < 0 || y > ROWS) return false;
		if (gameOver) return false;
		if (findChess(x, y)) return false;
		return true; 
	}
	
	// MouseListener
	public void mousePressed(MouseEvent e) {
		// ���������ϰ���ʱ����	
		
		// �������������λ��ת������������
		int xIndex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		int yIndex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		
		// ���������ⲻ���¡���Ϸ����ʱ���������¡����x��yλ���Ѿ������Ӵ��ڣ�������
		if (!canPush(xIndex, yIndex)) return;

		// ���Խ���ʱ�Ĵ���
		Point ch = new Point(xIndex, yIndex, isBlack ? Color.black : Color.white);

		//��������
		chessList[chessCount++] = ch;
		if (isBlack) board[xIndex][yIndex]=BLACK_CHESS;
		else board[xIndex][yIndex]=WHITE_CHESS;
		
		repaint();// ֪ͨϵͳ���»���

		// ���ʤ���������ʾ��Ϣ�����ܼ�������
		if (isWin(xIndex,yIndex)) {
			String colorName = isBlack ? "����" : "����";
			String msg = String.format("��ϲ��%sӮ�ˣ�", colorName);
			JOptionPane.showMessageDialog(this, msg);// ��׼�Ի��򡢸�֪�û�ĳ���ѷ���
			gameOver = true;
		}
		isBlack = !isBlack;
	}

	public void mouseClicked(MouseEvent e) {
		// ��갴��������ϵ���ʱ����
	}

	public void mouseEntered(MouseEvent e) {
		// �����뵽�����ʱ����
	}

	public void mouseExited(MouseEvent e) {
		// ����뿪���ʱ����
	}

	public void mouseReleased(MouseEvent e) {
		// ��갴ť��������ͷ�ʱ����
	}

	//MouseMotionListener
	public void mouseDragged(MouseEvent arg0) {
		// ��갴��������ϰ��²��϶�ʱ����
	}

	public void mouseMoved(MouseEvent e) {
		// ������ƶ�������ϵ��ް�������ʱ����
		
		// �������������λ��ת����������
		int x = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		int y = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		
		// ��Ϸ�Ѿ����������¡����������ⲻ���¡�x,yλ���Ѿ������Ӵ��ڲ�����
		if (canPush(x, y)) setCursor(new Cursor(Cursor.HAND_CURSOR));// ��״�������
		else setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// ���ó�Ĭ��״̬		
	}

}

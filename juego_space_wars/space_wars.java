

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class space_wars extends JFrame{
	TableroJuego tablero_juego;
	Puntuaciones panel_puntuaciones;
	
	public static void main(String[] args) {
		new space_wars();

	}
	public space_wars(){
		super();
		setSize(200,200);
		//setLocationRelativeTo(null);
		setBounds(200,200,600,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Prueba Paint");
		panel_puntuaciones = new Puntuaciones();
		tablero_juego = new TableroJuego(panel_puntuaciones);
		tablero_juego.setBackground(Color.orange);
		add(tablero_juego);
		add(panel_puntuaciones, BorderLayout.SOUTH);
		
		
		setVisible(true);
	}
}

class Puntuaciones extends JPanel{
	//JLabel nombre1,nombre2, puntuacion1,puntuacion2;
	private JLabel player1,player2;
	private JProgressBar points1,points2;
	private int point1 = 0;
	private int point2 = 0;
	
	Puntuaciones(){
//		nombre1 = new JLabel("Izquierdo");
//		nombre2 = new JLabel("Izquierdo");
//		puntuacion1 = new JLabel("10");
//		puntuacion2 = new JLabel("10");
		//new BoxLayout(this, BoxLayout.X_AXIS),
		setLayout( new GridLayout(1,2));
		setBackground(Color.cyan);
	     player1 = new JLabel("Izquierda");
	     points1 = new JProgressBar(0, 5);
	     points1.setForeground(Color.RED);
	     
	     player2 = new JLabel("Derecho");
	     points2 = new JProgressBar(0, 5);
	     points2.setForeground(Color.BLUE);
	     
	     points1.setValue(point1);
	     points2.setValue(point2);
	     
	     add(player1);
	     add(points1);
	     
	     add(player2);
	     add(points2);
//		add(nombre1);
//		add(puntuacion1);
//		add(nombre2);
//		add(puntuacion2);
//		
//		nombre1.setBackground(Color.cyan);
//		nombre2.setBackground(Color.cyan);
	}

	public void sumarPunto(int jugador) {
		if (jugador == 1) {
			point1++;
			points1.setValue(point1);
		}
		if (jugador == 2) {
			point2++;
			points2.setValue(point2);
		}
	}
	
	
}


//sin ser objeto
//class TableroJuego extends JPanel{
//	private int posX;
//	private int posY;
//	private int vel_desplazamiento;
//	private int ancho_cuadradito;
//	public TableroJuego() {
//		posX = 0;
//		posY = 0;
//		vel_desplazamiento = 5;
//		ancho_cuadradito = 30;
//		setFocusable(true);
//		addKeyListener(new KeyMonitor());
//		
//	}
//	
//
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		
//		Graphics2D g2d = (Graphics2D)g;
//		
//		g2d.fillRect(posX, posY, ancho_cuadradito, 30);
//	}
//
//	
//	class KeyMonitor extends KeyAdapter{
//		public void keyPressed(KeyEvent e) {
//			int key = e.getKeyCode();
//			System.out.println(e.getKeyCode());
//			switch (key) {
//			case KeyEvent.VK_LEFT: 
//				System.out.println("Vamos a la izquierda");
//				if (posX - vel_desplazamiento >= 0) {
//					posX = posX - vel_desplazamiento;
//					repaint();
//				}
//				break;
//			case KeyEvent.VK_RIGHT: 
//				System.out.println("Vamos a la derecha");
//				if (posX + vel_desplazamiento + ancho_cuadradito <= getHeight()) {
//					posX = posX + vel_desplazamiento;
//					repaint();
//				}
//
//				break;
//			case KeyEvent.VK_UP: 
//				System.out.println("Vamos a la arriba");
//				if (posY - vel_desplazamiento >=0) {
//					posY = posY - vel_desplazamiento;
//					repaint();
//				}
//
//				break;
//			case KeyEvent.VK_DOWN: 
//				System.out.println("Vamos a la abajo");
//				if (posY + vel_desplazamiento + ancho_cuadradito < getWidth()) {
//					posY = posY + vel_desplazamiento;
//					repaint();
//				}
//
//				break;
//			default:
//				System.out.println("No has clicado ningun cursor");
//				break;
//			}
//		}
//	}
//		public void keyTyped(KeyEvent e) {			
//		}
//		public void keyPressed(KeyEvent e) {
//		}
//		public void keyReleased(KeyEvent e) {	
//		}
//
//}

//siendo objeto
class TableroJuego extends JPanel{
	Rectangulito rectangulo1, rectangulo2;
	Pelota circulo;
	Puntuaciones panelPuntuaciones;
	public TableroJuego(Puntuaciones panelPuntuaciones) {
		this.panelPuntuaciones = panelPuntuaciones;
		setPreferredSize(new Dimension(600,400));
		rectangulo1 = new Rectangulito(5,10,5,20,80);
		//aqui el getWidth() todavia vale 0 y lo ponemos a mano
 		//rectangulo2 = new Rectangulito(getWidth()-5,10,5,20,80);
 		rectangulo2 = new Rectangulito(600-40,10,5,20,80);
		
		circulo = new Pelota(100,100,5,10);
		setFocusable(true);
		addKeyListener(new KeyMonitor());
		startTimer();

	}
	
	void startTimer(){
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			public void run() {
				if (getWidth() == 0 || getHeight() == 0) return;
				// se mueve la direccion
				//System.out.println("Tarea Ejecutada en: "+ System.currentTimeMillis());
				
				//colision en la izquierda
				if (circulo.getPosY() + circulo.getRadio() >= rectangulo1.getPosY() &&
						circulo.getPosY() <= rectangulo1.getPosY() + rectangulo1.getAlto_rectangulo() &&
						circulo.getPosX() <= rectangulo1.getPosX() + rectangulo1.getAncho_rectangulo()) {
					circulo.setDireccionX(circulo.getDireccionX()*(-1));
				}
				//colision en la derecha
				if (circulo.getPosY()+circulo.getRadio() >= rectangulo2.getPosY() &&
						circulo.getPosY() <= rectangulo2.getPosY() + rectangulo2.getAlto_rectangulo() &&
						circulo.getPosX()+ circulo.getRadio()*2 >= rectangulo2.getPosX()) {
					circulo.setDireccionX(circulo.getDireccionX()*(-1));
				}
				//REBOTE PELOTA PARED ABAJO
				if (circulo.getPosY()+circulo.getDireccionY()+ 2*circulo.getRadio() > getHeight()) {
					circulo.setDireccionY((-1)*circulo.getDireccionY());
				}
				//REBOTE PELOTA PARED ARRIBA
				if (circulo.getPosY()+circulo.getDireccionY() < 0) {
					circulo.setDireccionY((-1)*circulo.getDireccionY());
				}
				//REBOTE PELOTA PARED DERECHA
				if (circulo.getPosX()+circulo.getDireccionX()+ 2*circulo.getRadio() > getWidth()) {
					//circulo.setDireccionX((-1)*circulo.getDireccionX());
					resetTimer(timer);
				}
				//REBOTE PELOTA PARED IZQUIERDA
				if (circulo.getPosX()+circulo.getDireccionX() < 0) {
					
					resetTimer(timer);
					//circulo.setDireccionX((-1)*circulo.getDireccionX());
				}
				circulo.setPosX(circulo.getPosX()+circulo.getDireccionX());
				circulo.setPosY(circulo.getPosY()+circulo.getDireccionY());
				repaint();
			}
		};
		
		timer.schedule(task,100,20);
	}
	
	void resetTimer(Timer timer){
		timer.cancel();
		int point1 = 0;
		int point2 = 0;
		if (point1 != 5  )
		if(circulo.getPosX() == 0) {
			panelPuntuaciones.sumarPunto(2);
		}else {
			panelPuntuaciones.sumarPunto(1);
		}
		JOptionPane.showMessageDialog(null, "Game Over", "Goal Confirmation",JOptionPane.INFORMATION_MESSAGE);
		circulo.setPosX(getWidth()/2);
		circulo.setPosY(getHeight()/2);
		circulo.setDireccionX(circulo.getVel_desplazamiento());
		circulo.setDireccionY(circulo.getVel_desplazamiento());
		timer = new Timer();
		startTimer();
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.fillRect(rectangulo1.getPosX(), rectangulo1.getPosY(), rectangulo1.getAncho_rectangulo(), rectangulo1.getAlto_rectangulo());
		g2d.fillRect(rectangulo2.getPosX(), rectangulo2.getPosY(), rectangulo2.getAncho_rectangulo(), rectangulo2.getAlto_rectangulo());
		g2d.fillOval(circulo.getPosX(), circulo.getPosY(), 2*circulo.getRadio(), 2*circulo.getRadio());
	}

	
	class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			System.out.println(e.getKeyCode());
			switch (key) {
			//PARA CUADRADO
//			case KeyEvent.VK_LEFT: 
//				System.out.println("Vamos a la izquierda");
//				if (figura.getPosX() - figura.getVel_desplazamiento() >= 0) {
//					figura.setPosX(figura.getPosX() - figura.getVel_desplazamiento());
//					repaint();
//				}
//				break;
//			case KeyEvent.VK_RIGHT: 
//				System.out.println("Vamos a la derecha");
//				if (figura.getPosX() + figura.getVel_desplazamiento() + figura.getAncho_cuadradito() <= getWidth()) {
//					figura.setPosX(figura.getPosX() + figura.getVel_desplazamiento());
//					repaint();
//				}
//
//				break;
			case KeyEvent.VK_UP: 
				System.out.println("Vamos a la arriba");
				if (rectangulo1.getPosY() - rectangulo1.getVel_desplazamiento() >=0) {
					rectangulo1.setPosY( rectangulo1.getPosY() - rectangulo1.getVel_desplazamiento());
					repaint();
				}

				break;
			case KeyEvent.VK_W:
				System.out.println("Vamos a la arriba");
				if (rectangulo2.getPosY() - rectangulo2.getVel_desplazamiento() >=0) {
					rectangulo2.setPosY( rectangulo2.getPosY() - rectangulo2.getVel_desplazamiento());
					repaint();
				}
				break;
			case KeyEvent.VK_DOWN: 
				System.out.println("Vamos a la abajo");
				if (rectangulo1.getPosY() + rectangulo1.getVel_desplazamiento() + rectangulo1.getAlto_rectangulo()< getHeight()) {
					rectangulo1.setPosY( rectangulo1.getPosY() + rectangulo1.getVel_desplazamiento());
					repaint();
				}

				break;
			case KeyEvent.VK_S: 
				System.out.println("Vamos a la abajo");
				if (rectangulo2.getPosY() + rectangulo2.getVel_desplazamiento() + rectangulo2.getAlto_rectangulo()< getHeight()) {
					rectangulo2.setPosY( rectangulo2.getPosY() + rectangulo2.getVel_desplazamiento());
					repaint();
				}

				break;
			default:
				System.out.println("No has clicado ningun cursor");
				break;
			}
		}
	}

//class Quadrado{
//	private int posX;
//	private int posY;
//	private int vel_desplazamiento;
//	private int ancho_cuadradito;
//	public Quadrado(int posX, int posY, int vel_desplazamiento, int ancho_cuadradito) {
//		super();
//		this.posX = posX;
//		this.posY = posY;
//		this.vel_desplazamiento = vel_desplazamiento;
//		this.ancho_cuadradito = ancho_cuadradito;
//	}
//	public int getPosX() {
//		return posX;
//	}
//	public void setPosX(int posX) {
//		this.posX = posX;
//	}
//	public int getPosY() {
//		return posY;
//	}
//	public void setPosY(int posY) {
//		this.posY = posY;
//	}
//	public int getVel_desplazamiento() {
//		return vel_desplazamiento;
//	}
//	public void setVel_desplazamiento(int vel_desplazamiento) {
//		this.vel_desplazamiento = vel_desplazamiento;
//	}
//	public int getAncho_cuadradito() {
//		return ancho_cuadradito;
//	}
//	public void setAncho_cuadradito(int ancho_cuadradito) {
//		this.ancho_cuadradito = ancho_cuadradito;
//	}
//}
}
class Pelota{
	private int posX;
	private int posY;
	private int vel_desplazamiento;
	private int radio;
	int direccionX, direccionY;
	
	public Pelota(int posX, int posY, int vel_desplazamiento, int radio) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.vel_desplazamiento = vel_desplazamiento;
		this.radio = radio;
		direccionX = vel_desplazamiento;
		direccionY = vel_desplazamiento;
 	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getVel_desplazamiento() {
		return vel_desplazamiento;
	}
	public void setVel_desplazamiento(int vel_desplazamiento) {
		this.vel_desplazamiento = vel_desplazamiento;
	}
	public int getRadio() {
		return radio;
	}
	public void setRadio(int radio) {
		this.radio = radio;
	}
	public int getDireccionX() {
		return direccionX;
	}
	public void setDireccionX(int direccionX) {
		this.direccionX = direccionX;
	}
	public int getDireccionY() {
		return direccionY;
	}
	public void setDireccionY(int direccionY) {
		this.direccionY = direccionY;
	}
}

class Rectangulito{
	private int posX;
	private int posY;
	private int vel_desplazamiento;
	private int ancho_rectangulo,alto_rectangulo;

	public Rectangulito(int posX, int posY, int vel_desplazamiento, int ancho_rectangulo, int alto_rectangulo) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.vel_desplazamiento = vel_desplazamiento;
		this.ancho_rectangulo = ancho_rectangulo;
		this.alto_rectangulo = alto_rectangulo;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getVel_desplazamiento() {
		return vel_desplazamiento;
	}
	public void setVel_desplazamiento(int vel_desplazamiento) {
		this.vel_desplazamiento = vel_desplazamiento;
	}
	public int getAncho_rectangulo() {
		return ancho_rectangulo;
	}
	public void setAncho_rectangulo(int ancho_rectangulo) {
		this.ancho_rectangulo = ancho_rectangulo;
	}
	public int getAlto_rectangulo() {
		return alto_rectangulo;
	}
	public void setAlto_rectangulo(int alto_rectangulo) {
		this.alto_rectangulo = alto_rectangulo;
	}
	
}
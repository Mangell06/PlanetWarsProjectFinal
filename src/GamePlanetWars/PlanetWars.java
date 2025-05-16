package GamePlanetWars;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PlanetWars {
	
	public static void main(String[] args) {
		Connection conn = null;
	try {
		conn = DatabaseConnector.connect();
    } catch (ClassNotFoundException ex) {
		System.out.println(ex.getMessage());
	} catch (SQLException ey) {
		System.out.println(ey.getMessage());
	}
		VentanaJuego game = new VentanaJuego(conn);
		Timer enemySpawnTimer = new Timer();
		Timer battleTimer = new Timer();
		Timer resourceTimer = new Timer();
		
		enemySpawnTimer.schedule(new TimerTask() {
		    public void run() {
		    	if (game.getJuego() != null && game.getJuego().getPlaneta() != null && !game.getJuego().isDetener()) {
		    		int techLevel = game.getJuego().getPlaneta().getTechnologyDefense();
		    		int metal = (int) (Variables.METAL_BASE_ENEMY_ARMY + techLevel * 0.5);
		    		int deuterium = (int) (Variables.DEUTERIUM_BASE_ENEMY_ARMY + techLevel * 0.3);
		    		game.createEnemyArmy(metal, deuterium);
		    		game.getJuego().updateEnemyInformation();
		    		game.getJuego().setMessageBattleComming("Se han encontrado enemigos");
			        game.getJuego().repaint();
		    	}}}, 60000, 30000);
		
		battleTimer.schedule(new TimerTask() {
		    public void run() {
		    	if (game.getJuego() != null && game.getJuego().getPlaneta() != null && !game.getJuego().isDetener()) {
		    	boolean hay_ejercito_aliado = false;
		    	for (int i = 0; i < game.getJuego().getPlaneta().getArmy().length; i++) {
		    		if (game.getJuego().getPlaneta().getArmy()[i].size() > 0) {
		    			hay_ejercito_aliado = true;
		    		}
		    	}
		    	if (hay_ejercito_aliado) {
		    		game.getJuego().getPlaneta().getRepository().iniciarBatalla(game.getJuego().getPlaneta(), game.getJuego().getPlaneta().getNumBatalla());
		    	    Battle batalla = new Battle(game.getJuego().getPlaneta().getArmy(), game.getJuego().getEnemyArmy(), game.getJuego().getPlaneta(), game.getJuego().getPlaneta().getNumBatalla());
		    	    game.getJuego().setMessageBattleComming("Luchando!!!");
		    	    game.getJuego().repaint();
		    	    batalla.startBattle(game.getJuego().getPlaneta());
		    	    game.getJuego().addbattles(batalla);
		    	    game.getJuego().update_information();
		    	    try {
		    	        Thread.sleep(5000);
		    	    } catch (InterruptedException e) {
		    	        System.out.println(e.getMessage());
		    	    }
		    	} else {
		    	    game.getJuego().getPlaneta().roboResources();
		    	    game.getJuego().update_information();
			    	}
		        game.getJuego().restaurar_enemigo();
		        game.getJuego().updateEnemyInformation();
		        game.getJuego().setMessageBattleComming("Por ahora no hay enemigos");
	    		game.getJuego().repaint();
		    	}}}, 90000, 60000);
		
		resourceTimer.schedule(new TimerTask() {
		    public void run() {
		    	if (game.getJuego() != null && game.getJuego().getPlaneta() != null && !game.getJuego().isDetener()) {
			        game.getJuego().getPlaneta().setMetal(game.getJuego().getPlaneta().getMetal() + (Variables.PLANET_METAL_GENERATED + game.getJuego().getPlaneta().getTechnologyDefense() * 250));
			        game.getJuego().getPlaneta().setDeuterium(game.getJuego().getPlaneta().getDeuterium() + (Variables.PLANET_DEUTERIUM_GENERATED + game.getJuego().getPlaneta().getTechnologyDefense() * 100));
			        game.getJuego().update_information();
			        game.getJuego().repaint();
		    	}}}, 5000, 15000);}
}

class VentanaJuego extends JFrame {
	PanelIniciarSesion iniciarSesion;
	Game juego;
	public VentanaJuego(Connection conn) {
		setTitle("Planet Wars");
		setSize(1280,720);
		iniciarSesion = new PanelIniciarSesion(this, conn);
		add(iniciarSesion);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public PanelIniciarSesion getIniciarSesion() {
		return iniciarSesion;
	}
	public void createEnemyArmy(int metal, int deuterium) {
	    ArrayList<MilitaryUnit>[] army = new ArrayList[7];
	    for (int i = 0; i < army.length; i++) {
	        army[i] = new ArrayList<>();
	    }

	    Random rand = new Random();

	    int lightCostMetal = Variables.METAL_COST_LIGTHHUNTER;
	    int lightCostDeterium = Variables.DEUTERIUM_COST_LIGTHHUNTER;

	    int heavyCostMetal = Variables.METAL_COST_HEAVYHUNTER;
	    int heavyCostDeterium = Variables.DEUTERIUM_COST_HEAVYHUNTER;

	    int battleCostMetal = Variables.METAL_COST_BATTLESHIP;
	    int battleCostDeterium = Variables.DEUTERIUM_COST_BATTLESHIP;

	    int armoredCostMetal = Variables.METAL_COST_ARMOREDSHIP;
	    int armoredCostDeterium = Variables.DEUTERIUM_COST_ARMOREDSHIP;

	    while (metal >= lightCostMetal && deuterium >= lightCostDeterium) {
	        double r = rand.nextDouble() * 100;

	        if (r < 35 && metal >= lightCostMetal && deuterium >= lightCostDeterium) {
	            army[0].add(new LightHunter());
	            metal -= lightCostMetal;
	            deuterium -= lightCostDeterium;

	        } else if (r < 60 && metal >= heavyCostMetal && deuterium >= heavyCostDeterium) {
	            army[1].add(new HeavyHunter());
	            metal -= heavyCostMetal;
	            deuterium -= heavyCostDeterium;

	        } else if (r < 80 && metal >= battleCostMetal && deuterium >= battleCostDeterium) {
	            army[2].add(new BattleShip());
	            metal -= battleCostMetal;
	            deuterium -= battleCostDeterium;

	        } else if (r < 100 && metal >= armoredCostMetal && deuterium >= armoredCostDeterium) {
	            army[3].add(new ArmoredShip());
	            metal -= armoredCostMetal;
	            deuterium -= armoredCostDeterium;
	        }
	    }

	    juego.setEnemyArmy(army);
	}


	public void mostrarPanelJuego(User user, Planet planet) {
		getContentPane().removeAll();
		juego = new Game(user, planet);
		add(juego); 
		revalidate();
		repaint();
	}

	public Game getJuego() {
		return juego;
	}
	
}

class Game extends JPanel {
    private Planet planeta;
    private User usuario;
    private JPanel north,stats,barraderechastats, barraizquierdastats, barraabajostats, shop,battlescomming,reports;
    private FondoPanel planetstat;
    private JLabel name, metal, deuterium, leveltechnologyattack, leveltechnologydefense, nosubirataque, nosubirdefensa, precioataque, preciodefensa, messagebattlecomming;
    private JTabbedPane menu;
    private BufferedImage[] imagenesUnidades;
    private JButton subirataque, subirdefensa, buscarreport;
    private JTextArea battlereport;
    private JTextField elegirreport;
    private ArrayList<MilitaryUnit>[] enemyArmy;
    private boolean detener = false;
    private ArrayList<Battle> batallas = new ArrayList<Battle>();
    
    public void addbattles(Battle batalla) {
    	batallas.add(batalla);
    }
    
    public ArrayList<MilitaryUnit>[] getEnemyArmy() {
		return enemyArmy;
	}

	public void setEnemyArmy(ArrayList<MilitaryUnit>[] enemyArmy) {
		this.enemyArmy = enemyArmy;
	}
	
	public void restaurar_enemigo() {
        this.enemyArmy = new ArrayList[7];
        for (int i = 0; i < enemyArmy.length; i++) {
		    enemyArmy[i] = new ArrayList<>();
		}
	}
	
	public void setMessageBattleComming(String mensaje) {
		messagebattlecomming.setText(mensaje);;
		repaint();
	}

	public boolean isDetener() {
		return detener;
	}

	public Game(User usuario, Planet planeta) {
		setPreferredSize(new Dimension(1280, 720));
        this.usuario = usuario;
        this.planeta = planeta;
        this.enemyArmy = new ArrayList[7];
        for (int i = 0; i < enemyArmy.length; i++) {
		    enemyArmy[i] = new ArrayList<>();
		}
        setLayout(new BorderLayout());
        try {
        	imagenesUnidades = new BufferedImage[7];
        	imagenesUnidades[0] = ImageIO.read(new File("./Assets/Asset_LightHunter.png")); // getClass().getResourceAsStream("/Assets/Asset_LightHunter.png")
        	imagenesUnidades[1] = ImageIO.read(new File("./Assets/Asset_HeavyHunter.png"));
        	imagenesUnidades[2] = ImageIO.read(new File("./Assets/Asset_BattleShip.png"));
        	imagenesUnidades[3] = ImageIO.read(new File("./Assets/Asset_ArmoredShip.png"));
        	imagenesUnidades[4] = ImageIO.read(new File("./Assets/Asset_MissileLauncher.png"));
        	imagenesUnidades[5] = ImageIO.read(new File("./Assets/Asset_IonCannon.png"));
        	imagenesUnidades[6] = ImageIO.read(new File("./Assets/Asset_PlasmaCannon.png"));
        } catch (IOException e) {
            System.out.println("Error loading unit images: " + e.getMessage());
        }
        name = new JLabel(usuario.getName());
        name.setForeground(Color.WHITE);
        north = new JPanel();
        north.setBackground(Color.BLACK);
        messagebattlecomming = new JLabel("Por ahora no hay enemigos");
        messagebattlecomming.setForeground(Color.WHITE);
        add(north,BorderLayout.NORTH);
        north.add(name);
        menu = new JTabbedPane();
        menu.setForeground(Color.WHITE);
        menu.setBackground(Color.BLACK);
        stats = new JPanel();
        stats.setBackground(Color.BLACK);
        metal = new JLabel("Metal");
        metal.setForeground(Color.WHITE);
        deuterium = new JLabel("Deuterium");
        deuterium.setForeground(Color.WHITE);
        leveltechnologyattack = new JLabel("Level Technology Attack");
        leveltechnologyattack.setForeground(Color.WHITE);
        leveltechnologydefense = new JLabel("Level Technology Defense");
        leveltechnologydefense.setForeground(Color.WHITE);
        metal.setText("Metal: " + planeta.getMetal());
        deuterium.setText("Deuterium: " + planeta.getDeuterium());
        leveltechnologyattack.setText("Tech attack: " + planeta.getTechnologyAtack());
        leveltechnologydefense.setText("Tech Defense: " + planeta.getTechnologyDefense());
        stats.setLayout(new BorderLayout());
        planetstat = new FondoPanel(planeta.getImagen(), planeta, true);
        barraderechastats = new JPanel();
        barraderechastats.setBackground(Color.BLACK);
        barraizquierdastats = new JPanel();
        barraizquierdastats.setBackground(Color.BLACK);
        barraizquierdastats.add(metal);
        barraizquierdastats.add(deuterium);
        barraizquierdastats.add(leveltechnologyattack);
        barraizquierdastats.add(leveltechnologydefense);
        shop = new JPanel();
        shop.setBackground(Color.BLACK);
        JPanel mensajesPanel = new JPanel();
        mensajesPanel.setBackground(Color.BLACK);
        mensajesPanel.setLayout(new BoxLayout(mensajesPanel, BoxLayout.Y_AXIS));
        barraabajostats = new JPanel();
        JLabel texto = new JLabel("");
        texto.setForeground(Color.WHITE);
        mensajesPanel.add(texto);
        JLabel mensajeCompra = new JLabel("");
        mensajeCompra.setForeground(Color.WHITE);
        JLabel unidadesCompradas = new JLabel("");
        unidadesCompradas.setForeground(Color.WHITE);
        texto.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensajeCompra.setAlignmentX(Component.CENTER_ALIGNMENT);
        unidadesCompradas.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensajesPanel.add(mensajeCompra);
        mensajesPanel.add(unidadesCompradas);
        shop.add(mensajesPanel);
        mensajesPanel.setPreferredSize(new Dimension(1280,150));
        mensajesPanel.setMaximumSize(new Dimension(1280,150));
        mensajesPanel.setMinimumSize(new Dimension(1280,150));
        mensajesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        for (int i = 0; i < planeta.getArmy().length; i++) {
        	final int index = i;
        	FondoPanel naveimagen = new FondoPanel(imagenesUnidades[i], planeta, false); 
        	naveimagen.setPreferredSize(new Dimension(120,60)); 
        	naveimagen.setMinimumSize(new Dimension(120,60)); 
        	naveimagen.setMaximumSize(new Dimension(120,60));
    		barraderechastats.add(naveimagen);
    		JPanel compra = new JPanel();
    		compra.setBackground(Color.BLACK);
    		compra.setLayout(new BoxLayout(compra, BoxLayout.Y_AXIS));
    		FondoPanel naveimagenshop = new FondoPanel(imagenesUnidades[i], planeta, false);
        	naveimagenshop.setPreferredSize(new Dimension(120,80)); 
        	naveimagenshop.setMinimumSize(new Dimension(120,80)); 
        	naveimagenshop.setMaximumSize(new Dimension(120,80));
        	compra.setPreferredSize(new Dimension(170,170)); 
        	compra.setMinimumSize(new Dimension(170,170)); 
        	compra.setMaximumSize(new Dimension(170,170));
        	compra.add(Box.createVerticalStrut(10));
    		compra.add(naveimagenshop);
        	compra.add(Box.createVerticalStrut(10));
        	JTextField cantidad = new JTextField("1");
        	cantidad.setHorizontalAlignment(JTextField.CENTER);
        	cantidad.setPreferredSize(new Dimension(30,20));
        	cantidad.setMinimumSize(new Dimension(30,20));
        	cantidad.setMaximumSize(new Dimension(30,20));
        	cantidad.setAlignmentX(Component.CENTER_ALIGNMENT);
        	compra.add(cantidad);
        	compra.add(Box.createVerticalStrut(5));
    		JButton botoncito = new JButton("Buy");
    		botoncito.setPreferredSize(new Dimension(60,20));
    		botoncito.setMinimumSize(new Dimension(60,20));
    		botoncito.setMaximumSize(new Dimension(60,20));
    		botoncito.setAlignmentX(Component.CENTER_ALIGNMENT);
    		compra.add(botoncito);
    		compra.setBackground(Color.GRAY);
    		shop.add(compra);
    	    botoncito.addActionListener(new ActionListener() {
    	        public void actionPerformed(ActionEvent e) {
    	        	 try {
    	                 int n = Integer.parseInt(cantidad.getText().trim());
    	                 if (n <= 0) throw new NumberFormatException();
    	            	 texto.setText("");
    	            	 int antes = planeta.getArmy()[index].size();
    	                 switch(index) {
    	                     case 0:
    	                    	 planeta.newLightHunter(n);
    	                    	 break;
    	                     case 1 :
    	                    	 planeta.newHeavyHunter(n);
    	                    	 break;
    	                     case 2 :
    	                    	 planeta.newBattleShip(n);
    	                    	 break;
    	                     case 3 :
    	                    	 planeta.newArmoredShip(n);
    	                    	 break;
    	                     case 4 :
    	                    	 planeta.newMissileLauncher(n);
    	                    	 break;
    	                     case 5 :
    	                    	 planeta.newIonCannon(n);
    	                    	 break;
    	                     case 6 :
    	                    	 planeta.newPlasmaCannon(n);
    	                    	 break;
    	                 }
    	                 int despues = planeta.getArmy()[index].size();
    	                 int compradas = despues - antes;
    	                 update_information();
    	                 if (compradas == 0) {
    	                	    mensajeCompra.setText("❌ No se pudieron comprar unidades.");
    	                	    unidadesCompradas.setText("");
    	                	} else if (compradas != n) {
    	                		mensajeCompra.setText("❌ No se pudieron comprar todas las unidades.");
    	                	    unidadesCompradas.setText("✅ Compradas: " + compradas);
    	                	}else {
    	                	    mensajeCompra.setText("");
    	                	    unidadesCompradas.setText("✅ Compradas: " + compradas);
    	                	}
    	             } catch (NumberFormatException ex) {
    	            	 texto.setText("La cantidad debe ser un numero");
    	            	 unidadesCompradas.setText("");
	                	 mensajeCompra.setText("");
    	             }
    	         }});
    	    JLabel textoayudita = new JLabel("" + planeta.getArmy()[i].size());
    	    textoayudita.setForeground(Color.WHITE);
    		barraderechastats.add(textoayudita);
        }
        battlescomming = new JPanel();
        battlescomming.setBackground(Color.BLACK);
        battlescomming.add(messagebattlecomming);
        for (int i = 0; i < 4; i++) {
        	FondoPanel naveimagencomming = new FondoPanel(imagenesUnidades[i], planeta, false);
        	naveimagencomming.setPreferredSize(new Dimension(120,80)); 
        	naveimagencomming.setMinimumSize(new Dimension(120,80)); 
        	naveimagencomming.setMaximumSize(new Dimension(120,80));
        	battlescomming.add(naveimagencomming);
        	JLabel textoayuda = new JLabel("" + 0);
        	textoayuda.setForeground(Color.WHITE);
        	battlescomming.add(textoayuda);
        }
        stats.add(planetstat, BorderLayout.CENTER);
        stats.add(barraderechastats,BorderLayout.EAST);
        stats.add(barraizquierdastats,BorderLayout.WEST);
        stats.add(barraabajostats, BorderLayout.SOUTH);
        barraizquierdastats.setPreferredSize(new Dimension(250,600));
        barraizquierdastats.setMinimumSize(new Dimension(250,600));
        barraizquierdastats.setMaximumSize(new Dimension(250,600));
        barraderechastats.setPreferredSize(new Dimension(250,600));
        barraderechastats.setMinimumSize(new Dimension(250,600));
        barraderechastats.setMaximumSize(new Dimension(250,600));
        barraabajostats.setPreferredSize(new Dimension(1280,150));
        barraabajostats.setMinimumSize(new Dimension(1280,150));
        barraabajostats.setMaximumSize(new Dimension(1280,150));
        nosubirataque = new JLabel("");
        nosubirataque.setForeground(Color.WHITE);
        subirataque = new JButton("Update Level technology attack");
        nosubirdefensa = new JLabel("");
        nosubirdefensa.setForeground(Color.WHITE);
        subirdefensa = new JButton("Update Level technology defense");
        subirataque.addActionListener(new EventosJuego());
        subirdefensa.addActionListener(new EventosJuego());
        barraabajostats.setLayout(new BoxLayout(barraabajostats,BoxLayout.Y_AXIS));
        barraabajostats.add(nosubirataque);
        barraabajostats.setBackground(Color.BLACK);
        nosubirataque.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraabajostats.add(Box.createVerticalStrut(10));
        precioataque = new JLabel("Price Deuterium: " + planeta.getUpgradeAttackTechnologyDeuteriumCost());
        precioataque.setForeground(Color.WHITE);
        barraabajostats.add(precioataque);
        precioataque.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraabajostats.add(Box.createVerticalStrut(5));
        barraabajostats.add(subirataque);
        subirataque.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraabajostats.add(Box.createVerticalStrut(5));
        barraabajostats.add(nosubirdefensa);
        nosubirdefensa.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraabajostats.add(Box.createVerticalStrut(10));
        preciodefensa = new JLabel("Price Deuterium: " + planeta.getUpgradeDefenseTechnologyDeuteriumCost());
        preciodefensa.setForeground(Color.WHITE);
        barraabajostats.add(preciodefensa);
        preciodefensa.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraabajostats.add(Box.createVerticalStrut(5));
        barraabajostats.add(subirdefensa);
        subirdefensa.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraabajostats.add(Box.createVerticalStrut(10));
        menu.addTab("Stats", stats);
        menu.addTab("Shop", shop);
        menu.addTab("Battle Comming", battlescomming);
        reports = new JPanel();
        reports.setLayout(new BoxLayout(reports, BoxLayout.Y_AXIS));
        reports.setBackground(Color.BLACK);
        battlereport = new JTextArea("Busca aqui los reportes de tus batallas");
        battlereport.setPreferredSize(new Dimension(1000,400));
        battlereport.setMinimumSize(new Dimension(1000,400));
        battlereport.setMaximumSize(new Dimension(1000,400));
        reports.add(battlereport);
        elegirreport = new JTextField("1");
        elegirreport.setPreferredSize(new Dimension(100,50));
        elegirreport.setMinimumSize(new Dimension(100,50));
        elegirreport.setMaximumSize(new Dimension(100,50));
        elegirreport.setHorizontalAlignment(JTextField.CENTER);
        reports.add(Box.createVerticalStrut(10));
        reports.add(elegirreport);
        reports.add(Box.createVerticalStrut(10));
        buscarreport = new JButton("Buscar");
        buscarreport.setPreferredSize(new Dimension(100,50));
        buscarreport.setMinimumSize(new Dimension(100,50));
        buscarreport.setMaximumSize(new Dimension(100,50));
        buscarreport.setAlignmentX(Component.CENTER_ALIGNMENT);
        reports.add(buscarreport);
        buscarreport.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	try {
	        		int n = Integer.parseInt(elegirreport.getText());
	        		if (batallas.size() == 0) {
	        			battlereport.setText("Aun no a habido batallas");
	        		}
	        		if(batallas.size() > 0) {
		        		if (n > 0 && n <= batallas.size()) {
		        			battlereport.setText(batallas.get(n-1).getBattleReport(n));
		        		} else {
		        			battlereport.setText("Las batallas solo estan entre 1 (La primera) hasta " + batallas.size() + " (La ultima hasta la fecha)");
		        		}
	        		}
	        	}catch (NumberFormatException ex) {
	        		battlereport.setText("Tiene que ser un numero");
	        	}
	        }});
        menu.addTab("Battle reports", reports);
        add(menu,BorderLayout.CENTER);
    }
	
	public void updateEnemyInformation() {
		battlescomming.removeAll();
		battlescomming.add(messagebattlecomming);
		for (int i = 0; i < 4; i++) {
			if (enemyArmy[i] != null) {
	        	FondoPanel naveimagencomming = new FondoPanel(imagenesUnidades[i], planeta, false);
	        	naveimagencomming.setPreferredSize(new Dimension(120,80)); 
	        	naveimagencomming.setMinimumSize(new Dimension(120,80)); 
	        	naveimagencomming.setMaximumSize(new Dimension(120,80));
	        	battlescomming.add(naveimagencomming);
	        	JLabel textoayudita = new JLabel("" + enemyArmy[i].size());
	        	textoayudita.setForeground(Color.WHITE);
	        	battlescomming.add(textoayudita);
			} else {
	        	FondoPanel naveimagencomming = new FondoPanel(imagenesUnidades[i], planeta, false);
	        	naveimagencomming.setPreferredSize(new Dimension(120,80)); 
	        	naveimagencomming.setMinimumSize(new Dimension(120,80)); 
	        	naveimagencomming.setMaximumSize(new Dimension(120,80));
	        	battlescomming.add(naveimagencomming);
	        	JLabel textoayudita = new JLabel("" + 0);
	        	textoayudita.setForeground(Color.WHITE);
	        	battlescomming.add(textoayudita);
			}
        }
	}
    
    public Planet getPlaneta() {
		return planeta;
	}

	class EventosJuego implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			int anterior_nivel;
			int despues_nivel;
			if (e.getActionCommand().equals("Update Level technology attack")) {
				anterior_nivel = planeta.getTechnologyAtack();
				planeta.upgradeTechnologyAttack();
				despues_nivel = planeta.getTechnologyAtack();
				if (anterior_nivel == despues_nivel) {
					nosubirdefensa.setText("");
					nosubirataque.setText("No tienes suficientes recursos");
				} else {
					nosubirataque.setText("");
					nosubirdefensa.setText("");
				}
			} else if (e.getActionCommand().equals("Update Level technology defense")) {
				anterior_nivel = planeta.getTechnologyDefense();
				planeta.upgradeTechnologyDefense();
				despues_nivel = planeta.getTechnologyDefense();
				if (anterior_nivel == despues_nivel) {
					nosubirataque.setText("");
					nosubirdefensa.setText("No tienes suficientes recursos");
				} else {
					nosubirataque.setText("");
					nosubirdefensa.setText("");
				}
			}
			update_information();
		}
    	
    }
	
	public void reconstruirShop() {
	    shop.removeAll();
	    shop.setBackground(Color.BLACK);
	    JPanel mensajesPanel = new JPanel();
	    mensajesPanel.setBackground(Color.BLACK);
	    mensajesPanel.setLayout(new BoxLayout(mensajesPanel, BoxLayout.Y_AXIS));
	    mensajesPanel.setPreferredSize(new Dimension(1280, 150));
	    mensajesPanel.setMaximumSize(new Dimension(1280, 150));
	    mensajesPanel.setMinimumSize(new Dimension(1280, 150));
	    mensajesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    JLabel texto = new JLabel("");
	    texto.setForeground(Color.WHITE);
	    JLabel mensajeCompra = new JLabel("");
	    mensajeCompra.setForeground(Color.WHITE);
	    JLabel unidadesCompradas = new JLabel("");
	    unidadesCompradas.setForeground(Color.WHITE);
	    texto.setAlignmentX(Component.CENTER_ALIGNMENT);
	    mensajeCompra.setAlignmentX(Component.CENTER_ALIGNMENT);
	    unidadesCompradas.setAlignmentX(Component.CENTER_ALIGNMENT);
	    mensajesPanel.add(texto);
	    mensajesPanel.add(mensajeCompra);
	    mensajesPanel.add(unidadesCompradas);
	    shop.add(mensajesPanel);
	    for (int i = 0; i < planeta.getArmy().length; i++) {
	        final int index = i;
	        JPanel compra = new JPanel();
	        compra.setBackground(Color.GRAY);
	        compra.setLayout(new BoxLayout(compra, BoxLayout.Y_AXIS));
	        compra.setPreferredSize(new Dimension(170, 170));
	        compra.setMinimumSize(new Dimension(170, 170));
	        compra.setMaximumSize(new Dimension(170, 170));

	        FondoPanel naveimagenshop = new FondoPanel(imagenesUnidades[i], planeta, false);
	        naveimagenshop.setPreferredSize(new Dimension(120, 80));
	        naveimagenshop.setMinimumSize(new Dimension(120, 80));
	        naveimagenshop.setMaximumSize(new Dimension(120, 80));

	        JTextField cantidad = new JTextField("1");
	        cantidad.setHorizontalAlignment(JTextField.CENTER);
	        cantidad.setPreferredSize(new Dimension(30, 20));
	        cantidad.setMinimumSize(new Dimension(30, 20));
	        cantidad.setMaximumSize(new Dimension(30, 20));
	        cantidad.setAlignmentX(Component.CENTER_ALIGNMENT);

	        JButton botoncito = new JButton("Buy");
	        botoncito.setPreferredSize(new Dimension(60, 20));
	        botoncito.setMinimumSize(new Dimension(60, 20));
	        botoncito.setMaximumSize(new Dimension(60, 20));
	        botoncito.setAlignmentX(Component.CENTER_ALIGNMENT);

	        compra.add(Box.createVerticalStrut(10));
	        compra.add(naveimagenshop);
	        compra.add(Box.createVerticalStrut(10));
	        compra.add(cantidad);
	        compra.add(Box.createVerticalStrut(5));
	        compra.add(botoncito);
	        shop.add(compra);
	        botoncito.addActionListener(e -> {
	            try {
	                int n = Integer.parseInt(cantidad.getText().trim());
	                if (n <= 0) throw new NumberFormatException();

	                texto.setText("");

	                int antes = planeta.getArmy()[index].size();
	                switch (index) {
	                    case 0 -> planeta.newLightHunter(n);
	                    case 1 -> planeta.newHeavyHunter(n);
	                    case 2 -> planeta.newBattleShip(n);
	                    case 3 -> planeta.newArmoredShip(n);
	                    case 4 -> planeta.newMissileLauncher(n);
	                    case 5 -> planeta.newIonCannon(n);
	                    case 6 -> planeta.newPlasmaCannon(n);
	                }
	                int compradas = planeta.getArmy()[index].size() - antes;
	                update_information();
	                if (compradas == 0) {
	                    mensajeCompra.setText("❌ No se pudieron comprar unidades.");
	                    unidadesCompradas.setText("");
	                } else if (compradas != n) {
	                    mensajeCompra.setText("❌ No se pudieron comprar todas las unidades.");
	                    unidadesCompradas.setText("✅ Compradas: " + compradas);
	                } else {
	                    mensajeCompra.setText("");
	                    unidadesCompradas.setText("✅ Compradas: " + compradas);
	                }
	            } catch (NumberFormatException ex) {
	                texto.setText("La cantidad debe ser un numero");
	                mensajeCompra.setText("");
	                unidadesCompradas.setText("");
	            }});
	    }

	    shop.revalidate();
	    shop.repaint();
	}

	
	public void reiniciarPlaneta(Planet nuevo) {
	    this.planeta = nuevo;
	    planeta.createid();
	    batallas.clear();
	    planetstat.setImagen(nuevo.getImagen());
	    planetstat.setPlaneta(nuevo);
	    metal.setText("Metal: " + nuevo.getMetal());
	    deuterium.setText("Deuterium: " + nuevo.getDeuterium());
	    leveltechnologyattack.setText("Tech attack: " + nuevo.getTechnologyAtack());
	    leveltechnologydefense.setText("Tech Defense: " + nuevo.getTechnologyDefense());
	    precioataque.setText("Price Deuterium: " + nuevo.getUpgradeAttackTechnologyDeuteriumCost());
	    preciodefensa.setText("Price Deuterium: " + nuevo.getUpgradeDefenseTechnologyDeuteriumCost());
	    planetstat.setPlaneta(nuevo);
	    barraderechastats.removeAll();
	    barraderechastats.setBackground(Color.BLACK);
	    for (int i = 0; i < nuevo.getArmy().length; i++) {
	        FondoPanel naveimagen = new FondoPanel(imagenesUnidades[i], nuevo, false);
	        naveimagen.setPreferredSize(new Dimension(120, 60));
	        naveimagen.setMinimumSize(new Dimension(120, 60));
	        naveimagen.setMaximumSize(new Dimension(120, 60));
	        JLabel textoayudita = new JLabel("" + nuevo.getArmy()[i].size());
	        textoayudita.setForeground(Color.WHITE);
	        barraderechastats.add(naveimagen);
	        barraderechastats.add(textoayudita);
	    }
	    barraderechastats.revalidate();
	    barraderechastats.repaint();

	    updateEnemyInformation();
	    reconstruirShop();
	    battlereport.setText("Busca aqui los reportes de tus batallas");
	    elegirreport.setText("1");
	    repaint();
	    detener = false;
	}

	
	public void update_information() {
	    metal.setText("Metal: " + planeta.getMetal());
	    deuterium.setText("Deuterium: " + planeta.getDeuterium());
	    leveltechnologyattack.setText("Tech attack: " + planeta.getTechnologyAtack());
	    leveltechnologydefense.setText("Tech Defense: " + planeta.getTechnologyDefense());
	    precioataque.setText("Price Deuterium: " + planeta.getUpgradeAttackTechnologyDeuteriumCost());
	    preciodefensa.setText("Price Deuterium: " + planeta.getUpgradeDefenseTechnologyDeuteriumCost());

	    barraderechastats.removeAll();
	    barraderechastats.setBackground(Color.BLACK);
	    for (int i = 0; i < planeta.getArmy().length; i++) {
	        FondoPanel naveimagen = new FondoPanel(imagenesUnidades[i], planeta, false);
	        naveimagen.setPreferredSize(new Dimension(120, 60));
	        naveimagen.setMinimumSize(new Dimension(120, 60));
	        naveimagen.setMaximumSize(new Dimension(120, 60));

	        JLabel textoayudita = new JLabel("" + planeta.getArmy()[i].size());
	        textoayudita.setForeground(Color.WHITE);

	        barraderechastats.add(naveimagen);
	        barraderechastats.add(textoayudita);
	    }
	    planeta.getRepository().actualizarStatsPlaneta(planeta, batallas);
	    barraderechastats.revalidate();
	    barraderechastats.repaint();
	    if (planeta.getMetal() <= 0 || planeta.getDeuterium() <= 0) {
	        try {
	        	detener = true;
	            BufferedImage tierradestruida = ImageIO.read(new File("./Assets/Asset_EarthDestroyed.png"));
	            planetstat.setImagen(tierradestruida);
	            repaint();

	            String material = planeta.getMetal() <= 0 ? "Metal" : "Deuterium";
	            JOptionPane.showMessageDialog(null, "Game Over", "Tu planeta no tiene más " + material, JOptionPane.INFORMATION_MESSAGE);

	            reiniciarPlaneta(new Planet(planeta.getPlanet_id(),planeta.getImagen(), 1, 1, 100000, 1000000, 20000, 20000, planeta.getRepository().getConn()));
	            return;
	        } catch (IOException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	}

}

class FondoPanel extends JPanel {

    private BufferedImage imagen;
    private Planet planeta;
    private Boolean warning;

    public FondoPanel(BufferedImage imagen, Planet planeta, boolean warning) {
        this.imagen = imagen;
        this.planeta = planeta;
        this.warning = warning;
    }

    public void setImagen(BufferedImage imagen) {
		this.imagen = imagen;
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (imagen != null) {
            Image escalada = imagen.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            g.drawImage(escalada, 0, 0, this);
        }
        if (warning && (planeta != null && (planeta.getMetal() <= 5000 || planeta.getDeuterium() <= 5000))) {
        	String mensaje = "⚠ ¡PELIGRO! Recursos críticos";
            Font font = new Font("Arial", Font.BOLD, 15);
            FontMetrics metrics = g2.getFontMetrics(font);
            int textWidth = metrics.stringWidth(mensaje);
            int textHeight = metrics.getHeight()*2;
            int x = 10;
            int y = 20;
            g2.setColor(Color.BLACK);
            g2.fillRect(x - 5, y - textHeight + metrics.getAscent(), textWidth + 10, textHeight);

        	g2.setColor(new Color(255, 76, 76));
            g2.drawString(mensaje, 10, 20);
        }
    }

	public void setPlaneta(Planet planeta) {
		this.planeta = planeta;
	}
}

class PanelIniciarSesion extends JPanel {
    private VentanaJuego ventana;
    private JTextField name;
    private JPasswordField pass;
    private JLabel error;
    private Connection conn;

    public PanelIniciarSesion(VentanaJuego ventana, Connection conn) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1280, 720));
        this.conn = conn;
        // Norte - Título
        JPanel north = new JPanel();
        JLabel title = new JLabel("Planet Wars - Iniciar Sesión");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        north.setBackground(new Color(30, 30, 30));
        title.setForeground(Color.WHITE);
        north.add(title);
        add(north, BorderLayout.NORTH);

        // Centro - Formulario
        JPanel center = new JPanel();
        center.setLayout(new GridBagLayout());
        center.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.DARK_GRAY);

        name = new JTextField("Nombre de usuario", 20);
        pass = new JPasswordField("Contraseña", 20);
        error = new JLabel(" ");
        error.setForeground(Color.RED);

        form.add(Box.createVerticalStrut(20));
        form.add(name);
        form.add(Box.createVerticalStrut(10));
        form.add(pass);
        form.add(Box.createVerticalStrut(10));
        form.add(error);
        form.add(Box.createVerticalStrut(20));

        center.add(form, gbc);
        add(center, BorderLayout.CENTER);

        // Sur - Botones
        JPanel south = new JPanel();
        south.setBackground(new Color(30, 30, 30));
        JButton startaccount = new JButton("Iniciar Partida");
        JButton createaccount = new JButton("Crear Cuenta");
        south.add(startaccount);
        south.add(createaccount);
        add(south, BorderLayout.SOUTH);

        // Acciones
        startaccount.addActionListener(new EventosSesion());
        createaccount.addActionListener(new EventosSesion());
    }

    public class EventosSesion implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            String username = name.getText();
            String password = new String(pass.getPassword());

            if (command.equals("Crear Cuenta")) {
                if (!validarPassword(password)) {
                    error.setText("Contraseña débil. Usa mayúsculas, minúsculas, números y símbolos.");
                } else if (username.length() < 3 || username.length() > 12) {
                    error.setText("El nombre debe tener entre 3 y 12 caracteres.");
                } else {
                    try {
                    		// Verificar si el usuario introducido ya existe
                    		String sql = "SELECT planet_id FROM uswrs WHERE user_name = ?";
                    		PreparedStatement ps = conn.prepareStatement(sql);
                    		ps.setString(1, username);
                    		ResultSet rs = ps.executeQuery();
                    		
                    		if (rs.next()) {
                    			error.setText("❌ El usuario ya existe.");
                    		}
                    		
                    		// Crear planeta
                			BufferedImage img = ImageIO.read(new File("./Assets/Asset_EarthBasic.png"));
                            Planet planeta = new Planet(0,img, 1, 1, 100000, 1000000, 20000, 20000, conn);
                            planeta.createid();
                            planeta.getRepository().crear_planeta(planeta);
                            
                            // Insertar usuario en la tabla users
                            String insertUser = "INSERT INTO users (user_name, password, planet_id) VALUES (?, ?, ?)";
                            PreparedStatement insert = conn.prepareStatement(sql);
                            insert.setString(1, username);
                            insert.setString(2, password);
                            insert.setInt(3, planeta.getPlanet_id());
                            insert.executeUpdate();
                            
                            User user = new User(username, password);
                            ventana.mostrarPanelJuego(user, planeta);
                    } catch (IOException | SQLException ez) {
                        error.setText("Error cargando la imagen del planeta");
                    }
                }
            } else if (command.equals("Iniciar Partida")) {
                // Aquí se puede implementar carga de datos desde archivo
                error.setText("Funcionalidad aún no implementada.");
                
                try {
                	String buscarUser = "SELECT planet_id FROM users WHERE user_name = ? AND password = ?";
                	PreparedStatement buscarStmt = conn.prepareStatement(buscarUser);
                	buscarStmt.setString(1, username);
                	buscarStmt.setString(2, password);
                	ResultSet rs = buscarStmt.executeQuery();
                	
                	if (!rs.next()) {
                		error.setText("❌ User or Password not founded");
                		return;
                	}
                	
                	int planetId = rs.getInt("planet_id");
                	
                	// Aqui cargamos el planeta base a su ID (datos desde planet_stats)
                	String planetaQuery = "SELECT * FROM planet_stats WHERE planet_id = ?";
                	PreparedStatement planetaStmt = conn.prepareStatement(planetaQuery);
                	planetaStmt.setInt(1, planetId);
                	ResultSet planetaRs = planetaStmt.executeQuery();
                	
                	if (!planetaRs.next()) {
                		error.setText("❌  The planet not founded in MySQL.");
                		return;
                	}
                	
                	// Recuperar los datos del planeta
                	int metal = planetaRs.getInt("resource_metal_amount");
                	int deuterium = planetaRs.getInt("resource_deuterion_amount");
                	int techDef = planetaRs.getInt("technology_defense_level");
                	int techAtk = planetaRs.getInt("technology_attack_level");
        			BufferedImage img = ImageIO.read(new File("./Assets/Asset_EarthBasic.png"));
        			
        			Planet planeta = new Planet(planetId, img, techDef, techAtk, metal, deuterium, 20000, 20000, conn);
        			User user = new User(username, password);
        			ventana.mostrarPanelJuego(user, planeta);
                } catch (SQLException ex) {
                	error.setText("❌ Error to connect with MySQL.");
                } catch (IOException ez) {
                	error.setText("❌ Error to load the image.");
                }
            }
        }

        public boolean validarPassword(String password) {
            return password.length() >= 6 &&
                   password.matches(".*[a-z].*") &&
                   password.matches(".*[A-Z].*") &&
                   password.matches(".*\\d.*") &&
                   password.matches(".*[^a-zA-Z0-9].*");
        }
    }
}



class User {
	private String name;
	private String password;
	
	public User(String name, String Password) {
	    this.name = name;
	    this.password = Password;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name1) {
		name = name1;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password1) {
		password = password1;
	}
	
}

class Planet {
	private int planet_id, numBatalla;
	private BufferedImage imagen;
	private int technologyDefense;
	private int technologyAttack;
	private int metal;
	private int deuterium;
	private int upgradeDefenseTechnologyDeuteriumCost;
	private int upgradeAttackTechnologyDeuteriumCost;
	private ArrayList<MilitaryUnit>[] army;
	private PlanetRepository repository;
	
	public Planet(int id, BufferedImage imagen,int technologyDefense, int technologyAtack, int metal, int deuterium,
			int upgradeDefenseTechnologyDeuteriumCost, int upgradeAttackTechnologyDeuteriumCost, Connection conn) {
		super();
		this.planet_id = id;
		this.imagen = imagen;
		this.technologyDefense = technologyDefense;
		this.technologyAttack = technologyAtack;
		this.metal = metal;
		this.deuterium = deuterium;
		this.upgradeDefenseTechnologyDeuteriumCost = upgradeDefenseTechnologyDeuteriumCost;
		this.upgradeAttackTechnologyDeuteriumCost = upgradeAttackTechnologyDeuteriumCost;
		this.army = new ArrayList[7];
		for (int i = 0; i < army.length; i++) {
		    army[i] = new ArrayList<>();
		}
		this.repository = new PlanetRepository(conn);
	}
	
	public void createid() {
		this.planet_id = repository.getNextPlanetId();
	}
	
	public int getNumBatalla() {
		return numBatalla;
	}

	public PlanetRepository getRepository() {
		return repository;
	}
	public void setRepository(PlanetRepository repository) {
		this.repository = repository;
	}

	public void setTechnologyAttack(int technologyAttack) {
		this.technologyAttack = technologyAttack;
	}
	public void setNumBatalla(int numBatalla) {
		this.numBatalla = numBatalla;
	}



	public int getPlanet_id() {
		return planet_id;
	}

	public void setPlanet_id(int planet_id) {
		this.planet_id = planet_id;
	}

	public int getTechnologyAttack() {
		return technologyAttack;
	}

	public void roboResources() {
		int metalLoss = 5000;
	    int deuteriumLoss = 2500;
	    metal -= metalLoss;
	    deuterium -= deuteriumLoss;
	    if (metal < 0) {
	    	metal = 0;
	    }
	    if (deuterium < 0) {
	        deuterium = 0;
	    }
	}
	
	public BufferedImage getImagen() {
		return imagen;
	}

	public int getTechnologyDefense() {
		return technologyDefense;
	}

	public void setTechnologyDefense(int technologyDefense) {
		this.technologyDefense = technologyDefense;
	}

	public int getTechnologyAtack() {
		return technologyAttack;
	}

	public void setTechnologyAtack(int technologyAtack) {
		this.technologyAttack = technologyAtack;
	}

	public int getMetal() {
		return metal;
	}

	public void setMetal(int metal) {
		this.metal = metal;
	}

	public int getDeuterium() {
		return deuterium;
	}

	public void setDeuterium(int deuterium) {
		this.deuterium = deuterium;
	}

	public int getUpgradeDefenseTechnologyDeuteriumCost() {
		return upgradeDefenseTechnologyDeuteriumCost;
	}

	public void setUpgradeDefenseTechnologyDeuteriumCost(int upgradeDefenseTechnologyDeuteriumCost) {
		this.upgradeDefenseTechnologyDeuteriumCost = upgradeDefenseTechnologyDeuteriumCost;
	}

	public int getUpgradeAttackTechnologyDeuteriumCost() {
		return upgradeAttackTechnologyDeuteriumCost;
	}

	public void setUpgradeAttackTechnologyDeuteriumCost(int upgradeAttackTechnologyDeuteriumCost) {
		this.upgradeAttackTechnologyDeuteriumCost = upgradeAttackTechnologyDeuteriumCost;
	}

	public ArrayList<MilitaryUnit>[] getArmy() {
		return army;
	}

	public void setArmy(ArrayList<MilitaryUnit>[] army) {
		this.army = army;
	}

	public void upgradeTechnologyDefense() {
		try {
			if (upgradeDefenseTechnologyDeuteriumCost > deuterium) {
				throw new ResourceException("You don't have enough deuterium to upgrade defense technology.");
			}
			deuterium -= upgradeDefenseTechnologyDeuteriumCost;
			technologyDefense += 1;
			upgradeDefenseTechnologyDeuteriumCost *= 1.1;
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
	}
	

	public void upgradeTechnologyAttack() {
		try {
			if (upgradeAttackTechnologyDeuteriumCost > deuterium) {
				throw new ResourceException("You don't have enough deuterium to upgrade attack technology.");
			}
			deuterium -= upgradeAttackTechnologyDeuteriumCost;
			technologyAttack += 1;
			upgradeAttackTechnologyDeuteriumCost *= 1.1;
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void newLightHunter(int n) {
		for (int i = 0; i < n; i++) {
			try {
				if (Variables.METAL_COST_LIGTHHUNTER > metal || Variables.DEUTERIUM_COST_LIGTHHUNTER > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}

				metal -= Variables.METAL_COST_LIGTHHUNTER;
				deuterium -= Variables.DEUTERIUM_COST_LIGTHHUNTER;
			  army[0].add(new LightHunter(Variables.ARMOR_LIGTHHUNTER + ((technologyDefense * Variables.PLUS_ARMOR_LIGTHHUNTER_BY_TECHNOLOGY) % 1000) ,Variables.BASE_DAMAGE_LIGTHHUNTER + ((technologyAttack*Variables.PLUS_ATTACK_LIGTHHUNTER_BY_TECHNOLOGY)%1000)));
			  repository.construir_unidad(this, "lighthunter", Variables.METAL_COST_LIGTHHUNTER, Variables.DEUTERIUM_COST_LIGTHHUNTER);
			  repository.registrarCreacion(this, "lighthunter", this.getNumBatalla());
			} catch (ResourceException | SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void newHeavyHunter(int n) {
		for (int i = 0; i < n; i++) {
			try {
				if (Variables.METAL_COST_HEAVYHUNTER > metal || Variables.DEUTERIUM_COST_HEAVYHUNTER > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
				metal -= Variables.METAL_COST_HEAVYHUNTER;
				deuterium -= Variables.DEUTERIUM_COST_HEAVYHUNTER;
				army[1].add(new HeavyHunter(Variables.ARMOR_HEAVYHUNTER + ((technologyDefense*Variables.PLUS_ARMOR_HEAVYHUNTER_BY_TECHNOLOGY)%1000),Variables.BASE_DAMAGE_HEAVYHUNTER + ((technologyAttack*Variables.PLUS_ATTACK_HEAVYHUNTER_BY_TECHNOLOGY)%1000)));
				repository.construir_unidad(this, "heavyhunter", Variables.METAL_COST_HEAVYHUNTER, Variables.DEUTERIUM_COST_HEAVYHUNTER);
				repository.registrarCreacion(this, "heavyhunter", this.getNumBatalla());
			} catch (ResourceException | SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void newBattleShip(int n) {
		for (int i = 0; i < n; i++) {
			try {
				if (Variables.METAL_COST_BATTLESHIP > metal || Variables.DEUTERIUM_COST_BATTLESHIP > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
				metal -= Variables.METAL_COST_BATTLESHIP;
				deuterium -= Variables.DEUTERIUM_COST_BATTLESHIP;
			  army[2].add(new BattleShip(Variables.ARMOR_BATTLESHIP + ((technologyDefense*Variables.PLUS_ARMOR_BATTLESHIP_BY_TECHNOLOGY)%1000),Variables.BASE_DAMAGE_BATTLESHIP + ((technologyAttack*Variables.PLUS_ATTACK_BATTLESHIP_BY_TECHNOLOGY)%1000)));
			  repository.construir_unidad(this, "battleship", Variables.METAL_COST_BATTLESHIP, Variables.DEUTERIUM_COST_BATTLESHIP);
			  repository.registrarCreacion(this, "battleship", this.getNumBatalla());
			} catch (ResourceException | SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void newArmoredShip(int n) {
		for (int i = 0; i < n; i++) {
			try {
				if (Variables.METAL_COST_ARMOREDSHIP > metal || Variables.DEUTERIUM_COST_ARMOREDSHIP > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
				metal -= Variables.METAL_COST_ARMOREDSHIP;
				deuterium -= Variables.DEUTERIUM_COST_ARMOREDSHIP;
			  army[3].add(new ArmoredShip(Variables.ARMOR_ARMOREDSHIP + ((technologyDefense*Variables.PLUS_ARMOR_ARMOREDSHIP_BY_TECHNOLOGY)%1000),Variables.BASE_DAMAGE_ARMOREDSHIP + ((technologyAttack*Variables.PLUS_ATTACK_ARMOREDSHIP_BY_TECHNOLOGY)%1000)));
			  repository.construir_unidad(this, "armoredship", Variables.METAL_COST_ARMOREDSHIP, Variables.DEUTERIUM_COST_ARMOREDSHIP);
			  repository.registrarCreacion(this, "armoredship", this.getNumBatalla());
			} catch (ResourceException | SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void newMissileLauncher(int n) {
		for (int i = 0; i < n; i++) {
			try {
				if (Variables.METAL_COST_MISSILELAUNCHER > metal || Variables.DEUTERIUM_COST_MISSILELAUNCHER > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
			  metal -= Variables.METAL_COST_MISSILELAUNCHER;
			  deuterium -= Variables.DEUTERIUM_COST_MISSILELAUNCHER;
			  army[4].add(new MissileLauncher(Variables.ARMOR_MISSILELAUNCHER + ((technologyDefense * Variables.PLUS_ARMOR_MISSILELAUNCHER_BY_TECHNOLOGY) % 1000), Variables.BASE_DAMAGE_MISSILELAUNCHER + ((technologyAttack * Variables.PLUS_ATTACK_MISSILELAUNCHER_BY_TECHNOLOGY) % 1000)));
			  repository.construir_unidad(this, "missilelauncher", Variables.METAL_COST_MISSILELAUNCHER, Variables.DEUTERIUM_COST_MISSILELAUNCHER);
			  repository.registrarCreacion(this, "missilelauncher", this.getNumBatalla());
			} catch (ResourceException | SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void newIonCannon(int n) {
		for (int i = 0; i < n; i++) {
			try {
				if (Variables.METAL_COST_IONCANNON > metal || Variables.DEUTERIUM_COST_IONCANNON > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
				  metal -= Variables.METAL_COST_IONCANNON;
				  deuterium -= Variables.DEUTERIUM_COST_IONCANNON;
			  army[5].add(new IonCannon(Variables.ARMOR_IONCANNON + ((technologyDefense * Variables.PLUS_ARMOR_IONCANNON_BY_TECHNOLOGY) % 1000), Variables.BASE_DAMAGE_IONCANNON + ((technologyAttack * Variables.PLUS_ATTACK_IONCANNON_BY_TECHNOLOGY) % 1000)));
			  repository.construir_unidad(this, "ioncannon", Variables.METAL_COST_IONCANNON, Variables.DEUTERIUM_COST_IONCANNON);
			  repository.registrarCreacion(this, "ioncannon", this.getNumBatalla());
			} catch (ResourceException | SQLException e) {
				System.out.println(e.getMessage());
				break;
			}
		}
	}
	
	public void newPlasmaCannon(int n) {
		for (int i = 0; i < n; i++) {
			try {
				if (Variables.METAL_COST_PLASMACANNON > metal || Variables.DEUTERIUM_COST_PLASMACANNON > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
				metal -= Variables.METAL_COST_PLASMACANNON;
				deuterium -= Variables.DEUTERIUM_COST_PLASMACANNON;
			  army[6].add(new PlasmaCannon(Variables.ARMOR_PLASMACANNON + ((technologyDefense * Variables.PLUS_ARMOR_PLASMACANNON_BY_TECHNOLOGY) % 1000), Variables.BASE_DAMAGE_PLASMACANNON + ((technologyAttack * Variables.PLUS_ATTACK_PLASMACANNON_BY_TECHNOLOGY) % 1000)));
			  repository.construir_unidad(this, "plasmacannon", Variables.METAL_COST_PLASMACANNON, Variables.DEUTERIUM_COST_PLASMACANNON);
			  repository.registrarCreacion(this, "plasmacannon", this.getNumBatalla());
			} catch (ResourceException | SQLException e) {
				System.out.println(e.getMessage());
				break;
			}
		}
	}
	
	public void printStats() {
        String mostrar = "Planet Stats:\n";
        mostrar += "\nTECHNOLOGY\n";
        mostrar += String.format("\n%-30s%d", "Attack Technology", technologyAttack);
        mostrar += String.format("\n%-30s%d\n", "Defense Technology", technologyDefense);
        mostrar += "\nDEFENSES\n";
        mostrar += String.format("\n%-30s%d", "Missile Launcher", army[4].size());
        mostrar += String.format("\n%-30s%d", "Ion Cannon", army[5].size());
        mostrar += String.format("\n%-30s%d", "Plasma Cannon", army[6].size());
        mostrar += "\nFLEET\n";
        mostrar += String.format("\n%-30s%d", "Light Hunter", army[0].size());
        mostrar += String.format("\n%-30s%d", "Heavy Hunter", army[1].size());
        mostrar += String.format("\n%-30s%d", "Battle Ship", army[2].size());
        mostrar += String.format("\n%-30s%d", "Armord Ship", army[3].size());
        mostrar += "\nRESOURCES\n";
        mostrar += String.format("\n%-30s%d", "Metal", metal);
        mostrar += String.format("\n%-30s%d", "Deuterium", deuterium);
        System.out.println(mostrar);
    }
}

class PlanetRepository {
	private Connection conn;
	
	public PlanetRepository(Connection conn) {
		this.conn = conn;
	}
	
	public void crear_planeta(Planet planet) {
		try {
			String query = "INSERT INTO Planet_stats (planet_id, resource_metal_amount, "
					+ "resource_deuterion_amount, technology_defense_level, technology_attack_level, "
					+ "battles_counter, missilelauncher_remaining, ioncannon_remaining, "
					+ "plasmacannon_remaining, lighthunter_remaining, heavyhunter_remaining, "
					+ "battleship_remaining, armoredship_remaining)"
					+ "VALUES (?, ?, ?, ?, ?, 0, 0, 0, 0, 0, 0, 0, 0)";
		
			PreparedStatement pdsmt = conn.prepareStatement(query);
			pdsmt.setInt(1, planet.getPlanet_id());
			pdsmt.setInt(2, planet.getMetal());
			pdsmt.setInt(3, planet.getDeuterium());
			pdsmt.setInt(4, planet.getTechnologyDefense());
			pdsmt.setInt(5, planet.getTechnologyAttack());
			pdsmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error en el planeta: " + e.getMessage());
		}
	}
	
	public int getNextPlanetId() {
	    int nextId = 1;
	    try {
	        String sql = "SELECT COUNT(planet_id) FROM planet_stats";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            nextId = rs.getInt(1) + 1;
	        }
	    } catch (SQLException e) {
	        System.out.println("Error getting next planet_id: " + e.getMessage());
	    }
	    return nextId;
	}
	
	public Connection getConn() {
		return conn;
	}

	public void construir_unidad(Planet planet, String unidad, int metalCost, int deuteriumCost) throws SQLException, ResourceException {
		if (planet.getMetal() < metalCost || planet.getDeuterium() < deuteriumCost) {
			throw new ResourceException("You don't have enough deuterium to upgrade attack technology.");
		}
		
		if (planet.getMetal() > 0 || planet.getDeuterium() > 0) {
			planet.setMetal(planet.getMetal() - metalCost);
			planet.setDeuterium(planet.getDeuterium() - deuteriumCost);
		} else {
			planet.setMetal(0);
			planet.setDeuterium(0);
		}
		
		String query = "UPDATE planet_stats SET resource_metal_amount = resource_metal_amount - ?, "
				+ "resource_deuterion_amount = resource_deuterion_amount - ?, "
				+ unidad + "_remaining = " + unidad + "_remaining + 1 " + "WHERE planet_id = ?";
		
		PreparedStatement pdsmt = conn.prepareStatement(query);
		pdsmt.setInt(1, metalCost);
		pdsmt.setInt(2, deuteriumCost);
		pdsmt.setInt(3, planet.getPlanet_id());
		pdsmt.executeUpdate();
	}
	
	public void actualizarStatsPlaneta(Planet planet, ArrayList<Battle> batallas) {
	    try {
	        int[] unidades = new int[7];
	        for (int i = 0; i < 7; i++) {
	            unidades[i] = planet.getArmy()[i].size();
	        }
	        
	        int metalActual = planet.getMetal();
	        int deuteriumActual = planet.getDeuterium();

	        String sql = "UPDATE planet_stats SET resource_metal_amount = ?, " +
	                     "resource_deuterion_amount = ?, " + 
	                     "technology_defense_level = ?, technology_attack_level = ?, " + 
	                     "battles_counter = ?," +
	                     "missilelauncher_remaining = ?, ioncannon_remaining = ?, plasmacannon_remaining = ?, " +
	                     "lighthunter_remaining = ?, heavyhunter_remaining = ?, battleship_remaining = ?, armoredship_remaining = ? " +
	                     "WHERE planet_id = ?";

	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, metalActual);
	        ps.setInt(2, deuteriumActual);
	        ps.setInt(3, planet.getTechnologyDefense());
	        ps.setInt(4, planet.getTechnologyAttack());
	        ps.setInt(5, batallas.size());
	        ps.setInt(6, unidades[4]); // missilelauncher
	        ps.setInt(7, unidades[5]); // ioncannon
	        ps.setInt(8, unidades[6]); // plasmacannon
	        ps.setInt(9, unidades[0]); // lighthunter
	        ps.setInt(10, unidades[1]); // heavyhunter
	        ps.setInt(11, unidades[2]); // battleship
	        ps.setInt(12, unidades[3]); // armoredship
	        ps.setInt(13, planet.getPlanet_id());

	        ps.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Error to update the information: " + e.getMessage());
	    }
	}

	
	public void iniciarBatalla(Planet planet, int numBatalla) {
	    try {
	        // Verificar si ya existe la batalla
	        String checkQuery = "SELECT 1 FROM battle_stats WHERE planet_id = ? AND num_battle = ?";
	        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
	        checkStmt.setInt(1, planet.getPlanet_id());
	        checkStmt.setInt(2, numBatalla);
	        ResultSet rs = checkStmt.executeQuery();

	        if (rs.next()) {
	            System.out.println("Battle exists in MySQL.");
	            return;
	        }

	        // 1. Insertar nueva batalla
	        String insertBattle = "INSERT INTO battle_stats (planet_id, num_battle, resource_metal_acquired, resource_deuterion_acquired) VALUES (?, ?, 0, 0)";
	        PreparedStatement insertStmt = conn.prepareStatement(insertBattle);
	        insertStmt.setInt(1, planet.getPlanet_id());
	        insertStmt.setInt(2, numBatalla);
	        insertStmt.executeUpdate();

	        // 2. Insertar fila inicial en planet_battle_army
	        String insertArmy = "INSERT INTO planet_battle_army (planet_id, num_battle) VALUES (?, ?)";
	        PreparedStatement armyStmt = conn.prepareStatement(insertArmy);
	        armyStmt.setInt(1, planet.getPlanet_id());
	        armyStmt.setInt(2, numBatalla);
	        armyStmt.executeUpdate();

	        // 3. Insertar fila inicial en planet_battle_defense
	        String insertDefense = "INSERT INTO planet_battle_defense (planet_id, num_battle) VALUES (?, ?)";
	        PreparedStatement defenseStmt = conn.prepareStatement(insertDefense);
	        defenseStmt.setInt(1, planet.getPlanet_id());
	        defenseStmt.setInt(2, numBatalla);
	        defenseStmt.executeUpdate();

	        // 4. Actualizar contador de batallas
	        String updateCounter = "UPDATE planet_stats SET battles_counter = ? WHERE planet_id = ?";
	        PreparedStatement updateStmt = conn.prepareStatement(updateCounter);
	        updateStmt.setInt(1, numBatalla); // El nuevo valor correcto
	        updateStmt.setInt(2, planet.getPlanet_id());
	        updateStmt.executeUpdate();

	    } catch (SQLException e) {
	        System.out.println("Error to start the battle: " + e.getMessage());
	    }
	}
	
	public void actualizarRecursos(Planet planet, int metalWon, int deuteriumWon) {
		try {
			// Almacenar el metal ganado en la batalla
	        String acquiredResource = "UPDATE battle_stats SET resource_metal_acquired = ?, resource_deuterion_acquired = ? WHERE planet_id = ? AND num_battle = ?";
	        PreparedStatement updatePs = conn.prepareStatement(acquiredResource);
	        updatePs.setInt(1, metalWon);
	        updatePs.setInt(2, deuteriumWon);
	        updatePs.setInt(3, planet.getPlanet_id());
	        updatePs.setInt(4, planet.getNumBatalla());
	        updatePs.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error to insert the information: " + e.getMessage());
		}
	}

	
	public void registrarBaja(Planet planet, String unidad, int numBatalla) {
		try {
			String tabla;
			
			unidad = unidad.toLowerCase();
	        
	        // Distinguir por nombre de unidad directamente
	        if (unidad.equals("lighthunter") || unidad.equals("heavyhunter") ||
	        		unidad.equals("battleship") || unidad.equals("armoredship")) {
	            tabla = "planet_battle_army";
	        } else if (unidad.equals("missilelauncher") || unidad.equals("ioncannon") ||
	        		unidad.equals("plasmacannon")) {
	            tabla = "planet_battle_defense";
	        } else {
	            System.out.println("Error to access: " + unidad);
	            return;
	        }
	        
	        String checkQuery = "SELECT 1 FROM " + tabla + " WHERE planet_id = ? AND num_battle = ?";
	        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
	        checkStmt.setInt(1, planet.getPlanet_id());
	        checkStmt.setInt(2, planet.getNumBatalla());
	        ResultSet rs = checkStmt.executeQuery();
	        
	        if (!rs.next()) {
	        	String insertQuery = "INSERT INTO " + tabla + " (planet_id, num_battle) VALUES (?, ?)";
	            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
	            insertStmt.setInt(1, planet.getPlanet_id());
	            insertStmt.setInt(2, planet.getNumBatalla());
	            insertStmt.executeUpdate();
	        }
	        
	        String columna = unidad + "_destroyed";
	        String query = "UPDATE " + tabla + " SET " + columna + " = COALESCE(" + columna + ", 0) + 1 WHERE planet_id = ? AND num_battle = ?";

	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, planet.getPlanet_id());
	        ps.setInt(2, planet.getNumBatalla());
	        ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Error to access: " + e.getMessage());
		}
	}
	
	public void registrarCreacion(Planet planet, String unidad, int numBatalla) {
		try {
			String tabla;
			
			unidad = unidad.toLowerCase();
			
			if (unidad.equals("lighthunter") || unidad.equals("heavyhunter") ||
	        		unidad.equals("battleship") || unidad.equals("armoredship")) {
	            tabla = "planet_battle_army";
	        } else if (unidad.equals("missilelauncher") || unidad.equals("ioncannon") ||
	        		unidad.equals("plasmacannon")) {
	            tabla = "planet_battle_defense";
	        } else {
	            System.out.println("Error to access: " + unidad);
	            return;
	        }
			
			String checkQuery = "SELECT 1 FROM " + tabla + " WHERE planet_id = ? AND num_battle = ?";
	        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
	        checkStmt.setInt(1, planet.getPlanet_id());
	        checkStmt.setInt(2, numBatalla);
	        ResultSet rs = checkStmt.executeQuery();
	        
	        if (!rs.next()) {
	        	String insertQuery = "INSERT INTO " + tabla + " (planet_id, num_battle) VALUES (?, ?)";
	            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
	            insertStmt.setInt(1, planet.getPlanet_id());
	            insertStmt.setInt(2, numBatalla);
	            insertStmt.executeUpdate();
	        }
	        
	        String columna = unidad + "_built";
	        String updateQuery = "UPDATE " + tabla + " SET " + columna + " = COALESCE(" + columna + ", 0) + 1 WHERE planet_id = ? AND num_battle = ?";
	        PreparedStatement ps = conn.prepareStatement(updateQuery);
	        ps.setInt(1, planet.getPlanet_id());
	        ps.setInt(2, numBatalla);
	        ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error to access: " + e.getMessage());
		}
	}
	
	public void guardarLog(Planet planet, String texto, int numLinea, int numBatalla) {
	    try {
	        String query = "INSERT INTO Battle_log (planet_id, num_battle, num_line, log_entry) VALUES (?, ?, ?, ?)";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, planet.getPlanet_id());
	        ps.setInt(2, planet.getNumBatalla());
	        ps.setInt(3, numLinea);
	        ps.setString(4, texto);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Error to save the log (line " + numLinea + "): " + e.getMessage());
	    }
	}
	
	public void eliminarLogAnterior(Planet planet, int numBatalla) {
	    try {
	        String query = "DELETE FROM battle_log WHERE planet_id = ? AND num_battle = ?";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, planet.getPlanet_id());
	        ps.setInt(2, numBatalla);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Error deleting previous log: " + e.getMessage());
	    }
	}
	
	public int getNextBattleNumber(Planet planet) {
		try {
	        String selectQuery = "SELECT battles_counter FROM planet_stats WHERE planet_id = ?";
	        PreparedStatement ps = conn.prepareStatement(selectQuery);
	        ps.setInt(1, planet.getPlanet_id());
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int next = rs.getInt("battles_counter") + 1;

	            String updateQuery = "UPDATE planet_stats SET battles_counter = battles_counter + 1 WHERE planet_id = ?";
	            PreparedStatement update = conn.prepareStatement(updateQuery);
	            update.setInt(1, planet.getPlanet_id());
	            update.executeUpdate();

	            return next;
	        }
	    } catch (SQLException e) {
	        System.out.println("Error getting battle number: " + e.getMessage());
	    }
	    return 1;
	}
}

class ResourceException extends Exception {
	public ResourceException(String mensaje) {
		super(mensaje);
	}
}

abstract class Ship implements MilitaryUnit, Variables{
	private int armor;
	private int initialArmor;
	private int baseDamage;
	private boolean destroyed;
	
	public Ship(int armor, int baseDamage) {
		super();
		this.armor = armor;
		this.initialArmor = armor;
		this.baseDamage = baseDamage;
	}

	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getInitialArmor() {
		return initialArmor;
	}
	public void setInitialArmor(int initialArmor) {
		this.initialArmor = initialArmor;
	}

	public int getBaseDamage() {
		return baseDamage;
	}
	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void trueDestroyed() {
		this.destroyed = true;
	}
	
	
}

interface MilitaryUnit {
	abstract int attack();
	abstract BufferedImage getImagen();
	abstract void tekeDamage(int receivedDamage);
	abstract int getActualArmor();
	abstract int getMetalCost();
	abstract int getDeuteriumCost();
	abstract int getChanceGeneratinWaste();
	abstract int getChanceAttackAgain();
	abstract void resetArmor();
}

interface Variables {
	// resources available to create the first enemy fleet
	public final int DEUTERIUM_BASE_ENEMY_ARMY = 26000;
	public final int METAL_BASE_ENEMY_ARMY = 180000;
	// percentage increase of resources available to create enemy fleet
	public final int ENEMY_FLEET_INCREASE = 6;
	// resources increment every minute
	public final int PLANET_DEUTERIUM_GENERATED = 1500;
	public final int PLANET_METAL_GENERATED = 5000;
	// TECHNOLOGY COST
	public final int UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST = 2000;
	public final int UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST = 2000;
	public final int UPGRADE_PLUS_DEFENSE_TECHNOLOGY_DEUTERIUM_COST = 60;
	public final int UPGRADE_PLUS_ATTACK_TECHNOLOGY_DEUTERIUM_COST = 60;
	// COST SHIPS
	public final int METAL_COST_LIGTHHUNTER = 3000;
	public final int METAL_COST_HEAVYHUNTER = 6500;
	public final int METAL_COST_BATTLESHIP = 45000;
	public final int METAL_COST_ARMOREDSHIP = 30000;
	public final int DEUTERIUM_COST_LIGTHHUNTER = 50;
	public final int DEUTERIUM_COST_HEAVYHUNTER = 50;
	public final int DEUTERIUM_COST_BATTLESHIP = 7000;
	public final int DEUTERIUM_COST_ARMOREDSHIP = 15000;
	// COST DEFENSES
	public final int DEUTERIUM_COST_MISSILELAUNCHER = 0;
	public final int DEUTERIUM_COST_IONCANNON = 500;
	public final int DEUTERIUM_COST_PLASMACANNON = 5000;
	public final int METAL_COST_MISSILELAUNCHER = 2000;
	public final int METAL_COST_IONCANNON = 4000;
	public final int METAL_COST_PLASMACANNON = 50000;
	// array units costs
	public final int[] METAL_COST_UNITS =
	{METAL_COST_LIGTHHUNTER,METAL_COST_HEAVYHUNTER,METAL_COST_BATTLESHIP,METAL_COST_ARMOREDSHIP,METAL_COST_MISSILELAUNCHER,METAL_COST_IONCANNON,METAL_COST_PLASMACANNON};
	public final int[] DEUTERIUM_COST_UNITS = {DEUTERIUM_COST_LIGTHHUNTER,DEUTERIUM_COST_HEAVYHUNTER,DEUTERIUM_COST_BATTLESHIP,DEUTERIUM_COST_ARMOREDSHIP,DEUTERIUM_COST_MISSILELAUNCHER,DEUTERIUM_COST_IONCANNON,DEUTERIUM_COST_PLASMACANNON};
	// BASE DAMAGE SHIPS
	public final int BASE_DAMAGE_LIGTHHUNTER = 80;
	public final int BASE_DAMAGE_HEAVYHUNTER = 150;
	public final int BASE_DAMAGE_BATTLESHIP = 1000;
	public final int BASE_DAMAGE_ARMOREDSHIP = 700;
	// BASE DAMAGE DEFENSES
	public final int BASE_DAMAGE_MISSILELAUNCHER = 80;
	public final int BASE_DAMAGE_IONCANNON = 250;
	public final int BASE_DAMAGE_PLASMACANNON = 2000;
	// REDUCTION_DEFENSE
	public final int REDUCTION_DEFENSE_IONCANNON = 100;
	// ARMOR SHIPS
	public final int ARMOR_LIGTHHUNTER = 400;
	public final int ARMOR_HEAVYHUNTER = 1000;
	public final int ARMOR_BATTLESHIP = 6000;
	public final int ARMOR_ARMOREDSHIP = 8000;
	// ARMOR DEFENSES
	public final int ARMOR_MISSILELAUNCHER = 200;
	public final int ARMOR_IONCANNON = 1200;
	public final int ARMOR_PLASMACANNON = 7000;
	//fleet armor increase percentage per tech level
	public final int PLUS_ARMOR_LIGTHHUNTER_BY_TECHNOLOGY = 5;
	public final int PLUS_ARMOR_HEAVYHUNTER_BY_TECHNOLOGY = 5;
	public final int PLUS_ARMOR_BATTLESHIP_BY_TECHNOLOGY = 5;
	public final int PLUS_ARMOR_ARMOREDSHIP_BY_TECHNOLOGY = 5;
	// defense armor increase percentage per tech level
	public final int PLUS_ARMOR_MISSILELAUNCHER_BY_TECHNOLOGY = 5;
	public final int PLUS_ARMOR_IONCANNON_BY_TECHNOLOGY = 5;
	public final int PLUS_ARMOR_PLASMACANNON_BY_TECHNOLOGY = 5;
	// fleet attack power increase percentage per tech level
	public final int PLUS_ATTACK_LIGTHHUNTER_BY_TECHNOLOGY = 5;
	public final int PLUS_ATTACK_HEAVYHUNTER_BY_TECHNOLOGY = 5;
	public final int PLUS_ATTACK_BATTLESHIP_BY_TECHNOLOGY = 5;
	public final int PLUS_ATTACK_ARMOREDSHIP_BY_TECHNOLOGY = 5;
	// Defense attack power increase percentage per tech level
	public final int PLUS_ATTACK_MISSILELAUNCHER_BY_TECHNOLOGY = 5;
	public final int PLUS_ATTACK_IONCANNON_BY_TECHNOLOGY = 5;
	public final int PLUS_ATTACK_PLASMACANNON_BY_TECHNOLOGY = 5;
	// fleet probability of generating waste
	public final int CHANCE_GENERATNG_WASTE_LIGTHHUNTER = 55;
	public final int CHANCE_GENERATNG_WASTE_HEAVYHUNTER = 65;
	public final int CHANCE_GENERATNG_WASTE_BATTLESHIP = 80;
	public final int CHANCE_GENERATNG_WASTE_ARMOREDSHIP = 90;
	// Defense probability of generating waste
	public final int CHANCE_GENERATNG_WASTE_MISSILELAUNCHER = 55;
	public final int CHANCE_GENERATNG_WASTE_IONCANNON = 65;
	public final int CHANCE_GENERATNG_WASTE_PLASMACANNON = 75;
	// fleet chance to attack again
	public final int CHANCE_ATTACK_AGAIN_LIGTHHUNTER = 3;
	public final int CHANCE_ATTACK_AGAIN_HEAVYHUNTER = 7;
	public final int CHANCE_ATTACK_AGAIN_BATTLESHIP = 45;
	public final int CHANCE_ATTACK_AGAIN_ARMOREDSHIP = 70;
	//Defense chance to attack again
	public final int CHANCE_ATTACK_AGAIN_MISSILELAUNCHER = 5;
	public final int CHANCE_ATTACK_AGAIN_IONCANNON = 12;
	public final int CHANCE_ATTACK_AGAIN_PLASMACANNON = 30;
	// CHANCE ATTACK EVERY UNIT
	// LIGTHHUNTER, HEAVYHUNTER, BATTLESHIP, ARMOREDSHIP, MISSILELAUNCHER, IONCANNON, PLASMACANNON
	public final int[] CHANCE_ATTACK_PLANET_UNITS = {5,10,15,40,5,10,15};
	// LIGTHHUNTER, HEAVYHUNTER, BATTLESHIP, ARMOREDSHIP
	public final int[] CHANCE_ATTACK_ENEMY_UNITS = {10,20,30,40};
	// percentage of waste that will be generated with respect to the cost of the units
	public final int PERCENTATGE_WASTE = 70;
	public final int MIN_PERCENTAGE_TO_WIN = 20;
}

class LightHunter extends Ship {
	private BufferedImage imagen;

	public LightHunter(int armor, int baseDamage) {
		super(armor,baseDamage);
		try {
		    this.imagen = ImageIO.read(new File("./Assets/Asset_LightHunter.png"));
		} catch (IOException e) {
		   	System.out.println(e.getMessage());
		}
	}
	
	public LightHunter() {
		super(ARMOR_LIGTHHUNTER,BASE_DAMAGE_LIGTHHUNTER);
	}

	public BufferedImage getImagen() {
		return imagen;
	}

	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_LIGTHHUNTER;
	}

	public int getDeuteriumCost() {
		return DEUTERIUM_COST_LIGTHHUNTER;
	}

	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_LIGTHHUNTER;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_LIGTHHUNTER;
	}

	public void resetArmor() {
		setArmor(getInitialArmor());
	}
	
}

class HeavyHunter extends Ship {
	private BufferedImage imagen;

	public HeavyHunter(int armor, int baseDamage) {
		super(armor, baseDamage);
		try {
		    this.imagen = ImageIO.read(new File("./Assets/Asset_HeavyHunter.png"));
		} catch (IOException e) {
		   	System.out.println(e.getMessage());
		}
	}
	
	public HeavyHunter() {
		super(ARMOR_HEAVYHUNTER,BASE_DAMAGE_HEAVYHUNTER);
	}
	
	public BufferedImage getImagen() {
		return imagen;
	}
	
	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_HEAVYHUNTER;
	}

	public int getDeuteriumCost() {
		return DEUTERIUM_COST_HEAVYHUNTER;
	}

	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_HEAVYHUNTER;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_HEAVYHUNTER;
	}

	public void resetArmor() {
		setArmor(getInitialArmor());
	}
	
}

class BattleShip extends Ship {
	private BufferedImage imagen;

	public BattleShip(int armor, int baseDamage) {
		super(armor, baseDamage);
		try {
		    this.imagen = ImageIO.read(new File("./Assets/Asset_BattleShip.png"));
		} catch (IOException e) {
		   	System.out.println(e.getMessage());
		}
	}
	
	public BattleShip() {
		super(ARMOR_BATTLESHIP,BASE_DAMAGE_BATTLESHIP);
	}

	public BufferedImage getImagen() {
		return imagen;
	}
	
	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_BATTLESHIP;
	}

	public int getDeuteriumCost() {
		return DEUTERIUM_COST_BATTLESHIP;
	}

	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_BATTLESHIP;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_BATTLESHIP;
	}

	public void resetArmor() {
		setArmor(getInitialArmor());
	}
	
}

class ArmoredShip extends Ship {
	private BufferedImage imagen;

	public ArmoredShip(int armor, int baseDamage) {
		super(armor, baseDamage);
		try {
		    this.imagen = ImageIO.read(new File("./Assets/Asset_ArmoredShip.png"));
		} catch (IOException e) {
		   	System.out.println(e.getMessage());
		}
	}
	
	public ArmoredShip() {
		super(ARMOR_ARMOREDSHIP,BASE_DAMAGE_ARMOREDSHIP);
	}
	

	public BufferedImage getImagen() {
		return imagen;
	}
	
	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_ARMOREDSHIP;
	}

	public int getDeuteriumCost() {
		return DEUTERIUM_COST_ARMOREDSHIP;
	}

	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_ARMOREDSHIP;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_ARMOREDSHIP;
	}

	public void resetArmor() {
		setArmor(getInitialArmor());
	}
	
}

abstract class Defense implements MilitaryUnit,  Variables{
	private int armor;
	private int initialArmor;
	private int baseDamage;
	private boolean destroyed;
	
	public Defense(int armor, int baseDamage) {
		super();
		this.armor = armor;
		this.initialArmor = armor;
		this.baseDamage = baseDamage;
	}
	
	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}
	
	public int getInitialArmor() {
		return initialArmor;
	}
	public void setInitialArmor(int initialArmor) {
		this.initialArmor = initialArmor;
	}
	
	public int getBaseDamage() {
		return baseDamage;
	}
	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}

	public void trueDestroyed() {
		this.destroyed = true;
	}
}

class MissileLauncher extends Defense {
	private BufferedImage imagen;
	
	public MissileLauncher(int armor, int baseDamage) {
		super(armor, baseDamage);	
		try {
		    this.imagen = ImageIO.read(new File("./Assets/Asset_MissileLauncher.png"));
		} catch (IOException e) {
		   	System.out.println(e.getMessage());
		}
	}

	public BufferedImage getImagen() {
		return imagen;
	}
	
	// === METODOS INTERFAZ ===
	public int attack() {
		return getBaseDamage();
	}
	
	public void tekeDamage(int receivedDamage) {
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}
	
	public int getActualArmor() {
		return getArmor();
	}
	
	public int getMetalCost() {
		return METAL_COST_MISSILELAUNCHER;
	}

	public int getDeuteriumCost() {
		return DEUTERIUM_COST_MISSILELAUNCHER;
	}

	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_MISSILELAUNCHER;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_MISSILELAUNCHER;
	}

	public void resetArmor() {
		setArmor(getInitialArmor());
	}
}

class IonCannon extends Defense {
	private BufferedImage imagen;
	
	public IonCannon(int armor, int baseDamage) {
		super(armor, baseDamage);
		try {
		    this.imagen = ImageIO.read(new File("./Assets/Asset_IonCannon.png"));
		} catch (IOException e) {
		   	System.out.println(e.getMessage());
		}
	}
	

	public BufferedImage getImagen() {
		return imagen;
	}
	
	// === METODOS INTERFAZ ===
	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_IONCANNON;
	}

	public int getDeuteriumCost() {
		return DEUTERIUM_COST_IONCANNON;
	}

	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_IONCANNON;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_IONCANNON;
	}

	public void resetArmor() {
		setArmor(getInitialArmor());
	}
}

class PlasmaCannon extends Defense {
	private BufferedImage imagen;

	public PlasmaCannon(int armor, int baseDamage) {
		super(armor, baseDamage);
		try {
		    this.imagen = ImageIO.read(new File("./Assets/Asset_PlasmaCannon.png"));
		} catch (IOException e) {
		   	System.out.println(e.getMessage());
		}
	}

	public BufferedImage getImagen() {
		return imagen;
	}

	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {	
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}
  
  public int getActualArmor() {
			return getArmor();
	}
  
  public int getMetalCost() {
			return METAL_COST_PLASMACANNON;
	}

  public int getDeuteriumCost() {
    return DEUTERIUM_COST_PLASMACANNON;
  }

  public int getChanceGeneratinWaste() {
    return CHANCE_GENERATNG_WASTE_PLASMACANNON;
  }

  public int getChanceAttackAgain() {
    return CHANCE_ATTACK_AGAIN_PLASMACANNON;
  }

  public void resetArmor() {
    setArmor(getInitialArmor());
  }
	
}

class Battle implements Variables {
	private ArrayList<MilitaryUnit>[] planetArmy, enemyArmy;
	private ArrayList[][] armies;
	private String battleDevelopment;
	private int[][] initialCostFleet, resourcesLooses, initialArmies;
	private int initialNumberUnitsPlanet, initialNumberUnitsEnemy;
	private int[] wasteMetalDeuterium, enemyDrops, planetDrops, actualNumberUnitsPlanet, actualNumberUnitsEnemy;
	private Random rand;
	private int numBatalla;
	
	public Battle(ArrayList<MilitaryUnit>[] planetArmy, ArrayList<MilitaryUnit>[] enemyArmy, Planet planeta, int numBatalla) {
	    this.planetArmy = planetArmy;
	    this.enemyArmy = enemyArmy;
	    this.armies = new ArrayList[2][7];
	    armies[0] = planetArmy;
	    armies[1] = enemyArmy;
	    this.battleDevelopment = "";
	    this.initialNumberUnitsPlanet = initialFleetNumber(planetArmy);
	    this.initialNumberUnitsEnemy = initialFleetNumber(enemyArmy);
	    this.initialCostFleet = new int[2][2];
	    this.initialCostFleet[0] = fleetResourceCost(planetArmy);
	    this.initialCostFleet[1] = fleetResourceCost(enemyArmy);
	    this.initialNumberUnitsPlanet = initialFleetNumber(planetArmy);
	    this.initialNumberUnitsEnemy = initialFleetNumber(enemyArmy);
	    
	}
	
	public String getBattleReport(int battles) {
		String mostrar = "";
		String[] unitNames = {"Light Hunter","Heavy Hunter","Battle Ship","Armored Ship","Missile Launcher","Ion Cannon","Plasma Cannon"};
		mostrar += "BATTLE NUMBER: " + battles;
		mostrar += "\nBATTLE STATISTICS\n";
		mostrar += String.format("\n%15s%-5s%-5s%-5s%-5s%-5s", "Initial Army Planet", "Units", "Drops", "Initial Army Enemy", "Units", "Drops");
	    for (int i = 0; i < 7; i++) {
	        int initialPlanet = initialArmies[0][i];
	        int actualPlanet = actualNumberUnitsPlanet[i];
	        int dropsPlanet = initialPlanet - actualPlanet;
	        int initialEnemy = initialArmies[1][i];
	        int actualEnemy = actualNumberUnitsEnemy[i];
	        int dropsEnemy = initialEnemy - actualEnemy;
	        mostrar += String.format("\n%-25s%-10d%-10d%-25s%-10d%-10d",unitNames[i], initialPlanet, dropsPlanet,unitNames[i], initialEnemy, dropsEnemy);
	    }
	    mostrar += "\n\nRESOURCES LOSSES:";
	    mostrar += String.format("\n%-25s%-10s%-10s", "", "Metal", "Deuterium");
	    mostrar += String.format("\n%-25s%-10d%-10d", "Planet", resourcesLooses[0][0], resourcesLooses[0][1]);
	    mostrar += String.format("\n%-25s%-10d%-10d", "Enemy", resourcesLooses[1][0], resourcesLooses[1][1]);
	    mostrar += "\n\nWASTE GENERATED:";
	    mostrar += String.format("\n%-25s%-10d%-10d", "Total Waste", wasteMetalDeuterium[0], wasteMetalDeuterium[1]);
	    mostrar += String.format("\n%-25s%-10d%-10d", "Planet Won", planetDrops[0], planetDrops[1]);
	    mostrar += String.format("\n%-25s%-10d%-10d", "Enemy Won", enemyDrops[0], enemyDrops[1]);
	    int totalPlanetLoss = resourcesLooses[0][0] + resourcesLooses[0][1];
	    int totalEnemyLoss = resourcesLooses[1][0] + resourcesLooses[1][1];
	    String winner;
	    if (totalEnemyLoss > totalPlanetLoss) {
	        winner = "Planet";
	    } else if (totalEnemyLoss < totalPlanetLoss) {
	        winner = "Enemy";
	    } else {
	        winner = "Draw";
	    }
	    mostrar += "\n\nWINNER: " + winner;
		return mostrar;
	}
	public String getBattleDevelopment() {
		return battleDevelopment;
	}
	public void initInitialArmies() {
	    this.initialArmies = new int[2][7];
		for (int i = 0; i < initialArmies[0].length; i++) {
			initialArmies[0][i] = planetArmy[i].size();
			initialArmies[1][i] = enemyArmy[i].size();
		}
	}
	
	public int[] countArmyforType(ArrayList<MilitaryUnit>[] army) {
		int[] count = new int[army.length];
		for (int i = 0; i < army.length; i++) {
			count[i] = army[i].size();
		}
		return count;
	}
	
	public void updateResourcesLoose() {
		int[][] coste_recursos_actuales = new int[2][3];
		coste_recursos_actuales[0] = fleetResourceCost(planetArmy);
		coste_recursos_actuales[1] = fleetResourceCost(enemyArmy);
		resourcesLooses[0][0] = initialCostFleet[0][0] - coste_recursos_actuales[0][0];
		resourcesLooses[0][1] = initialCostFleet[0][1] - coste_recursos_actuales[0][1];
		resourcesLooses[1][0] = initialCostFleet[1][0] - coste_recursos_actuales[1][0];
		resourcesLooses[1][1] = initialCostFleet[1][1] - coste_recursos_actuales[1][1];
		resourcesLooses[0][2] = resourcesLooses[0][0] + 5 * resourcesLooses[0][1];
		resourcesLooses[1][2] = resourcesLooses[1][0] + 5 * resourcesLooses[1][1];
	}
	
	public int[] fleetResourceCost(ArrayList<MilitaryUnit>[] army) {
	    int[] costs = new int[2];
	    costs[0] = 0;
	    costs[1] = 0;
	    for (int i = 0; i < army.length; i++) {
	        for (MilitaryUnit unit : army[i]) {
	            costs[0] += unit.getMetalCost();
	            costs[1] += unit.getDeuteriumCost();
	        }
	    }
	    return costs;
	}
	
	public int initialFleetNumber(ArrayList<MilitaryUnit>[] army) {
		int countfleet = 0;
		for (int i = 0; i < army.length; i++ ) {
			countfleet += army[i].size();
		}
		
		return countfleet;
	}
	
	public void resetArmyArmor() {
		for (int i = 0; i < planetArmy.length; i++) {
			for (int j = 0; j < planetArmy[i].size(); j++) {
				planetArmy[i].get(j).resetArmor();
			}
		}
	}
	
	public void updateactualunits() {
		actualNumberUnitsEnemy = countArmyforType(enemyArmy);
		actualNumberUnitsPlanet = countArmyforType(planetArmy);
	}
	
	public int remainderPercentatgeFleet(ArrayList<MilitaryUnit>[] army) {
		int total = 0;
		for (ArrayList<MilitaryUnit> group : army) {
			total += group.size();
		}
		
		int initialUnits = 0;
		
		try {
			if (army == planetArmy) {
				initialUnits = initialNumberUnitsPlanet;
			} else if (army == enemyArmy) {
				initialUnits = initialNumberUnitsEnemy;
			}
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return (total * 100) / initialUnits;
	}
	
	public int getGroupDefender(ArrayList<MilitaryUnit>[] army) {
		int total = 0;
		for (ArrayList<MilitaryUnit> group : army) {
			total += group.size(); // Calculo de las unidades totales
		}
		
		int initialUnits;
		// Comprobaciones para saber el tipo de unidades que calculamos
		if (army == planetArmy) {
			initialUnits = initialNumberUnitsPlanet;
		} else if (army == enemyArmy) {
			initialUnits = initialNumberUnitsEnemy;
		} else {
			throw new IllegalArgumentException("Unknown army reference passed.");
		}
		
		int totalUnits = initialUnits;
		if (totalUnits <= 0) {
			return -1; // Ya no tiene unidades para defenderse
		}
		
		int random = rand.nextInt(totalUnits);
		int cumulative = 0;
		
		for (int i = 0; i < army.length; i++) {
			cumulative += army[i].size();
			if (random < cumulative) {
				return i;
			}
		}
		
		return -1;
	}
	
	public int getPlanetGroupAttacker() {
		int[] probabilities = Variables.CHANCE_ATTACK_PLANET_UNITS;
		int total = 0;
		for (int probability : probabilities) {
			total += probability;
		}
		
		int random = rand.nextInt(total);
		int cumulative = 0;
		
		for (int i = 0; i < probabilities.length; i++) {
			cumulative += probabilities[i];
			if (random < cumulative) {
				return i;
			}
		}
		
		return -1;
	}
	
	public int getEnemyGroupAttacker() {
		int[] probabilities = Variables.CHANCE_ATTACK_ENEMY_UNITS;
		int total = 0;
		for (int probability : probabilities) {
			total += probability;
		}
		
		int random = rand.nextInt(total);
		int cumulative = 0;
		
		for (int i = 0; i < probabilities.length; i++) {
			cumulative += probabilities[i];
			if (random < cumulative) {
				return i;
			}
		}
		return -1;
	}
	
	// Cuando una nave ataca a otra nave.
	public void ataque_nave(MilitaryUnit atacante, MilitaryUnit atacara,boolean atacamos, Planet planeta) {
		atacara.tekeDamage(atacante.attack());
		if (atacara.getActualArmor() <= 0) {
			if (atacara.getChanceGeneratinWaste() > (int) (Math.random()*100+1)) {
				wasteMetalDeuterium[0] += atacara.getMetalCost();
				wasteMetalDeuterium[1] += atacara.getDeuteriumCost();
				if (atacamos) {
					enemyDrops[0] += atacara.getMetalCost();
					enemyDrops[1] += atacara.getDeuteriumCost();
				} else {
					planetDrops[0] += atacara.getMetalCost();
					planetDrops[1] += atacara.getDeuteriumCost();
				}
			}
			removedestroyships(planeta);
		}
	}
	
	// Elimina todas las naves con la armadura por debajo o igual a 0.
	public void removedestroyships(Planet planeta) {
        for (int i = 0; i < planetArmy.length; i++) {
            for (int j = 0; j < planetArmy[i].size(); j++) {
                if (planetArmy[i].get(j).getActualArmor() <= 0) {
                    if (planetArmy[i].get(j) instanceof Ship) {
                        Ship unidad = (Ship) planetArmy[i].get(j);
                        if (!unidad.isDestroyed()) {
                            unidad.trueDestroyed();
                            planeta.getRepository().registrarBaja(planeta, unidad.getClass().getSimpleName().toLowerCase(), numBatalla);
                        }
                    } else if (planetArmy[i].get(j) instanceof Defense) {
                        Defense unidad = (Defense) planetArmy[i].get(j);
                        if (!unidad.isDestroyed()) {
                            unidad.trueDestroyed();
                            planeta.getRepository().registrarBaja(planeta, unidad.getClass().getSimpleName().toLowerCase(), numBatalla);
                        }
                    }
                    planetArmy[i].remove(j);
                }
            }
        }
        for (int i = 0; i < enemyArmy.length; i++) {
            for (int j = 0; j < enemyArmy[i].size(); j++) {
                if (enemyArmy[i].get(j).getActualArmor() <= 0) {
                    enemyArmy[i].remove(j);
                }
            }
        }
    }

	
	// Se mira que MilitaryUnit es y de ahí se hace de manera random en base a su % de CHANGE_ATTACK_AGAIN si vuelve a atacar o no.
	public boolean againattack(MilitaryUnit atacante) {
		if (atacante.getChanceAttackAgain() > (int) (Math.random()*100+1)) {
			return true;
		}
		return false;
	}
  
	// Aqui se reune toda la batalla.
	public void startBattle(Planet planeta) {
	    rand = new Random();
	    wasteMetalDeuterium = new int[2];
	    enemyDrops = new int[2];
	    planetDrops = new int[2];
	    resourcesLooses = new int[2][3];

	    initInitialArmies();     // Guarda conteo inicial
	    resetArmyArmor();        // Reinicia armaduras

	    boolean planetAttacks = rand.nextBoolean(); // decide quién empieza

	    int turnosSinAtaque = 0;

	    while (remainderPercentatgeFleet(planetArmy) > MIN_PERCENTAGE_TO_WIN &&
	           remainderPercentatgeFleet(enemyArmy) > MIN_PERCENTAGE_TO_WIN &&
	           turnosSinAtaque < 20) {

	        updateactualunits();

	        ArrayList<MilitaryUnit>[] attackingArmy;
	        ArrayList<MilitaryUnit>[] defendingArmy;
	        String atacante;

	        if (planetAttacks) {
	            attackingArmy = planetArmy;
	            defendingArmy = enemyArmy;
	            atacante = "Planet";
	        } else {
	            attackingArmy = enemyArmy;
	            defendingArmy = planetArmy;
	            atacante = "Fleet Enemy";
	        }

	        // Elegimos un grupo atacante válido (máximo 10 intentos para evitar bucle infinito)
	        int attackingGroup = -1;
	        for (int i = 0; i < 10; i++) {
	            if (planetAttacks) {
	                attackingGroup = rand.nextInt(7); // incluye flota y defensas
	            } else {
	                attackingGroup = getEnemyGroupAttacker(); // solo flota
	            }
	            if (attackingGroup >= 0 && attackingArmy[attackingGroup].size() > 0) {
	                break;
	            }
	        }

	        if (attackingGroup == -1 || attackingArmy[attackingGroup].isEmpty()) {
	            planetAttacks = !planetAttacks;
	            turnosSinAtaque++;
	            continue;
	        }

	        MilitaryUnit attacker = attackingArmy[attackingGroup]
	                                .get(attackingArmy[attackingGroup].size() - 1);

	        boolean repeatAttack;
	        do {
	            int defendingGroup = getGroupDefender(defendingArmy);

	            if (defendingGroup == -1 || defendingArmy[defendingGroup].isEmpty()) {
	                break;
	            }

	            MilitaryUnit defender = defendingArmy[defendingGroup]
	                                    .get(defendingArmy[defendingGroup].size() - 1);

	            // Registro de ataque
	            battleDevelopment += "Attacks " + atacante + ": "
	                                + attacker.getClass().getSimpleName()
	                                + " attacks " + defender.getClass().getSimpleName() + "\n";

	            int damage = attacker.attack();
	            battleDevelopment += attacker.getClass().getSimpleName()
	                                + " generates the damage = " + damage + "\n";

	            int newArmor = Math.max(0, defender.getActualArmor() - damage);
	            battleDevelopment += defender.getClass().getSimpleName()
	                                + " stays with armor = " + newArmor + "\n";

	            ataque_nave(attacker, defender, planetAttacks, planeta);

	            repeatAttack = againattack(attacker);

	        } while (repeatAttack);

	        removedestroyships(planeta);
	        planetAttacks = !planetAttacks;
	        turnosSinAtaque = 0; // hubo combate
	    }

	    updateResourcesLoose();

	    int totalPlanetLoss = resourcesLooses[0][0] + resourcesLooses[0][1];
	    int totalEnemyLoss = resourcesLooses[1][0] + resourcesLooses[1][1];
	    int bonificacion = 10;
	    if (totalEnemyLoss > totalPlanetLoss) {
	    	bonificacion = planeta.getTechnologyAtack();
	        int metalGanado = (resourcesLooses[1][0] * (10 + bonificacion)) / 10;
	        int deutGanado = (resourcesLooses[1][1] * (10 + bonificacion)) / 10;
	        planeta.setMetal(planeta.getMetal() + metalGanado);
	        planeta.setDeuterium(planeta.getDeuterium() + deutGanado);
	    } else {
	        planeta.roboResources();
	    }
	    resetArmyArmor();
	    
	    planeta.getRepository().actualizarRecursos(planeta, resourcesLooses[1][1] * (1 + bonificacion / 10), resourcesLooses[1][0] * (1 + bonificacion / 10));
        planeta.getRepository().eliminarLogAnterior(planeta, numBatalla);
        
        String[] lineas = getBattleDevelopment().split("\n");
        for (int i = 0; i < lineas.length; i++) {
            planeta.getRepository().guardarLog(planeta, lineas[i], i + 1, numBatalla);
        }
	}
}

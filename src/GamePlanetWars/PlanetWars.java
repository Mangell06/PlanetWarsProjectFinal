package GamePlanetWars;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PlanetWars {

	public static void main(String[] args) {
		new VentanaJuego();
	}

}

class VentanaJuego extends JFrame {
	PanelIniciarSesion iniciarSesion;
	public VentanaJuego() {
		setTitle("Planet Wars");
		setSize(800,600);
		iniciarSesion = new PanelIniciarSesion(this);
		add(iniciarSesion);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void mostrarPanelJuego(User user, Planet planet) {
		getContentPane().removeAll();
		add(new Game(user, planet)); 
		revalidate();
		repaint();
	}
}

class Game extends JPanel {
    private Planet planeta;
    private User usuario;
    private JPanel north,stats,barraderechastats, barraizquierdastats, barraabajostats, shop,battles,reports;
    private FondoPanel planetstat;
    private JLabel name, metal, deuterium, leveltechnologyattack, leveltechnologydefense, nosubirataque, nosubirdefensa, precioataque, preciodefensa;
    private JTabbedPane menu;
    private BufferedImage[] imagenesUnidades;
    private JButton subirataque, subirdefensa;
    private JTextArea battledevelopment, battlereport;
    

    public Game(User usuario, Planet planeta) {
        this.usuario = usuario;
        this.planeta = planeta;
        setLayout(new BorderLayout());
        try {
        	imagenesUnidades = new BufferedImage[7];
            imagenesUnidades[0] = ImageIO.read(new File(".\\src\\Assets\\Asset_LightHunter.png"));
            imagenesUnidades[1] = ImageIO.read(new File(".\\src\\Assets\\Asset_HeavyHunter.png"));
            imagenesUnidades[2] = ImageIO.read(new File(".\\src\\Assets\\Asset_BattleShip.png"));
            imagenesUnidades[3] = ImageIO.read(new File(".\\src\\Assets\\Asset_ArmoredShip.png"));
            imagenesUnidades[4] = ImageIO.read(new File(".\\src\\Assets\\Asset_MissileLauncher.png"));
            imagenesUnidades[5] = ImageIO.read(new File(".\\src\\Assets\\Asset_IonnCannon.png"));
            imagenesUnidades[6] = ImageIO.read(new File(".\\src\\Assets\\Asset_PlasmaCannon.png"));
        } catch (IOException e) {
            System.out.println("Error loading unit images: " + e.getMessage());
        }
        name = new JLabel(usuario.getName());
        north = new JPanel();
        add(north,BorderLayout.NORTH);
        north.setBackground(Color.GREEN);
        north.add(name);
        menu = new JTabbedPane();
        stats = new JPanel();
        metal = new JLabel("Metal");
        deuterium = new JLabel("Deuterium");
        leveltechnologyattack = new JLabel("Level Technology Attack");
        leveltechnologydefense = new JLabel("Level Technology Defense");
        metal.setText("Metal: " + planeta.getMetal());
        deuterium.setText("Deuterium: " + planeta.getDeuterium());
        leveltechnologyattack.setText("Tech attack: " + planeta.getTechnologyAtack());
        leveltechnologydefense.setText("Tech Defense: " + planeta.getTechnologyDefense());
        stats.setLayout(new BorderLayout());
        planetstat = new FondoPanel(planeta.getImagen());
        barraderechastats = new JPanel();
        barraizquierdastats = new JPanel();
        barraizquierdastats.add(metal);
        barraizquierdastats.add(deuterium);
        barraizquierdastats.add(leveltechnologyattack);
        barraizquierdastats.add(leveltechnologydefense);
        shop = new JPanel();
        JPanel mensajesPanel = new JPanel();
        mensajesPanel.setLayout(new BoxLayout(mensajesPanel, BoxLayout.Y_AXIS));
        barraabajostats = new JPanel();
        JLabel texto = new JLabel("");
        mensajesPanel.add(texto);
        JLabel mensajeCompra = new JLabel("");
        JLabel unidadesCompradas = new JLabel("");
        texto.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensajeCompra.setAlignmentX(Component.CENTER_ALIGNMENT);
        unidadesCompradas.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensajesPanel.add(mensajeCompra);
        mensajesPanel.add(unidadesCompradas);
        shop.add(mensajesPanel);
        mensajesPanel.setPreferredSize(new Dimension(800,100));
        mensajesPanel.setMaximumSize(new Dimension(800,100));
        mensajesPanel.setMinimumSize(new Dimension(800,100));
        mensajesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        for (int i = 0; i < planeta.getArmy().length; i++) {
        	final int index = i;
        	FondoPanel naveimagen = new FondoPanel(imagenesUnidades[i]); 
        	naveimagen.setPreferredSize(new Dimension(80,40)); 
        	naveimagen.setMinimumSize(new Dimension(80,40)); 
        	naveimagen.setMaximumSize(new Dimension(80,40));
    		barraderechastats.add(naveimagen);
    		JPanel compra = new JPanel();
    		compra.setLayout(new BoxLayout(compra, BoxLayout.Y_AXIS));
    		FondoPanel naveimagenshop = new FondoPanel(imagenesUnidades[i]);
        	naveimagenshop.setPreferredSize(new Dimension(60,40)); 
        	naveimagenshop.setMinimumSize(new Dimension(60,40)); 
        	naveimagenshop.setMaximumSize(new Dimension(60,40));
        	compra.setPreferredSize(new Dimension(120,120)); 
        	compra.setMinimumSize(new Dimension(120,120)); 
        	compra.setMaximumSize(new Dimension(120,120));
        	compra.add(Box.createVerticalStrut(10));
    		compra.add(naveimagenshop);
        	compra.add(Box.createVerticalStrut(10));
        	JTextField cantidad = new JTextField("1");
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
    		compra.setBackground(Color.YELLOW);
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
    		barraderechastats.add(new JLabel("" + planeta.getArmy()[i].size() + "\n"));
        }
        stats.add(planetstat, BorderLayout.CENTER);
        stats.add(barraderechastats,BorderLayout.EAST);
        stats.add(barraizquierdastats,BorderLayout.WEST);
        stats.add(barraabajostats, BorderLayout.SOUTH);
        barraizquierdastats.setPreferredSize(new Dimension(125,600));
        barraizquierdastats.setMinimumSize(new Dimension(125,600));
        barraizquierdastats.setMaximumSize(new Dimension(125,600));
        barraderechastats.setPreferredSize(new Dimension(125,600));
        barraderechastats.setMinimumSize(new Dimension(125,600));
        barraderechastats.setMaximumSize(new Dimension(125,600));
        barraabajostats.setPreferredSize(new Dimension(800,150));
        barraabajostats.setMinimumSize(new Dimension(800,150));
        barraabajostats.setMaximumSize(new Dimension(800,150));
        nosubirataque = new JLabel("");
        subirataque = new JButton("Update Level technology attack");
        nosubirdefensa = new JLabel("");
        subirdefensa = new JButton("Update Level technology defense");
        subirataque.addActionListener(new EventosJuego());
        subirdefensa.addActionListener(new EventosJuego());
        barraabajostats.setLayout(new BoxLayout(barraabajostats,BoxLayout.Y_AXIS));
        barraabajostats.add(nosubirataque);
        nosubirataque.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraabajostats.add(Box.createVerticalStrut(10));
        precioataque = new JLabel("Price Deuterium: " + planeta.getUpgradeAttackTechnologyDeuteriumCost());
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
        barraabajostats.add(preciodefensa);
        preciodefensa.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraabajostats.add(Box.createVerticalStrut(5));
        barraabajostats.add(subirdefensa);
        subirdefensa.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraabajostats.add(Box.createVerticalStrut(10));
        menu.addTab("Stats", stats);
        menu.addTab("Shop", shop);
        battles = new JPanel();
        menu.addTab("Battle develop", battles);
        reports = new JPanel();
        menu.addTab("Battle reports", reports);
        add(menu,BorderLayout.CENTER);
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
    
    public void update_information() {
        metal.setText("Metal: " + planeta.getMetal());
        deuterium.setText("Deuterium: " + planeta.getDeuterium());
        leveltechnologyattack.setText("Tech attack: " + planeta.getTechnologyAtack());
        leveltechnologydefense.setText("Tech Defense: " + planeta.getTechnologyDefense());
        precioataque.setText("Price Deuterium: " + planeta.getUpgradeAttackTechnologyDeuteriumCost());
        preciodefensa.setText("Price Deuterium: " + planeta.getUpgradeDefenseTechnologyDeuteriumCost());
        barraderechastats.removeAll();
        for (int i = 0; i < planeta.getArmy().length; i++) {
            FondoPanel naveimagen = new FondoPanel(imagenesUnidades[i]); 
            naveimagen.setPreferredSize(new Dimension(80,40)); 
            barraderechastats.add(naveimagen);
            barraderechastats.add(new JLabel("" + planeta.getArmy()[i].size()));
        }

        barraderechastats.revalidate();
        barraderechastats.repaint();
    }
}

class FondoPanel extends JPanel {

    private BufferedImage imagen;

    public FondoPanel(BufferedImage imagen) {
        this.imagen = imagen;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            Image escalada = imagen.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            g.drawImage(escalada, 0, 0, this);
        }
    }
}

class PanelIniciarSesion extends JPanel {
	private JPanel north,center,south, paneldeingreso;
	private VentanaJuego ventana;
	private JLabel title, error;
	private JButton startaccount, createaccount;
	private JTextField name;
	private JPasswordField pass;
	
	PanelIniciarSesion(VentanaJuego ventanita) {
		setLayout(new BorderLayout());
		north = new JPanel();
		center = new JPanel();
		paneldeingreso = new JPanel();
		south = new JPanel();
		title = new JLabel("Sesion");
		name = new JTextField("Nameuser");
		pass = new JPasswordField("Password");
		ventana = ventanita;
		error = new JLabel("");
		error.setPreferredSize(new Dimension(250, 40));
		error.setMaximumSize(new Dimension(250, 40));
		error.setMinimumSize(new Dimension(250, 40));
		startaccount = new JButton("Start Account");
		createaccount = new JButton("Create Account");
		north.setBackground(Color.green);
		center.setBackground(Color.white);
		paneldeingreso.setBackground(Color.white);
		south.setBackground(Color.green);
		north.add(title);
		center.setLayout(new BoxLayout(center,BoxLayout.X_AXIS));
		paneldeingreso.setLayout(new BoxLayout(paneldeingreso, BoxLayout.Y_AXIS));
		paneldeingreso.add(Box.createVerticalStrut(20));
		paneldeingreso.add(name);
		paneldeingreso.add(Box.createVerticalStrut(10));
		paneldeingreso.add(pass);
		paneldeingreso.add(Box.createVerticalStrut(10));
		paneldeingreso.add(error);
		center.add(Box.createHorizontalStrut(325));
		center.add(paneldeingreso);
		south.add(startaccount);
		south.add(createaccount);
		name.setToolTipText("Nameuser");
		pass.setToolTipText("Password");
		name.setMaximumSize(new Dimension(250,40));
		pass.setMaximumSize(new Dimension(250,40));
		north.setMaximumSize(new Dimension(800,40));
		center.setMaximumSize(new Dimension(800,40));
		south.setMaximumSize(new Dimension(800,40));
		add(north,BorderLayout.NORTH);
		add(center,BorderLayout.CENTER);
		add(south,BorderLayout.SOUTH);
		startaccount.addActionListener(new EventosSesion());
		createaccount.addActionListener(new EventosSesion());
	}
	
	class EventosSesion implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			User usuario;
			switch(e.getActionCommand()) {
			case "Create Account":
				String contraseña = new String(pass.getPassword());
				if (name.getText().length() > 12 || name.getText().length() < 3 || !contraseña.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{6,}$")) {
					error.setText("Nombre o contraseña poco seguros");
				} else {
					try {
					    BufferedImage imagen = ImageIO.read(new File(".\\src\\Assets\\Asset_EarthBasic.png"));
					    ventana.mostrarPanelJuego(new User(name.getText(),contraseña),new Planet(imagen, 1, 1, 100000, 1000000, 40000, 20000));
					} catch (IOException z) {
					   	System.out.println(z.getMessage());
					}
				}
				break;
			case "Start Account":
				break;
			}
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
	
	public void elegirIDAutomaticamente() {}

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
	private BufferedImage imagen;
	private int technologyDefense;
	private int technologyAttack;
	private int metal;
	private int deuterium;
	private int upgradeDefenseTechnologyDeuteriumCost;
	private int upgradeAttackTechnologyDeuteriumCost;
	private ArrayList<MilitaryUnit>[] army;
	
	public Planet(BufferedImage imagen,int technologyDefense, int technologyAtack, int metal, int deuterium,
			int upgradeDefenseTechnologyDeuteriumCost, int upgradeAttackTechnologyDeuteriumCost) {
		super();
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
			  army[0].add(new LigthHunter(Variables.ARMOR_LIGTHHUNTER + ((technologyDefense * Variables.PLUS_ARMOR_LIGTHHUNTER_BY_TECHNOLOGY) % 1000) ,Variables.BASE_DAMAGE_LIGTHHUNTER + ((technologyAttack*Variables.PLUS_ATTACK_LIGTHHUNTER_BY_TECHNOLOGY)%1000)));
			} catch (ResourceException e) {
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
			} catch (ResourceException e) {
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
			} catch (ResourceException e) {
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
			} catch (ResourceException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void newMissileLauncher(int n) {
		int contador = 0;
		for (int i = 0; i < n; i++) {
			try {
				if (Variables.METAL_COST_MISSILELAUNCHER > metal || Variables.DEUTERIUM_COST_MISSILELAUNCHER > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
			  metal -= Variables.METAL_COST_MISSILELAUNCHER;
			  deuterium -= Variables.DEUTERIUM_COST_MISSILELAUNCHER;
			  army[4].add(new MissileLauncher(Variables.ARMOR_MISSILELAUNCHER + ((technologyDefense * Variables.PLUS_ARMOR_MISSILELAUNCHER_BY_TECHNOLOGY) % 1000), Variables.BASE_DAMAGE_MISSILELAUNCHER + ((technologyAttack * Variables.PLUS_ATTACK_MISSILELAUNCHER_BY_TECHNOLOGY) % 1000)));
				contador += 1;
			} catch (ResourceException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("added " + contador + "Missile Launcher");
	}
	
	public void newIonCannon(int n) {
		int contador = 0;
		for (int i = 0; i < n; i++) {
			try {
				if (Variables.METAL_COST_IONCANNON > metal || Variables.DEUTERIUM_COST_IONCANNON > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
				  metal -= Variables.METAL_COST_IONCANNON;
				  deuterium -= Variables.DEUTERIUM_COST_IONCANNON;
			  army[5].add(new IonCannon(Variables.ARMOR_IONCANNON + ((technologyDefense * Variables.PLUS_ARMOR_IONCANNON_BY_TECHNOLOGY) % 1000), Variables.BASE_DAMAGE_IONCANNON + ((technologyAttack * Variables.PLUS_ATTACK_IONCANNON_BY_TECHNOLOGY) % 1000)));
			  contador += 1;
			} catch (ResourceException e) {
				System.out.println(e.getMessage());
				break;
			}
		}
		System.out.println("added " + contador + "Ion Cannon");
	}
	
	public void newPlasmaCannon(int n) {
		int contador = 0;
		for (int i = 0; i < n; i++) {
			try {
				if (Variables.METAL_COST_PLASMACANNON > metal || Variables.DEUTERIUM_COST_PLASMACANNON > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
				metal -= Variables.METAL_COST_PLASMACANNON;
				deuterium -= Variables.DEUTERIUM_COST_PLASMACANNON;
			  army[6].add(new PlasmaCannon(Variables.ARMOR_PLASMACANNON + ((technologyDefense * Variables.PLUS_ARMOR_PLASMACANNON_BY_TECHNOLOGY) % 1000), Variables.BASE_DAMAGE_PLASMACANNON + ((technologyAttack * Variables.PLUS_ATTACK_PLASMACANNON_BY_TECHNOLOGY) % 1000)));
			  contador += 1;
			} catch (ResourceException e) {
				System.out.println(e.getMessage());
				break;
			}
		}
		System.out.println("added " + contador + "Plasma Cannon");
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

class ResourceException extends Exception {
	public ResourceException(String mensaje) {
		super(mensaje);
	}
}

abstract class ship implements MilitaryUnit, Variables{
	private int armor;
	private int initialArmor;
	private int baseDamage;
	
	public ship(int armor, int baseDamage) {
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

class LigthHunter extends ship {
	private BufferedImage imagen;

	public LigthHunter(int armor, int baseDamage) {
		super(armor,baseDamage);
		try {
		    this.imagen = ImageIO.read(new File(".\\src\\Assets\\Asset_LightHunter.png"));
		} catch (IOException e) {
		   	System.out.println(e.getMessage());
		}
	}
	
	public LigthHunter() {
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

class HeavyHunter extends ship {
	private BufferedImage imagen;

	public HeavyHunter(int armor, int baseDamage) {
		super(armor, baseDamage);
		try {
		    this.imagen = ImageIO.read(new File(".\\src\\Assets\\Asset_HeavyHunter.png"));
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

class BattleShip extends ship {
	private BufferedImage imagen;

	public BattleShip(int armor, int baseDamage) {
		super(armor, baseDamage);
		try {
		    this.imagen = ImageIO.read(new File(".\\src\\Assets\\Asset_BattleShip.png"));
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

class ArmoredShip extends ship {
	private BufferedImage imagen;

	public ArmoredShip(int armor, int baseDamage) {
		super(armor, baseDamage);
		try {
		    this.imagen = ImageIO.read(new File(".\\src\\Assets\\Asset_ArmoredShip.png"));
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
}

class MissileLauncher extends Defense {
	private BufferedImage imagen;
	
	public MissileLauncher(int armor, int baseDamage) {
		super(armor, baseDamage);	
		try {
		    this.imagen = ImageIO.read(new File(".\\src\\Assets\\Asset_MissileLauncher.png"));
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
		    this.imagen = ImageIO.read(new File(".\\src\\Assets\\Asset_IonnCannon.png"));
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
		    this.imagen = ImageIO.read(new File(".\\src\\Assets\\Asset_PlasmaCannon.png"));
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
	
	public Battle(ArrayList<MilitaryUnit>[] planetArmy, ArrayList<MilitaryUnit>[] enemyArmy) {
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
	    String winner = (totalPlanetLoss < totalEnemyLoss) ? "Planet" : "Enemy";
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
		resourcesLooses[0][0] = coste_recursos_actuales[0][0] - initialCostFleet[0][0];
		resourcesLooses[0][1] = coste_recursos_actuales[0][1] - initialCostFleet[0][1];
		resourcesLooses[1][0] = coste_recursos_actuales[1][0] - initialCostFleet[1][0];
		resourcesLooses[1][1] = coste_recursos_actuales[1][1] - initialCostFleet[1][1];
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
	public void ataque_nave(MilitaryUnit atacante, MilitaryUnit atacara,boolean atacamos) {
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
			removedestroyships();
		}
	}
	
	// Elimina todas las naves con la armadura por debajo o igual a 0.
	public void removedestroyships() {
		for (int i = 0; i < planetArmy.length; i++) {
			for (int j = 0; j < planetArmy[i].size(); j++) {
				if (planetArmy[i].get(j).getActualArmor() <= 0) {
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
	public void startBattle() {
	    rand = new Random();
	    wasteMetalDeuterium = new int[2];
	    enemyDrops = new int[2];
	    planetDrops = new int[2];
	    resourcesLooses = new int[2][3];

	    initInitialArmies(); // Guarda conteo inicial
	    resetArmyArmor();    // Reinicia armaduras

	    boolean planetAttacks = rand.nextBoolean(); // decide quién empieza

	    while (remainderPercentatgeFleet(planetArmy) > MIN_PERCENTAGE_TO_WIN &&
	           remainderPercentatgeFleet(enemyArmy) > MIN_PERCENTAGE_TO_WIN) {

	        updateactualunits(); // Actualiza unidades actuales

	        battleDevelopment += "********************CHANGE ATTACKER********************\n";

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

	        int attackingGroup;
	        do {
		        if (planetAttacks) {
		            attackingGroup = getPlanetGroupAttacker();
		        } else {
		            attackingGroup = getEnemyGroupAttacker();
		        }
	        } while (attackingArmy[attackingGroup].size() == 0);

	        if (attackingGroup == -1 || attackingArmy[attackingGroup].isEmpty()) {
	            planetAttacks = !planetAttacks;
	            continue;
	        }
	        
	        
	        MilitaryUnit attacker = attackingArmy[attackingGroup].get(attackingArmy[attackingGroup].size()-1);

	        boolean repeatAttack;
	        do {
	            int defendingGroup = getGroupDefender(defendingArmy);
	            if (defendingGroup == -1 || defendingArmy[defendingGroup].isEmpty()) break;

	            MilitaryUnit defender = defendingArmy[defendingGroup].get(defendingArmy[defendingGroup].size()-1);

	            battleDevelopment += String.format("Attacks %s: %s attacks %s\n",
	                    atacante,
	                    attacker.getClass().getSimpleName(),
	                    defender.getClass().getSimpleName());

	            int damage = attacker.attack();
	            battleDevelopment += attacker.getClass().getSimpleName() + " generates the damage = " + damage + "\n";

	            int newArmor = Math.max(0, defender.getActualArmor() - damage);
	            battleDevelopment += defender.getClass().getSimpleName() + " stays with armor = " + newArmor + "\n";

	            ataque_nave(attacker, defender, planetAttacks);

	            repeatAttack = againattack(attacker);

	        } while (repeatAttack);

	        removedestroyships();
	        planetAttacks = !planetAttacks; // cambia el turno
	    }
	    updateResourcesLoose(); // Calcula pérdidas al finalizar
	    resetArmyArmor();    // Reinicia armaduras al final
	}
}

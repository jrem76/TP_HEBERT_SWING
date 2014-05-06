import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import resume.Resume;


public class Form_View {
	private JFrame frame;
	private Resume cv;
	private JButton add, ajouter_champs;
	private JComboBox<String> jcb;
	private JTextField lastName, firstName;
	private Map<String, JTextField[]> map;
	private JPanel panel;
	private int nb_ecole, nb_experience, nb_langue, nb_competence;
	
	public Form_View() {
		createView();
		placeComponents();
		createController();
		nb_ecole = 0;
		nb_experience = 0;
		nb_langue = 0;
		nb_competence = 0;
	}

	private void createController() {
		// TODO Auto-generated method stub
		ajouter_champs.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(jcb.getSelectedItem());
				String item = (String) jcb.getSelectedItem();
				if (item.equals("Scolarite")) {
					JTextField[] tab = new JTextField[4];
					tab[0] = new JTextField("Diplome", 20);
					tab[1] = new JTextField("Lieu de preparation", 20);
					tab[2] = new JTextField("Ville", 20);
					tab[3] = new JTextField("Date", 20);
					
					map.put("Scolarite"+nb_ecole, tab);
					JPanel tmp = new JPanel(new GridLayout(1, 5)); {
						tmp.add(new JLabel("Scolarite : "));
						for (int i = 0; i < 4; i ++) {
							tmp.add(tab[i]);
						}
					}
					panel.add(tmp);
					frame.revalidate();
					nb_ecole++;
				} else if (item.equals("Experience")) {
					JTextField[] tab = new JTextField[3];
					tab[0] = new JTextField("Nom de l'entreprise", 20);
					tab[1] = new JTextField("Poste occupe", 20);
					tab[2] = new JTextField("Date", 20);

					map.put("Experience"+nb_experience, tab);
					
					JPanel tmp = new JPanel(new GridLayout(1, 4)); {
						tmp.add(new JLabel("Experience : "));
						for (int i = 0; i < 3; i ++) {
							tmp.add(tab[i]);
						}
					}
					panel.add(tmp);
					frame.revalidate();
					
					nb_experience++;
				} else if (item.equalsIgnoreCase("Competence")) {
					JTextField[] tab = new JTextField[2];
					tab[0] = new JTextField("Nom", 20);
					tab[1] = new JTextField("Niveau", 20);

					map.put("Competence"+nb_competence, tab);
					JPanel tmp = new JPanel(new GridLayout(1, 3)); {
						tmp.add(new JLabel("Competence : "));
						for (int i = 0; i < 2; i ++) {
							tmp.add(tab[i]);
						}
					}
					panel.add(tmp);
					frame.revalidate();
					nb_competence++;
				} else if (item.equals("Langue")) {
					JTextField[] tab = new JTextField[2];
					tab[0] = new JTextField("Nom", 20);
					tab[1] = new JTextField("Niveau", 20);
					map.put("Langue"+nb_langue, tab);
					System.out.println(map.get("Langue"+nb_langue).length);
					JPanel tmp = new JPanel(new GridLayout(1, 3)); {
						tmp.add(new JLabel("Langue : "));
						for (int i = 0; i < 2; i ++) {
							tmp.add(tab[i]);
						}
					}
					panel.add(tmp);
					frame.revalidate();
					nb_langue++;
					
				}
				frame.pack();
			}
		});
	}

	private void placeComponents() {
		// TODO Auto-generated method stub
		JPanel p = new JPanel(); {
			p.add(jcb);
			p.add(ajouter_champs);
			p.add(add);
		}
		frame.add(p, BorderLayout.NORTH);
		JTextField[] tab = new JTextField[4];
		tab[0] = new JTextField("Diplome", 20);
		tab[1] = new JTextField("Lieu de preparation", 20);
		tab[2] = new JTextField("Ville", 20);
		tab[3] = new JTextField("Date", 20);
		JPanel tmp = new JPanel(new GridLayout(1, 5)); {
			tmp.add(new JLabel("Scolarite : "));
			for (int i = 0; i < 4; i ++) {
				tmp.add(tab[i]);
			}
		}		
		panel.add(tmp);
		tab = new JTextField[3];
		tab[0] = new JTextField("Nom de l'entreprise", 20);
		tab[1] = new JTextField("Poste occupe", 20);
		tab[2] = new JTextField("Date", 20);
		tmp = new JPanel(new GridLayout(1, 4)); {
			tmp.add(new JLabel("Experience : "));
			for (int i = 0; i < 3; i ++) {
				tmp.add(tab[i]);
			}
		}
		panel.add(tmp);
		tab = new JTextField[2];
		tab[0] = new JTextField("Nom", 20);
		tab[1] = new JTextField("Niveau", 20);
		tmp = new JPanel(new GridLayout(1, 3)); {
			tmp.add(new JLabel("Competence : "));
			for (int i = 0; i < 2; i ++) {
				tmp.add(tab[i]);
			}
		}
		panel.add(tmp);
		tab = new JTextField[2];
		tab[0] = new JTextField("Nom", 20);
		tab[1] = new JTextField("Niveau", 20);
		tmp = new JPanel(new GridLayout(1, 3)); {
			tmp.add(new JLabel("Langue : "));
			for (int i = 0; i < 2; i ++) {
				tmp.add(tab[i]);
			}
		}
		panel.add(tmp);
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	
	}
	
	private void createView() {
		// TODO Auto-generated method stub
		Resume cv = new Resume();
		frame = new JFrame("CV --- Ajouter un CV ---");
		ajouter_champs = new JButton("Ajouter les champs");
		map = new TreeMap<String, JTextField[]>();
		jcb = new JComboBox<String>();
		add = new JButton("Ajouter le CV a la base");
		jcb.addItem("Competence");
		jcb.addItem("Experience");		
		jcb.addItem("Langue");
		jcb.addItem("Scolarite");
		panel = new JPanel(new GridLayout(0,1));
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Form_View();
            }
        });
    }
}

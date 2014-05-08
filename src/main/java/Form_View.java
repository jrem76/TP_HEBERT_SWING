import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import resume.*;


public class Form_View {
	private JFrame frame;
	private Resume cv;
	private JButton add, ajouter_champs;
	private JComboBox<String> jcb;
	private JTextField lastName, firstName;
	private Map<String, JTextField[]> map;
	private JPanel panel;
	private int nb_ecole, nb_experience, nb_langue, nb_competence;



    private boolean add_possible = false;
	
	public Form_View() {
        cv = new Resume();
		createView();
		placeComponents();
		createController();
		nb_ecole = 0;
		nb_experience = 0;
		nb_langue = 0;
		nb_competence = 0;
	}

	private void createController() {
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
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(map.keySet().size());
                Langues langues = new Langues();
                Competences_Info ci = new Competences_Info();
                Scolarite sc = new Scolarite();
                Experiences exp = new Experiences();
                for (String s : map.keySet()) {
                    System.out.println(s);
                    JTextField[] tab = map.get(s);
                    if (s.contains("Langue")) {
                        String nom;
                        String niveau;
                        if (!tab[0].getText().equals("Nom") && !tab[0].getText().isEmpty())
                            nom = tab[0].getText();
                        else
                            nom = "";
                        if (!tab[1].getText().equals("Niveau") && !tab[1].getText().isEmpty())
                            niveau = tab[1].getText();
                        else
                            niveau = "";
                        langues.add(new Langue(nom, niveau));
                    } else if (s.contains("Experience")) {
                        String nom;
                        String poste;
                        String date;
                        if (!tab[0].getText().equals("Nom de l'entreprise") && !tab[0].getText().isEmpty())
                            nom = tab[0].getText();
                        else
                            nom = "";
                        if (!tab[1].getText().equals("Poste occupe") && !tab[1].getText().isEmpty())
                            poste = tab[1].getText();
                        else
                            poste = "";
                        if (!tab[2].getText().equals("Date") && !tab[2].getText().isEmpty())
                            date = tab[2].getText();
                        else
                            date = "";
                        exp.add(new Entreprise(nom, date, poste));
                    } else if (s.contains("Scolarite")) {
                        String diplome;
                        String lieu;
                        String ville;
                        String date;
                        if (!tab[0].getText().equals("Diplome") && !tab[0].getText().isEmpty())
                            diplome = tab[0].getText();
                        else
                            diplome = "";
                        if (!tab[1].getText().equals("Lieu de preparation") && !tab[1].getText().isEmpty())
                            lieu = tab[1].getText();
                        else
                            lieu = "";
                        if (!tab[2].getText().equals("Ville") && !tab[2].getText().isEmpty())
                            ville = tab[2].getText();
                        else
                            ville = "";
                        if (!tab[3].getText().equals("Date") && !tab[3].getText().isEmpty())
                            date = tab[3].getText();
                        else
                            date = "";
                        sc.add(new Ecole(diplome, lieu, ville, date));
                    } else if (s.contains("Competence")) {
                        String nom;
                        String niveau;
                        if (!tab[0].getText().equals("Nom") && !tab[0].getText().isEmpty())
                            nom = tab[0].getText();
                        else
                            nom = "";
                        if (!tab[1].getText().equals("Niveau") && !tab[1].getText().isEmpty())
                            niveau = tab[1].getText();
                        else
                            niveau = "";
                        ci.add(new Competence(nom, niveau));
                    }
                }
                System.out.println(lastName.getText());
                if (!lastName.getText().isEmpty() && !lastName.getText().equals("Nom"))
                    cv.setLastName(lastName.getText());
                else
                    cv.setLastName("");
                if (!firstName.getText().isEmpty() && !firstName.getText().equals("Prenom"))
                    cv.setFirstName(firstName.getText());
                else
                    cv.setFirstName("");
                cv.setCompetences_info(ci);
                cv.setScolarite(sc);
                cv.setExperiences(exp);
                cv.setLangues(langues);
                new CV_View(cv, frame);
                //TO-DO
                // send();
                add_possible = true;
            }
        });
	}

	private void placeComponents() {
		JPanel p = new JPanel(); {
			p.add(jcb);
			p.add(ajouter_champs);
			p.add(add);
		}
		frame.add(p, BorderLayout.NORTH);
        JPanel tmp1 = new JPanel(); {
            tmp1.add(firstName);
            tmp1.add(lastName);
        }
        panel.add(tmp1);
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
        map.put("Scolarite"+nb_ecole++, tab);
		panel.add(tmp);
		tab = new JTextField[3];
		tab[0] = new JTextField("Nom de l'entreprise", 20);
		tab[1] = new JTextField("Poste occupe", 20);
		tab[2] = new JTextField("Date", 20);
        map.put("Experience"+nb_experience++, tab);
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
        map.put("Competence"+nb_competence++, tab);
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
        map.put("Langue"+nb_langue++, tab);
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
		Resume cv = new Resume();
		frame = new JFrame("CV --- Ajouter un CV ---");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(frame);
            //force chaque composant de la fenêtre à appeler sa méthode updateUI
        } catch (InstantiationException e) {
        } catch (ClassNotFoundException e) {
        } catch (UnsupportedLookAndFeelException e) {
        } catch (IllegalAccessException e) {}
		ajouter_champs = new JButton("Ajouter les champs");
		map = new TreeMap<String, JTextField[]>();
		jcb = new JComboBox<String>();
		add = new JButton("Ajouter le CV a la base");
		jcb.addItem("Competence");
		jcb.addItem("Experience");		
		jcb.addItem("Langue");
		jcb.addItem("Scolarite");
		panel = new JPanel(new GridLayout(0,1));
        lastName = new JTextField("Nom", 20);
        firstName = new JTextField("Prenom", 20);
	}

    public Resume getCv() {
        return cv;
    }
    public boolean isAdd_possible() {
        return add_possible;
    }
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Form_View();
            }
        });
    }
}

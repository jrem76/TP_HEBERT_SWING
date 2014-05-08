import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.Service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import resume.Competence;
import resume.Competences_Info;
import resume.Ecole;
import resume.Entreprise;
import resume.Experiences;
import resume.Langue;
import resume.Langues;
import resume.Resume;
import resume.ResumeManager;
import resume.Scolarite;

public class View {
	private JFrame frame;
	private JButton b_form;
    private JButton refresh;
	private Map<JButton, Integer > list_cv;
	
	private Service service;
	private JAXBContext jc;
	private ResumeManager rm = new ResumeManager();
	private static final QName qname = new QName("", "");
	private static final String URL = "http://tphebertxml.jrem76.cloudbees.net/rest/resume/";
	
	public View() {
		try {
			jc = JAXBContext.newInstance(Ecole.class, Langue.class, Competence.class, Resume.class, ResumeManager.class);
		} catch (JAXBException je) {
			System.out.println("Cannot create JAXBContext " + je);
		}
		createView();
		try {
			createModel();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
            e.printStackTrace();
		}
		placeComponents();
		createController();
	}
	
	private void createView() {
		frame = new JFrame("TP WEB Hebert - CV");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (InstantiationException e) {
        } catch (ClassNotFoundException e) {
        } catch (UnsupportedLookAndFeelException e) {
        } catch (IllegalAccessException e) {}
		b_form = new JButton("Ajouter un CV");
        refresh = new JButton("Rafraichir");
	}
	
	private void createModel() throws JAXBException, SAXException, IOException, ParserConfigurationException {
		list_cv = new HashMap<JButton, Integer>();
        lister_les_cv();
	}

    private void lister_les_cv() throws JAXBException, SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(URL);
        NodeList racine = document.getDocumentElement().getElementsByTagName("listResume");

        for (int i=0; i < racine.getLength(); i++) {
            Element e = (Element) racine.item(i);
            NodeList list = e.getChildNodes();
            Resume cv = new Resume();
            String lastName = list.item(2).getTextContent();
            String firstName = list.item(1).getTextContent();
            String id = list.item(0).getTextContent();
            System.out.println(id);
            list_cv.put(new JButton("Voir le CV de " + firstName + " " + lastName), Integer.parseInt(id));
        }
    }
	private void placeComponents() {
		JPanel p = new JPanel(); {
			p.add(b_form);
            p.add(refresh);
		}		
		frame.add(p, BorderLayout.NORTH);
        /*
        for (int i = 2; i < 100; i++)
            list_cv.put(new JButton("Blabla"), i);*/

		p = new JPanel(new GridLayout(0, 1)); {
			for (JButton j : list_cv.keySet()) {
				p.add(j);
			}
		}
        JScrollPane jsp = new JScrollPane(p);
		frame.add(jsp);
	}
	
	private void createController() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		b_form.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Form_View fv = new Form_View();
                try {
                    lister_les_cv();
                } catch (JAXBException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
		});
		
		for (JButton j : list_cv.keySet()) {
			j.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton jb = (JButton) e.getSource();
					try {
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				        factory.setValidating(false);
				        factory.setNamespaceAware(true);
				        DocumentBuilder builder = factory.newDocumentBuilder();
				        Document document = builder.parse(URL + list_cv.get(jb));
				        NodeList racine = document.getDocumentElement().getElementsByTagName("listResume");
						Resume cv = analyze(racine);
						new CV_View(cv, frame);
					} catch (ParserConfigurationException e1) {
						e1.printStackTrace();
					} catch (SAXException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}				
				}
			});
		}

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    lister_les_cv();
                } catch (JAXBException e1) {
                    e1.printStackTrace();
                } catch (SAXException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ParserConfigurationException e1) {
                    e1.printStackTrace();
                }
                frame.revalidate();
            }
        });
	}
	private Resume analyze(NodeList racine) {
     Element e = (Element) racine.item(0);
     NodeList list = e.getChildNodes();
     Resume cv = new Resume();
     for (int j = 0; j < list.getLength(); j++) {
     	 String lastName = list.item(1).getTextContent();
          String firstName = list.item(2).getTextContent();                     
     	
     	cv.setLastName(lastName);
     	cv.setFirstName(firstName);
     	if (list.item(j).getNodeName().equals("competences_info")) {
     		Element e1 = (Element) list.item(j);
     		NodeList list2 = e1.getChildNodes();
     		Competences_Info ci = new Competences_Info();
     		
     		for (int m = 0; m < list2.getLength(); m++) {                			
     			Element e2 = (Element) list2.item(m);
     			NodeList list3 = e2.getChildNodes();   
     			String niveau = "";
 				String nom = "";
     			for (int n = 0; n < list3.getLength(); n++) { 
     				if (list3.item(n).getNodeName().equals("niveau")) niveau = list3.item(n).getTextContent();
     				else if (list3.item(n).getNodeName().equals("nom")) nom =  list3.item(n).getTextContent();
         		}
     			Competence c = new Competence(nom, niveau);
     			ci.add(c);
 			}   
     		cv.setCompetences_info(ci);
     	} else if (list.item(j).getNodeName().equals("langues")) {
     		
     		Element e1 = (Element) list.item(j);
     		NodeList list2 = e1.getChildNodes();
     		Langues langues = new Langues();
     		
     		for (int m = 0; m < list2.getLength(); m++) {                			
     			Element e2 = (Element) list2.item(m);
     			NodeList list3 = e2.getChildNodes();    
     			String niveau = "";
 				String nom = "";
     			for (int n = 0; n < list3.getLength(); n++) { 
     				if (list3.item(n).getNodeName().equals("niveau")) niveau = list3.item(n).getTextContent();
     				else if (list3.item(n).getNodeName().equals("nom")) nom =  list3.item(n).getTextContent();
     		    }
     			Langue l = new Langue(nom, niveau);
     			langues.add(l);
 			}
     		cv.setLangues(langues);
     	} else if (list.item(j).getNodeName().equals("experiences")) {
     		
     		Element e1 = (Element) list.item(j);
     		NodeList list2 = e1.getChildNodes();
     		Experiences exp = new Experiences();
     		
     		for (int m = 0; m < list2.getLength(); m++) {                			
     			Element e2 = (Element) list2.item(m);
     			NodeList list3 = e2.getChildNodes();     
     			String date = "";
 				String nom = "";
 				String poste = "";
     			for (int n = 0; n < list3.getLength(); n++) {              				
     				
     				if (list3.item(n).getNodeName().equals("date")) date = list3.item(n).getTextContent();
     				else if (list3.item(n).getNodeName().equals("nom")) nom =  list3.item(n).getTextContent();
     				else if (list3.item(n).getNodeName().equals("poste")) poste =  list3.item(n).getTextContent();
     				
     				
         		}
     			Entreprise entreprise = new Entreprise(nom, date, poste);
     			exp.add(entreprise);
 			}
     		cv.setExperiences(exp);
     	} else if (list.item(j).getNodeName().equals("scolarite")) {
     		Element e1 = (Element) list.item(j);
     		NodeList list2 = e1.getChildNodes();
     		Scolarite sc = new Scolarite();
     		
     		for (int m = 0; m < list2.getLength(); m++) {                			
     			Element e2 = (Element) list2.item(m);
     			NodeList list3 = e2.getChildNodes();
     			String date = "";
 				String nom = "";
 				String diplome = "";
 				String ville = "";            				
     			for (int n = 0; n < list3.getLength(); n++) {   
     				if (list3.item(n).getNodeName().equals("date")) date = list3.item(n).getTextContent();
     				else if (list3.item(n).getNodeName().equals("nom")) nom =  list3.item(n).getTextContent();
     				else if (list3.item(n).getNodeName().equals("diplome")) diplome =  list3.item(n).getTextContent();
     				else if (list3.item(n).getNodeName().equals("ville")) ville =  list3.item(n).getTextContent();
     			}
     			Ecole ecole = new Ecole(nom, diplome, date, ville);
     			sc.add(ecole);
 			}
     		cv.setScolarite(sc);
     	}
      
     }		 
		return cv;
	}
	public void display() {
		frame.setSize(400,500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new View().display();
            }
        });
    }
}
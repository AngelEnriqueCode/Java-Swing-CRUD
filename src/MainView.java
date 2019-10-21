import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import entity.City;
import model.CityModel;


public class MainView {


    public static void main(String[] args) {
        new MainView();
    }

    public MainView()  {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }
                
                JFrame frame = new JFrame("Cities");
                frame.setBounds(100, 100, 500, 300);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(new TestPane());
                //frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
	
	
	public class TestPane extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JPanel mainList;

        public TestPane() {
            setLayout(new BorderLayout());
            mainList = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.weighty = 1;
            mainList.add(new JPanel(), gbc);
            add(new JScrollPane(mainList));
            
            GetCities(mainList);
            
            JButton add = new JButton("Add");
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	newCity(mainList);	
                }
            });
            add(add, BorderLayout.SOUTH);
        }
    }
	
	
	public void GetCities (JPanel mainList) {
		
    	CityModel cityModel = new CityModel();
		List<City> cities = cityModel.findAll();
		//For each city it will create a new panel
		for(City city : cities) {
			createNewPanel(city, mainList, cityModel );
		}
		
    }
	
	/**
	 * It requires a new City object, the parent frame where the new panel will be created and the controller class.
	 * It will create a new panel.
	 * @param city
	 * @param mainList
	 * @param cityModel
	 */
	public void createNewPanel(City city, JPanel mainList, CityModel cityModel ) {
    	//object instancing
		JPanel panel = new JPanel();
		JButton deleteButton = new JButton("Delete");
		deleteButton.setToolTipText(city.getId());
		JButton infoButton = new JButton("More Info");
		infoButton.setToolTipText(city.getId());
		JButton favButton = new JButton("Favourite");
		favButton.setToolTipText(city.getId());
        
		//Adding to the principal panel
		panel.add(new JLabel("City:"));
		panel.add(new JLabel(city.getName()));
        panel.add(deleteButton);
        panel.add(infoButton);
        panel.add(favButton);
        
        //if is a favourite it will create a check box object
        var fav = city.getFavourite();
        if (fav != null ) {
        	panel.add(new JLabel("Favourite: "));
        	JCheckBox favChecBox = new JCheckBox();
        	panel.add(favChecBox);
        	favChecBox.setSelected(true);
        	favChecBox.setEnabled(false);
        	favButton.setEnabled(false);
        }
        
        //Adding the panel to the parent container mainList
        panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(panel, gbc1, 0);
        
        //ActionListener declared for the button of delete
		deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//Calling to the controller to remove the record
            	cityModel.Delete(deleteButton.getToolTipText());
            	
            	mainList.remove(deleteButton.getParent());
            	mainList.repaint();
 	            mainList.validate();
            	
            } 
        });
		
		//ActionListener declared for the button of more information
		infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//Calling to the controller to get the information of one particular record (city)
            	City city = cityModel.findOne(infoButton.getToolTipText());
            	
            	JFrame infoFrame = new JFrame("Info about: " + city.getName());
            	infoFrame.setBounds(100, 100, 400, 300);
            	infoFrame.setVisible(true);
            	JPanel panel = new JPanel();
            	panel.add(new JTextArea(city.getText()));
            	infoFrame.add(panel);
            	
            } 
        });
		
		//ActionListener declared for the button of more favourite
		favButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	//Calling to the controller to update the DDBB
            	cityModel.Favourite(favButton.getToolTipText());
            
            	JCheckBox favChecBox = new JCheckBox();
            	panel.add(favChecBox);
            	favChecBox.setSelected(true);
            	favChecBox.setEnabled(false);
            	favButton.setEnabled(false);
            	mainList.repaint();
 	            mainList.validate();
            } 
        });
    }
	
	/**
	 * Method to open a new JFrame to show the add form
	 * @param mainList (where the panel will be added)
	 */
	public void newCity( JPanel mainList) {
		
		//New Jframe is created
		JFrame newFrame = new JFrame("New City");
    	newFrame.pack();
    	newFrame.setVisible(true);
    	newFrame.setBounds(100, 100, 300, 150);
    	
    	//New test pane is added inside the Jframe
    	newFrame.add(new TestPane());
    	
    	// ID Label
    	JLabel id_label = new JLabel();
    	id_label.setText("City ID :");
        JTextField id_field = new JTextField();
        
    	// City Name Label
    	JLabel name_label = new JLabel();
        name_label.setText("City Name :");
        JTextField name_field = new JTextField();
        
        // Description Label
    	JLabel text_label = new JLabel();
    	text_label.setText("City Description :");
        JTextField text_field = new JTextField();

        // Submit button
        JButton submit = new JButton("SUBMIT");
        submit.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		//New city object is instantiated
        		City city = new City();
        		CityModel cityModel = new CityModel();
	            //Filling the class fields
        		city.setId(id_field.getText());
	            city.setName(name_field.getText());
	            city.setText(text_field.getText());
	            cityModel.Add(city);
	            newFrame.setVisible(false);
	            
	            //Creating a new panel in the mainList
	            createNewPanel(city, mainList, cityModel );
	            
	            mainList.repaint();
	            mainList.validate();
                }
            });

        //Adding the fields in the panel of the new Form. (is not the first one)
        JPanel Datapanel = new JPanel(new GridLayout(4, 1));

        Datapanel.add(id_label);
        Datapanel.add(id_field );
        Datapanel.add(name_label);
        Datapanel.add(name_field );
        Datapanel.add(text_label);
        Datapanel.add(text_field );
        JLabel message = new JLabel();
        Datapanel.add(message);
        Datapanel.add(submit);
        
        //Adding all components in the new Jframe (new window)
        newFrame.add(Datapanel);
       
    }
	
	

}


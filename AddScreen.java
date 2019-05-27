package p1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import org.hibernate.*;
import org.hibernate.cfg.*;

class AddScreen extends JFrame
{
	Container c;
	JLabel nameLabel;
	JLabel nameemptyLabel;
	JLabel rollLabel;
	JLabel rollemptyLabel;
	JLabel ageLabel;
	JLabel ageemptyLabel;
	JLabel genderLabel;
	JLabel genderemptyLabel;
	JTextField nameField;
	JTextField rollField;
	JTextField ageField;
	JRadioButton maleRadio;
	JRadioButton femaleRadio;
	JButton subBtn;
	JButton backBtn;
	Border empty,white;
	
	
	
	
	AddScreen(){
		
		c = getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// components instantiations
		nameemptyLabel = new JLabel("                                                                                                                                          ");
		nameemptyLabel.setFont(new Font("Arial", Font.BOLD, 7));
		rollemptyLabel = new JLabel("                                                                                                                                          ");
		rollemptyLabel.setFont(new Font("Arial", Font.BOLD, 7));
		ageemptyLabel = new JLabel("                                                                                                                                          ");
		ageemptyLabel.setFont(new Font("Arial", Font.BOLD, 7));
		genderemptyLabel = new JLabel("                                                                                                                                                                               ");
		genderemptyLabel.setFont(new Font("Arial", Font.BOLD, 7));
		
		nameLabel = new JLabel("Enter Name : ");
		nameLabel.setFont(new Font("Arial", Font.BOLD, 19));
		rollLabel = new JLabel("Enter Roll no : ");
		rollLabel.setFont(new Font("Arial", Font.BOLD, 19));
		ageLabel = new JLabel("Enter Age : ");
		ageLabel.setFont(new Font("Arial", Font.BOLD, 19));
		genderLabel = new JLabel("Enter Gender : ");
		genderLabel.setFont(new Font("Arial", Font.BOLD, 19));
		nameField = new JTextField();
		nameField.setPreferredSize( new Dimension( 250, 24 ));
		rollField = new JTextField();
		rollField.setPreferredSize( new Dimension( 200, 24 ));
		ageField = new JTextField();
		ageField.setPreferredSize( new Dimension( 200, 24 ));
		
		maleRadio = new JRadioButton("male");
		maleRadio.setOpaque(false);
		femaleRadio = new JRadioButton("Female");
		femaleRadio.setOpaque(false);
		ButtonGroup group = new ButtonGroup();
		group.add(maleRadio);
        group.add(femaleRadio);
		
		
		subBtn = new JButton("Submit");
		subBtn.setBackground(new Color(0, 208, 96));
		subBtn.setForeground(Color.WHITE);
		subBtn.setFont(new Font("Arial", Font.BOLD, 12));
		subBtn.setBorder(white);
		subBtn.setFocusPainted(false);
		subBtn.setPreferredSize(new Dimension(70, 35));
		
		backBtn = new JButton("Back");
		backBtn.setBackground(new Color(255, 0, 0));
		backBtn.setForeground(Color.WHITE);
		backBtn.setFont(new Font("Arial", Font.BOLD, 12));
		backBtn.setBorder(white);
		backBtn.setFocusPainted(false);
		backBtn.setPreferredSize(new Dimension(70, 35));
		
		c.add(nameLabel);
		c.add(nameField);
		c.add(nameemptyLabel);
		c.add(rollLabel);
		c.add(rollField);
		c.add(rollemptyLabel);
		c.add(ageLabel);
		c.add(ageField);
		c.add(ageemptyLabel);
		c.add(genderLabel);
		c.add(maleRadio);
		c.add(femaleRadio);
		c.add(genderemptyLabel);
		c.add(subBtn);
		c.add(backBtn);
		
		//action listener
		ActionListener a = (ae) -> {
			MainScreen ms = new MainScreen();
			this.dispose();
		};
		
		backBtn.addActionListener(a);
		
		ActionListener a1 = (ae) -> {
			
			String gender = "";
			int age = Integer.parseInt(ageField.getText());
			if(maleRadio.isSelected()){
				gender = maleRadio.getText();
			}else if(femaleRadio.isSelected()){
				gender = femaleRadio.getText();
			}
			
			
			if(nameField.getText().isEmpty() || rollField.getText().isEmpty() || ageField.getText().isEmpty() || gender.equals("")){
				JOptionPane.showMessageDialog(c,"Please Enter Value in Black Field");
			}else if(nameField.getText().matches("[0-9]+")){
				JOptionPane.showMessageDialog(c,"Name should not contain any numbers");
			}else if(nameField.getText().length() <= 2){
				JOptionPane.showMessageDialog(c,"Name should be greater than 2 letter");
			}else if(rollField.getText().matches("[0-9]+") == false){
				JOptionPane.showMessageDialog(c,"Please enter numbers in rollno field");
			}else if(ageField.getText().matches("[0-9]+") == false){
				JOptionPane.showMessageDialog(c,"Please enter numbers in age field");
			}else if(age > 45){
				JOptionPane.showMessageDialog(c,"Student age must be less than 46");
			}else{
					Configuration cfg = new Configuration();
					cfg.configure("hibernate.cfg.xml");
					SessionFactory sfact = cfg.buildSessionFactory();
					Session session = sfact.openSession();
					Transaction t = null;
					try{
						System.out.println("begin");
						t = session.beginTransaction();
						Student stud = new Student();
						stud.setName(nameField.getText());
						stud.setRno(Integer.parseInt(rollField.getText()));
						stud.setAge(Integer.parseInt(ageField.getText()));
						stud.setGender(gender);
						session.save(stud);
						t.commit();
						System.out.println("Record Inserted...");
						System.out.println("end");
						JOptionPane.showMessageDialog(c,"Data Saved Successfully...");
					}catch(Exception e){
						if (t!=null) t.rollback();
					}finally{
						session.flush();
						session.close();
					}
			}
		};
		
		subBtn.addActionListener(a1);
		
		
		// JFrame Basic Settings
		c.setBackground(Color.yellow);
		setTitle("S.M.S");
		setSize(440,260);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String args[]){
		AddScreen as = new AddScreen();
	}
}
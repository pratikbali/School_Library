import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


class AddMembers extends JInternalFrame implements ActionListener,ItemListener
{
	Statement stm;
	Connection cn=null;
	PreparedStatement ps;
	ResultSet rs;

	String sql;
	int flag=0,i;
	

	JLabel lbl[] = new JLabel[9];
    String lbl_string[] =
	{
        " Member Type "," MemberID ", " Name "," Roll "," Class ", " Division "," Department "," Contact ",
		" Email "
	};
	JButton btn_chk = new JButton("Check");

	JTextField tf[] = new JTextField[8];
	JButton btn_addMember = new JButton("Add Member");
	JButton btn_exit = new JButton("Exit");
	JButton btn_clr = new JButton("Clear");
	JComboBox cb_membType = new JComboBox();
	
    JPanel northPanel = new JPanel();  
    JLabel northLabel = new JLabel("MEMBER INFORMATION");   
    JPanel centerPanel = new JPanel();   
    JPanel lbl_Panel = new JPanel();
    JPanel input_Panel = new JPanel();
	JPanel cb_Panel = new JPanel();
    JPanel tf_Panel = new JPanel();
    JPanel ButtonPanel = new JPanel();
    JPanel southPanel = new JPanel();

	AddMembers()//constructor
	{
		super("Add Member", false, true, false, true);
		
		ImageIcon icon = new ImageIcon("images/Add16.gif");
		setFrameIcon(icon);
		Container cp = getContentPane();
		
		//  setting north panel for north label
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        northPanel.add(northLabel);
        cp.add("North", northPanel);
      
		// setting center panel
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Add a new book:"));

		// setting Label Panel
        lbl_Panel.setLayout(new GridLayout(9, 1, 1, 1));
		for(i=0;i<9;i++)
		{
			lbl[i] = new JLabel(lbl_string[i]);
			lbl[i].setFont(new Font("Comic sans ms", Font.BOLD, 13));
			lbl_Panel.add(lbl[i]);
		}
		centerPanel.add("West", lbl_Panel);

		// setting input Panel
		input_Panel.setLayout(new BorderLayout());
		// ComboBox Panel
		cb_Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
			cb_membType.addItem("Select");
			cb_membType.addItem("Teacher");
			cb_membType.addItem("Student");
		cb_Panel.add(cb_membType);
		input_Panel.add("North", cb_Panel);
		// TextField panel
		tf_Panel.setLayout(new GridLayout(8, 1, 1, 1));
		for(i=0;i<8;i++)
		{
			tf[i] = new JTextField(15);
			tf_Panel.add(tf[i]);
		}
		tf[0].requestFocus();

		input_Panel.add("Center", tf_Panel);
		centerPanel.add("Center", input_Panel);
				
		// setting insert Button
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btn_addMember.setFont(new Font("Comic sans ms", Font.BOLD, 13));
        btn_chk.setFont(new Font("Comic sans ms", Font.BOLD, 13));
        btn_clr.setFont(new Font("Comic sans ms", Font.BOLD, 13));
		ButtonPanel.add(btn_clr);
		ButtonPanel.add(btn_addMember);
		ButtonPanel.add(btn_chk);
		btn_addMember.setVisible(false);
		centerPanel.add("South", ButtonPanel);
		
		cp.add("Center", centerPanel);
		
		//centerPanel.setVisible(false);
		
		// setting south panel
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btn_exit.setFont(new Font("Comic sans ms", Font.BOLD, 13));
        southPanel.add(btn_exit);
        southPanel.setBorder(BorderFactory.createEtchedBorder());
        cp.add("South", southPanel);
		
		//adding listener
		btn_addMember.addActionListener(this);
		btn_chk.addActionListener(this);
		btn_exit.addActionListener(this);
		btn_clr.addActionListener(this);
		cb_membType.addItemListener(this);

		
		//setting ToolTipText
		btn_addMember.setToolTipText("Insert Member In Library");
		btn_chk.setToolTipText("Check Inserted Data is Correct or Not");
		btn_exit.setToolTipText("Exit From This Frame");
		btn_clr.setToolTipText("Clears each Text Field");
		
		
		btn_addMember.setMnemonic('a');
		btn_chk.setMnemonic('c');
		btn_exit.setMnemonic('x');
		btn_clr.setMnemonic('l');
		
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql:///library","root","");
			stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stm.executeQuery("select * from  Members");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		Hide();
		valid(tf[0]);
		valid(tf[2]);
		valid(tf[6]);
		
		setVisible(true);
		pack();
	}
	
	public void Hide()
	{
		tf[0].setVisible(false);//hide memberID
		tf[1].setVisible(false);//hide Member Name
		tf[2].setVisible(false);//hide roll
		tf[3].setVisible(false);//hide class
		tf[4].setVisible(false);//hide division
		tf[5].setVisible(false);//show department
		tf[6].setVisible(false);//hide contact
		tf[7].setVisible(false);//hide Email	
		
		lbl[1].setVisible(false);//hide Member Name
		lbl[2].setVisible(false);//hide roll
		lbl[3].setVisible(false);//hide class
		lbl[4].setVisible(false);//hide division
		lbl[5].setVisible(false);//show department
		lbl[6].setVisible(false);//hide contact
		lbl[7].setVisible(false);//hide Email	
		lbl[8].setVisible(false);//hide Email	

	}
	public void Show()
	{
		tf[0].setVisible(true);//show memberID
		tf[1].setVisible(true);//show Member Name
		tf[6].setVisible(true);//show contact
		tf[7].setVisible(true);//show Email	

		lbl[1].setVisible(true);//show Member Name
		lbl[2].setVisible(true);//show Member Name
		lbl[7].setVisible(true);//show contact
		lbl[8].setVisible(true);//show Email	

	}
	
	public void Teacher()
	{
		tf[2].setVisible(false);//hide roll
		tf[3].setVisible(false);//hide class
		tf[4].setVisible(false);//hide division
		tf[5].setVisible(true);//show department
		tf[0].requestFocus();

		lbl[3].setVisible(false);//hide roll
		lbl[4].setVisible(false);//hide class
		lbl[5].setVisible(false);//hide division
		lbl[6].setVisible(true);//show department

	}
	
	public void Student()
	{
		tf[2].setVisible(true);//show roll
		tf[3].setVisible(true);//show class
		tf[4].setVisible(true);//show division
		tf[5].setVisible(false);//hide department
		tf[0].requestFocus();

		lbl[3].setVisible(true);//show roll
		lbl[4].setVisible(true);//show class
		lbl[5].setVisible(true);//show division
		lbl[6].setVisible(false);//hide department

	}

	public void itemStateChanged(ItemEvent ie)
	{
		if(ie.getSource()==cb_membType)
		{
			if(cb_membType.getSelectedIndex()==0)// if teacher selected
			{
				Hide();
			}
			if(cb_membType.getSelectedIndex()==1)// if teacher selected
			{
				Teacher();
				Show();
			}
			if(cb_membType.getSelectedIndex()==2)// if student selected
			{
				Student();
				Show();
			}
			
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			if(e.getSource()==btn_chk)
			{
				for(i=0;i<8;i++)
				{
					if(tf[i].getText().length()==0 || cb_membType.getSelectedIndex()==0)
					{					
						JOptionPane.showMessageDialog(null,"All Fields Are Necessary");
						tf[0].requestFocus();
						break;
					}
				
					else
					{ 
						btn_addMember.setVisible(true);
						btn_chk.setVisible(false);
					}
				}
			}
			if(e.getSource()==btn_addMember)
			{
				String type = cb_membType.getSelectedItem().toString();//selec member type 
				String memberID = tf[0].getText();
				String name = tf[1].getText();
				String roll = tf[2].getText().toString();
				String cls = tf[3].getText();
				String div = tf[4].getText();
				String dept = tf[5].getText();
				String contact = tf[6].getText().toString();
				String email = tf[7].getText();

				rs=stm.executeQuery("select * from Members");	
				while(rs.next())
				{
					if(rs.getString(2).equals(memberID))
					{flag=1;
						JOptionPane.showMessageDialog(null,"Member already exist");
						tf[0].requestFocus();
					}
				}
					
				if(flag==0)
				{	
					sql = "insert into Members values('"+type+"','"+memberID+"','"+name+"','"+roll+"','"+cls+"','"+div+"','"+dept+"','"+contact+"','"+email+"')";
							
					ps = cn.prepareStatement(sql);
					ps.execute();
					ps.close();
					JOptionPane.showMessageDialog(null,"Member Added successfully");
					clrscr();
					//break;
				}
			}
			
			
			if(e.getSource()==btn_clr)
			{
				clrscr();
			}
			if(e.getSource()==btn_exit)
			{
				dispose();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	void valid(final JTextField tt)
	{
		tt.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				if(tt.getText().length()<10 && e.getKeyChar()>='0' && e.getKeyChar()<='9')

				super.keyTyped(e);    // optional

				else
				{
					e.consume();		// discard the event
					Toolkit tk=Toolkit.getDefaultToolkit();
					tk.beep();	// raise the sound
				}
            }
		});
	}
	
	public void clrscr()
	{
		for(i=0;i<8;i++)
				{
					tf[i].setText("");
					tf[0].requestFocus();
				}	
	}
	
	
	public static void main(String a[])
	{
		new AddMembers();
	}
}

/**
create database library;
use library;
create table Members
(
	Type varchar(50),
	MemberID varchar(50) primary key,
	Name varchar(50),
	Roll varchar(50),
	Class varchar(50),
	Division varchar(50),
	Dept varchar(50),
	Phone varchar(50),
	Email varchar(50)
);
insert into Members values ('teacher','t01','prince','','','','Comp','9011989737','cnpratik@gmail.com');
insert into Members values ('student','t01','prince','3','ty','a','','9011989737','cnpratik@gmail.com');
select * from Members;
**/


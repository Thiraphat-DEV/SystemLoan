import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.sql.*;
/**
 *
 * Description
 *
 * @version 1.0 from 2/13/2022
 * @author 
 */

public class persons extends JFrame {
  // start attributes
  private JPanel jPanel1 = new JPanel(null, true);
    private JLabel lUserID = new JLabel();
    private JLabel lUsername = new JLabel();
    private JTextField jid = new JTextField();
    private JTextField jusername = new JTextField();
    private JLabel lPrice = new JLabel();
    private JNumberField jprice = new JNumberField();
    private JButton btn_cal = new JButton();
    private JNumberField jpriceTax = new JNumberField();
    private JNumberField jsumprice = new JNumberField();
    private JLabel lPriceTax = new JLabel();
    private JLabel lSumPrice = new JLabel();
    private JSeparator jSeparator1 = new JSeparator();
    private JButton bSave = new JButton();
    private JButton bUpdate = new JButton();
    private JButton bSearch = new JButton();
    private JButton bDelete = new JButton();
    private JComboBox<String> joptions = new JComboBox<String>();
      private DefaultComboBoxModel<String> joptionsModel = new DefaultComboBoxModel<String>();
    private JButton bClose = new JButton();
  private JLabel jLabel1 = new JLabel();
  private JTable jTable1 = new JTable(0, 5);
    private DefaultTableModel jTable1Model = (DefaultTableModel) jTable1.getModel();
    private JScrollPane jTable1ScrollPane = new JScrollPane(jTable1);
  // end attributes
     private Connection con = null;  // connect to database
     private Statement state = null; // state from input 
     private ResultSet rs = null; // get state
     private PreparedStatement ps;  
     private String driver =  "com.mysql.cj.jdbc.Driver";//init driver
     private String dbURL = "jdbc:mysql://localhost/personsload"; 
  
  public persons() { 
    // Frame-Init
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 1034; 
    int frameHeight = 664;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("persons");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    // start components
     try {
      Class.forName(driver);
    } catch(Exception e) {
      System.out.println("Driver is undefined" + e.getMessage());
    }  // end of try
    jPanel1.setBounds(0, -16, 1033, 849);
    jPanel1.setOpaque(false);
    cp.add(jPanel1);
    jLabel1.setBounds(232, 0, 634, 57);
    jLabel1.setText("Loan System  Starting At 1000 Baht");
    jLabel1.setFont(new Font("Angsana New", Font.BOLD + Font.ITALIC, 36));
    jLabel1.setForeground(Color.RED);
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    cp.add(jLabel1);
    lUserID.setBounds(16, 80, 137, 49);
    lUserID.setText("UserID");
    lUserID.setHorizontalAlignment(SwingConstants.CENTER);
    jPanel1.add(lUserID);
    lUsername.setBounds(16, 144, 153, 65);
    lUsername.setText("Username");
    lUsername.setHorizontalAlignment(SwingConstants.CENTER);
    jPanel1.add(lUsername);
    jid.setBounds(176, 80, 121, 49);
    jid.setHorizontalAlignment(SwingConstants.CENTER);
    jid.setBackground(Color.WHITE);
    jid.setForeground(Color.BLUE);
    jPanel1.add(jid);
    jusername.setBounds(176, 152, 169, 49);
    jusername.setHorizontalAlignment(SwingConstants.CENTER);
    jusername.setBackground(Color.WHITE);
    jusername.setForeground(Color.BLUE);
    jPanel1.add(jusername);
    lPrice.setBounds(16, 224, 129, 49);
    lPrice.setText("Price(BAHT)");
    lPrice.setHorizontalAlignment(SwingConstants.CENTER);
    jPanel1.add(lPrice);
    jprice.setBounds(176, 224, 129, 41);
    jprice.setText("");
    jprice.setBackground(Color.WHITE);
    jprice.setForeground(Color.BLUE);
    jprice.setHorizontalAlignment(SwingConstants.CENTER);
    jprice.setToolTipText("Enter Money > 1000 BAHT");
    jPanel1.add(jprice);
    btn_cal.setBounds(320, 352, 121, 41);
    btn_cal.setText("Calculate");
    btn_cal.setMargin(new Insets(2, 2, 2, 2));
    btn_cal.addMouseListener(new MouseInputAdapter() { 
      public void mouseClicked(MouseEvent evt) { 
        btn_cal_mouseClicked(evt);
      }
      
     
    });
    btn_cal.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btn_cal.setForeground(Color.MAGENTA);
    jPanel1.add(btn_cal);
    jpriceTax.setBounds(176, 288, 129, 41);
    jpriceTax.setText("");
    jpriceTax.setBackground(Color.WHITE);
    jpriceTax.setForeground(Color.BLUE);
    jpriceTax.setHorizontalAlignment(SwingConstants.CENTER);
    jpriceTax.setEditable(false);
    jpriceTax.setEnabled(false);
    jPanel1.add(jpriceTax);
    jsumprice.setBounds(176, 352, 129, 41);
    jsumprice.setText("");
    jsumprice.setBackground(Color.WHITE);
    jsumprice.setForeground(Color.BLUE);
    jsumprice.setHorizontalAlignment(SwingConstants.CENTER);
    jPanel1.add(jsumprice);
    lPriceTax.setBounds(24, 288, 121, 49);
    lPriceTax.setText("PriceTax(%)");
    lPriceTax.setHorizontalAlignment(SwingConstants.CENTER);
    jPanel1.add(lPriceTax);
    lSumPrice.setBounds(24, 352, 129, 41);
    lSumPrice.setText("SumPrice(BAHT)");
    lSumPrice.setHorizontalAlignment(SwingConstants.CENTER);
    jPanel1.add(lSumPrice);
    jSeparator1.setBounds(8, 8, 1025, 57);
    jSeparator1.setBackground(Color.GREEN);
    jPanel1.add(jSeparator1);
    jTable1ScrollPane.setBounds(504, 80, 529, 313);
    jTable1.getColumnModel().getColumn(0).setHeaderValue("PersonId");
    jTable1.getColumnModel().getColumn(1).setHeaderValue("PersonName");
    jTable1.getColumnModel().getColumn(2).setHeaderValue("Price");
    jTable1.getColumnModel().getColumn(3).setHeaderValue("PriceTax");
    jTable1.getColumnModel().getColumn(4).setHeaderValue("SumPrice");
    try { 
     Class.forName(driver);    
      con = DriverManager.getConnection(dbURL, "root", "");// connect to localhost 
      state = con.createStatement();// create State with showState from server
      rs = state.executeQuery("SELECT * FROM person");
      while(rs.next()) {
      jTable1Model.addRow(new Object[]{
        rs.getString("personID"),
        rs.getString("personName"),
        rs.getString("Price"),
        rs.getString("PriceTax"),
        rs.getString("SumPrice"),
        });
      }     
    } catch(Exception e) {
       System.out.println("Error of ShowData" + e.toString());
    } // end of try
    jTable1.setEnabled(false);
    jPanel1.add(jTable1ScrollPane);
    bSave.setBounds(48, 424, 97, 49);
    bSave.setText("Save");
    bSave.setMargin(new Insets(2, 2, 2, 2));
    bSave.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bSave_ActionPerformed(evt);
      }
    });
    bSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
    bSave.setForeground(Color.MAGENTA);
    bSave.setBorder(BorderFactory.createBevelBorder(1, Color.MAGENTA, Color.CYAN));
    jPanel1.add(bSave);
    bUpdate.setBounds(184, 424, 97, 49);
    bUpdate.setText("Update");
    bUpdate.setMargin(new Insets(2, 2, 2, 2));
    bUpdate.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bUpdate_ActionPerformed(evt);
      }
    });
    bUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
    bUpdate.setForeground(Color.MAGENTA);
    bUpdate.setBorder(BorderFactory.createEtchedBorder(1, Color.GREEN, Color.RED));
    jPanel1.add(bUpdate);
    bSearch.setBounds(864, 424, 137, 49);
    bSearch.setText("Search");
    bSearch.setMargin(new Insets(2, 2, 2, 2));
    bSearch.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bSearch_ActionPerformed(evt);
      }
    });
    bSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
    bSearch.setBorder(BorderFactory.createEtchedBorder(1, Color.YELLOW, Color.CYAN));
    bSearch.setForeground(Color.MAGENTA);
    jPanel1.add(bSearch);
    bDelete.setBounds(320, 424, 113, 49);
    bDelete.setText("Delete");
    bDelete.setMargin(new Insets(2, 2, 2, 2));
    bDelete.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bDelete_ActionPerformed(evt);
      }  
    });
    bDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
    bDelete.setForeground(Color.MAGENTA);
    bDelete.setBorder(BorderFactory.createEtchedBorder(1, Color.GREEN, Color.RED));
    jPanel1.add(bDelete);
    joptions.setModel(joptionsModel);
    joptions.setBounds(656, 424, 177, 49);
    joptions.setCursor(new Cursor(Cursor.HAND_CURSOR));
    joptions.setForeground(Color.BLUE);
    joptions.setBackground(Color.WHITE);
    joptions.setSelectedIndex(-1);
    jPanel1.add(joptions);
    bClose.setBounds(472, 424, 129, 49);
    bClose.setText("Close");
    bClose.setMargin(new Insets(2, 2, 2, 2));
    bClose.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bClose_ActionPerformed(evt);
      }
    });
    bClose.setBorder(BorderFactory.createEtchedBorder(0, Color.MAGENTA, Color.GREEN));
    bClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
    bClose.setForeground(Color.MAGENTA);
    jPanel1.add(bClose);
    // end components
    personNameOption();
    setVisible(true);
    
 
  } // end of public persons

  // start methods
  
  public static void main(String[] args) {
    new persons();
  } // end of main
  
  
  public void personNameOption() {
    try {
      ps = con.prepareStatement("SELECT personName FROM person");
      rs = ps.executeQuery();
      joptions.removeAllItems();
      
      while(rs.next()) {
         joptions.addItem(rs.getString(1));
        }
    } catch(Exception e) {
        System.out.println(e.getMessage());
    } // end of try
  }
  
  //fix bug 
  public void btn_cal_mouseClicked(MouseEvent evt) {
    // TODO add your code here
     try {
      checkTax(jprice.getDouble()); //checkTax to money
      //calculate to money  * tax
      double taxOfmoney = (jprice.getDouble() * jpriceTax.getDouble())/100.00;
      double sumOfTax = jprice.getDouble() + taxOfmoney;
      //set field sumPrice
      jsumprice.setDouble(sumOfTax); 
              
    } catch(NumberFormatException e) {
      System.out.println("Error of calculate" +  e.getMessage());
    } // end of try
  } // end of btn_cal_ActionPerformed

  public void bSave_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
     try{
      state  = con.createStatement();
      ps = con.prepareStatement("INSERT INTO person VALUES(?, ?, ?, ?, ?)"); //insert data to mysql
      //values
      ps.setString(1, jid.getText());
      ps.setString(2, jusername.getText());
      ps.setString(3, jprice.getText());
      ps.setString(4, jpriceTax.getText());
      ps.setString(5, jsumprice.getText());
      ps.executeUpdate();// run sql
      JOptionPane.showMessageDialog(this, "Insert Successed");
      //close connection and statement
      ps.close();
      state.close();
      con.close();
      jid.setText("");
      jusername.setText("");
      jprice.setText("");
      jpriceTax.setText("");
      jsumprice.setText("");
      jid.requestFocus(); // focus of textfieldID
    }catch (Exception e) {
      System.out.println("Error Connect to mysql" + e.getMessage());
    }
  } // end of bSave_ActionPerformed
  //complete
  public void bUpdate_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
     //before check money to update
    try {
     ps = con.prepareStatement("UPDATE person SET personName = ? , price = ? , priceTax = ?, sumPrice = ? WHERE personID = ?");
     ps.setString(1, jusername.getText());
     ps.setString(2, jprice.getText());
     ps.setString(3, jpriceTax.getText());
     ps.setString(4, jsumprice.getText());
     ps.setString(5, jid.getText());
     int result = ps.executeUpdate();
     
     if(result == 1) {  // after result is updated setTextfield is emptyString 
        System.out.println("Update Record to Successed");
        JOptionPane.showMessageDialog(this, "Update Successed");
        jusername.setText("");
        jprice.setText("");
        jpriceTax.setText("");
        jsumprice.setText("");
        jid.setText("");
        jid.requestFocus();
       }
    } catch(Exception e) {
       System.out.println(e.getMessage());
    }
  } // end of bUpdate_ActionPerformed

  public void bSearch_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    String pName = joptions.getSelectedItem().toString();
    try {
      ps = con.prepareStatement("SELECT * FROM person WHERE personName = ?");
      ps.setString(1, pName);
      rs = ps.executeQuery();
      
      //check option  and setOption 
      if(rs.next()) {
        jid.setText(rs.getString(1));
        jusername.setText(rs.getString(2));
        jprice.setText(rs.getString(3));
        jpriceTax.setText(rs.getString(4));
        jsumprice.setText(rs.getString(5));
        }
    } catch(Exception e) {
      System.out.println("Error of Searching " +  e.getMessage());
    }  // end of try
  } // end of bSearch_ActionPerformed

  public void bDelete_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    try {
      ps = con.prepareStatement("DELETE FROM person WHERE personName = ?");
      ps.setString(1, jusername.getText());
      //get command
      String str = evt.getActionCommand(); 
      int rowOfdelete = ps.executeUpdate(); // get command is compared to String
      
      //check row is not null
      if(rowOfdelete > 0 && str.equals("Delete")) { //true is 2 option 
        System.out.println("Delete Data Successed");
        JOptionPane.showMessageDialog(this, "Delete Data Successed");
        joptions.removeItem(jusername.getText());
        jid.setText("");
        jusername.setText("");
        jprice.setText("");
        jpriceTax.setText("");
        jsumprice.setText("");
        jid.requestFocus();
        }
     } catch(Exception e) {
       System.out.println("Error of DeleteData" + e.getMessage());
     } //end of try    
  } // end of bDelete_ActionPerformed

  public void bClose_ActionPerformed(ActionEvent evt) {
  if (jid.getText() != null && jusername.getText() != null && jprice.getText() != null) {
      System.exit(0);
  }else {
      JOptionPane.showMessageDialog(this, "Please complete the information ");
   } // end of if-else // end of if
    // TODO add your code here
    
  } // end of bClose_ActionPerformed
  
  public int checkTax(double money)  {
        try {
        if(money < 1000) {
        JOptionPane.showMessageDialog(this, "Please EnterMoney greather 1000 ");
        jpriceTax.setText("");
        jsumprice.setText("");
        }else {
           if(money > 100000) {
            jpriceTax.setInt(20);
          }else if (money >  10000) {
            jpriceTax.setInt(10);
          }else {
             jpriceTax.setInt(7);
          } // end of if-else 
        } // end of if-else
         
        }  catch(NumberFormatException e) {
           System.out.println("Error of CheckTax" +  e.getMessage());
        } // end of try
           return (0);
        }
  // end methods
} // end of class persons

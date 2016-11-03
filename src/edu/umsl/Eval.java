/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umsl;

/**
 *
 * @author arpdz2
 */


import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import javax.swing.*;
import javax.swing.event.*;
//import org.apache.derby.jdbc.*;
import sun.jdbc.odbc.JdbcOdbcDriver;

/**
 *
 * @author brilaw
 */
public class Eval extends JFrame implements ActionListener, ItemListener, ChangeListener
{
    //DECLARE THE ELEMENTS OR OBJECTS THAT YOU WILL PUT IN YOUR FRAME
    //NOTICE HOW A PANEL IS CREATED FOR EACH ONE THIS WILL MAKE IT EASIER BUILD
   
    public JLabel teamLabel, teamLabel2, teamLabel3, teamLabel4, teamLabel5, teamLabel6, teamLabel7, teamLabel8;
    private JComboBox teamComboBox;
    private JPanel teamPanel, teamPanel2, teamPanel3, teamPanel4, teamPanel5, teamPanel6, teamPanel7;
    private JButton submitButton, calcAvg, clearButton;
    private JSlider teamSlider1, teamSlider2, teamSlider3, teamSlider4;
    private JTextField teamComments;
   
    // instance variables used to manipulate database
   private Connection myConnection;
   private Statement myStatement;
   private ResultSet myResultSet;
          
   public String teamName;
   public double q1;
   public double q2;
   public double q3;
   public double q4;
   public double Avg;
   public String comments;
  
   //MAIN METHOD: NOTICE WE TAKE IN THE ARGUMENTS THAT ARE
   //PASSED IN AND INSTANTIATE OUR CLASS WITH THEM
    public static void main(String args[])
    {
        // check command-line arguments
      //if ( args.length == 2 )
      //{
         // get command-line arguments
        String databaseDriver = "org.apache.derby.jdbc.ClientDriver";
        //String databaseDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
        String databaseURL = "jdbc:derby://localhost:1527/MyTestTwo;create=true";
       
        
         // create new Eval
         Eval myeval = new Eval( databaseDriver, databaseURL );
         myeval.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
      //}
      //else // invalid command-line arguments
      //{
      //   System.out.println( "Usage: java EVAL needs databaseDriver databaseURL" );
      //}
    }
   
   
    //CONSTRUCTOR: WE SET UP OUR DATABASE HERE THEN MAKE A CALL
    //TO A FUNCTION CALLED CREATEUSERINTERFACE TO BUILD OUR GUI
    public Eval(String databaseDriver, String databaseURL)
    {
        // establish connection to database
      try
      {
         // load Sun driver
         //Class.forName( databaseDriver );
         DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
         // connect to database
         myConnection = DriverManager.getConnection( databaseURL );

         // create Statement for executing SQL
         myStatement = myConnection.createStatement();
      }
      catch ( SQLException exception )
      {
         exception.printStackTrace();
      }
      //catch ( ClassNotFoundException exception )
     // {
      //   exception.printStackTrace();
      //}

      createUserInterface(); // set up GUI
     
     

     
    }
   

    private void createUserInterface()
    {
      // get content pane for attaching GUI components
      Container contentPane = getContentPane();

      // enable explicit positioning of GUI components
      contentPane.setLayout( null ); 
       
      // INSTRUCTOR COMBO BOX SET UP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      // set up teams
      teamPanel = new JPanel();
      teamPanel.setBounds(40, 20, 276, 48 );
      teamPanel.setBorder( BorderFactory.createEtchedBorder() );
      teamPanel.setLayout( null );
      contentPane.add( teamPanel );

      // set up Instructor Label
      teamLabel = new JLabel();
      teamLabel.setBounds( 25, 15, 100, 20 );
      teamLabel.setText( "Team:" );
      teamPanel.add( teamLabel );

      // set up accountNumberJComboBox
      teamComboBox = new JComboBox();
      teamComboBox.setBounds( 150, 15, 96, 25 );
      teamComboBox.addItem( "" );
      teamComboBox.setSelectedIndex( 0 );
      teamPanel.add( teamComboBox );
      teamComboBox.addItemListener(this);
      
      //set up submit button
      
      submitButton = new JButton( "SUBMIT" );
      submitButton.setBounds(132, 525, 80, 46);
      submitButton.setVisible(true);
      contentPane.add(submitButton);
      submitButton.setEnabled(false);
      submitButton.addActionListener(this);
     
      //set up q1 slider
      teamPanel2 = new JPanel();
      teamPanel2.setBounds(40, 80, 276, 48 );
      teamPanel2.setBorder( BorderFactory.createEtchedBorder() );
      teamPanel2.setLayout( null );
      contentPane.add( teamPanel2 );
      
      teamLabel2 = new JLabel();
      teamLabel2.setBounds( 05, 15, 100, 20 );
      teamLabel2.setText( "Q1 Technical:" );
      teamPanel2.add( teamLabel2 );

    teamSlider1 = new JSlider(SwingConstants.HORIZONTAL, 0, 8, 0);
    teamSlider1.setBounds(90, 5, 180, 40);
    teamSlider1.setMajorTickSpacing(1);
    teamSlider1.setPaintTicks(true);
    teamSlider1.setPaintLabels(true);
    teamSlider1.setVisible(true);
    teamPanel2.add(teamSlider1);
    teamSlider1.addChangeListener(this);
    
    //set up q2 slider
    teamPanel3 = new JPanel();
      teamPanel3.setBounds(40, 140, 276, 48 );
      teamPanel3.setBorder( BorderFactory.createEtchedBorder() );
      teamPanel3.setLayout( null );
      contentPane.add( teamPanel3 );
      
      teamLabel3 = new JLabel();
      teamLabel3.setBounds( 05, 15, 100, 20 );
      teamLabel3.setText( "Q2 Useful:" );
      teamPanel3.add( teamLabel3 );

    teamSlider2 = new JSlider(SwingConstants.HORIZONTAL, 0, 8, 0);
    teamSlider2.setBounds(90, 5, 180, 40);
    teamSlider2.setMajorTickSpacing(1);
    teamSlider2.setPaintTicks(true);
    teamSlider2.setPaintLabels(true);
    teamSlider2.setVisible(true);
    teamPanel3.add(teamSlider2);
    teamSlider2.addChangeListener(this);
    
     //set up q3 slider
      teamPanel4 = new JPanel();
      teamPanel4.setBounds(40, 200, 276, 48 );
      teamPanel4.setBorder( BorderFactory.createEtchedBorder() );
      teamPanel4.setLayout( null );
      contentPane.add( teamPanel4 );
      
      teamLabel4 = new JLabel();
      teamLabel4.setBounds( 05, 15, 100, 20 );
      teamLabel4.setText( "Q3 Clarity:" );
      teamPanel4.add( teamLabel4 );

    teamSlider3 = new JSlider(SwingConstants.HORIZONTAL, 0, 8, 0);
    teamSlider3.setBounds(90, 5, 180, 40);
    teamSlider3.setMajorTickSpacing(1);
    teamSlider3.setPaintTicks(true);
    teamSlider3.setPaintLabels(true);
    teamSlider3.setVisible(true);
    teamPanel4.add(teamSlider3);
    teamSlider3.addChangeListener(this);
      
    
    //set up q4 slider
      teamPanel5 = new JPanel();
      teamPanel5.setBounds(40, 260, 276, 48 );
      teamPanel5.setBorder( BorderFactory.createEtchedBorder() );
      teamPanel5.setLayout( null );
      contentPane.add( teamPanel5 );
      
      teamLabel5 = new JLabel();
      teamLabel5.setBounds( 05, 15, 100, 20 );
      teamLabel5.setText( "Q4 Overall:" );
      teamPanel5.add( teamLabel5 );

    teamSlider4 = new JSlider(SwingConstants.HORIZONTAL, 0, 8, 0);
    teamSlider4.setBounds(90, 5, 180, 40);
    teamSlider4.setMajorTickSpacing(1);
    teamSlider4.setPaintTicks(true);
    teamSlider4.setPaintLabels(true);
    teamSlider4.setVisible(true);
    teamPanel5.add(teamSlider4);
    teamSlider4.addChangeListener(this);
      
    
    //Add comments text box
      teamPanel6 = new JPanel();
      teamPanel6.setBounds(40, 320, 276, 100 );
      teamPanel6.setBorder( BorderFactory.createEtchedBorder() );
      teamPanel6.setLayout( null );
      contentPane.add( teamPanel6 );
      
      teamLabel6 = new JLabel();
      teamLabel6.setBounds( 05, 15, 100, 20 );
      teamLabel6.setText( "Comments:" );
      teamPanel6.add( teamLabel6 );
    
      teamComments = new JTextField();
      teamComments.setBounds(90, 5, 180, 90);
      teamComments.setVisible(true);
      //teamComments.setLineWrap(true);
      //teamComments.setWrapStyleWord(true);
      teamPanel6.add(teamComments);
      teamComments.addActionListener(this);
      
      //Add computed average box
      teamPanel7 = new JPanel();
      teamPanel7.setBounds(40, 440, 276, 48 );
      teamPanel7.setBorder( BorderFactory.createEtchedBorder() );
      teamPanel7.setLayout( null );
      contentPane.add( teamPanel7 );
      
      teamLabel7 = new JLabel();
      teamLabel7.setBounds( 05, 01, 100, 20 );
      teamLabel7.setText( "Computed Avg:" );
      teamPanel7.add( teamLabel7 );
    
      //set up calc avg button
       calcAvg = new JButton( "Calc Avg" );
      calcAvg.setBounds(05, 20, 90, 25);
      calcAvg.setVisible(true);
      teamPanel7.add(calcAvg);
      calcAvg.addActionListener(this);
      
     
    teamLabel8 = new JLabel();
    teamLabel8.setBounds( 220, 15, 100, 20 );
    //teamLabel8.setText(String.valueOf(Avg));
    teamPanel7.add( teamLabel8 );
      
    //set up clear button
      clearButton = new JButton( "CLEAR" );
      clearButton.setBounds (275, 525, 80, 46);
      clearButton.setVisible(true);
      contentPane.add(clearButton);
      clearButton.addActionListener(this);
    
    
    
      loadTeams();
    
  

      setTitle( "EVAL" );   // set title bar string
      setSize( 375, 610 ); // set window size
      setVisible( true );  // display window
    }

   
    private void loadTeams()
    {
         // get all account numbers from database
      try
      {
        
          myResultSet = myStatement.executeQuery( "SELECT DISTINCT TEAMNAME FROM APP.TEAMS");
       
         while ( myResultSet.next() )
         {
               teamComboBox.addItem(myResultSet.getString( "TEAMNAME" ) );
         }

         myResultSet.close(); // close myResultSet

      } // end try

      catch ( SQLException exception )
      {
         exception.printStackTrace();
      }
    }


    @Override
    public void actionPerformed(ActionEvent event)
    {
        
        
        teamName = (String)teamComboBox.getSelectedItem();
        //comments = (String)teamComments.getSelectedText();
        comments = teamComments.getText();
        
        if (event.getSource() == calcAvg)
        {
            Avg = ((q1 + q2 + q3 + q4) / 4);
            teamLabel8.setText(String.valueOf(Avg));
            submitButton.setEnabled(true);
        }
       
        
          if (event.getSource() == submitButton)
        {
            String submit = "You've hit the submit button.";
            JOptionPane.showMessageDialog(submitButton, submit, "Submit", JOptionPane.PLAIN_MESSAGE);
            updateDatabase();
        }
          
        if (event.getSource() == clearButton)
        {
            teamComboBox.setSelectedIndex( 0 );
            teamSlider1.setValue(0);
            teamSlider2.setValue(0);
            teamSlider3.setValue(0);
            teamSlider4.setValue(0);
            submitButton.setEnabled(false);
            teamComments.setText(null);
            teamLabel8.setText(null);
        }
               
    }
    
    @Override
    public void itemStateChanged(ItemEvent event)
    {
        
       
    }
    
    @Override
    public void stateChanged( ChangeEvent event )
	{
            
            
            if( event.getSource() == teamSlider1  ||  event.getSource() == teamSlider2 || event.getSource() == teamSlider3 || event.getSource() == teamSlider4)
            {
                q1 = teamSlider1.getValue();
                q2 = teamSlider2.getValue();
                q3 = teamSlider3.getValue();
                q4 = teamSlider4.getValue();
            }
        }
    
    public void updateDatabase()
    {
        try
        {
        int team = Integer.parseInt(teamName);
        String computedAverage = String.valueOf(Avg);
        String sql = "UPDATE APP.TEAMS SET Q1TECHNICAL='" + q1 + "', Q2USEFUL='" + q2 + "', Q3CLARITY='" + q3 + "', Q4OVERALL='" + q4 + "', COMMENTS='" + comments + "', COMPUTEDAVERAGE='" + computedAverage + "' WHERE TEAMNAME= " + team +"";
        myStatement.executeUpdate(sql);
        }
        catch( SQLException exception )
      {
         exception.printStackTrace();
      }
    }
}
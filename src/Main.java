import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main {
    public static String[] dataDB;
    public static void main(String[] args) {
        database();
        display();

    }
    public static void database(){
        String userName = "postgres";
        String password = "1812";
        String connectionURL="jdbc:postgresql://localhost:5432/calc";
        try(Connection con = DriverManager.getConnection(connectionURL,userName,password);
        Statement statement = con.createStatement())
        {
            System.out.println("Подключено");
            int rows=0;
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM calc");
            while (resultSet.next()) {
                rows = resultSet.getInt("count");
            }
            resultSet  = statement.executeQuery("select * from calc");
            int i=0;
            dataDB = new String[rows];
            while (resultSet.next()) {
                System.out.println(resultSet.getString("model"));
                dataDB[i] = resultSet.getString("model");
               i++;
           }

        } catch (SQLException e) {
        }
    }

    private static void display() throws HeadlessException{
        JFrame window = new JFrame("База данных");
        JButton exitButton = new JButton("--выход--");
        JButton resetButton = new JButton("обновить");
        ActionListener exit = e -> System.exit(0);
        exitButton.addActionListener(exit);
        //iconPanel settings
        Box iconPanel = new Box(BoxLayout.PAGE_AXIS);
        iconPanel.add(resetButton);
        iconPanel.add(exitButton);
        iconPanel.setBackground(Color.darkGray);
        iconPanel.setVisible(true);
        Box.createVerticalGlue();
        window.add(iconPanel,BorderLayout.WEST);
        //grid setting
        JPanel grid = new JPanel() {

            @Override
            // arbitrary placeholder size
            public Dimension getPreferredSize() {
                return new Dimension(500, 400);
            }
        };
        grid.setBackground(Color.gray);
        window.add(grid, BorderLayout.CENTER);

        //ComboBox settings
        JComboBox<String> comboBox = new JComboBox<>(dataDB);
        comboBox.setEditable(false);
        comboBox.setSize(1000,5);
        grid.add(comboBox,BorderLayout.CENTER);
        //window setting
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setBackground(Color.darkGray);
    }


}
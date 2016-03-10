import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Select_Update_Delete_Shelf_Data extends JPanel {
	private JButton Update;
	private JButton Delete;
	// private static JComboBox shelfNameList = new JComboBox();
	static DefaultComboBoxModel model = new DefaultComboBoxModel();
	private static JComboBox shelfNameList = new JComboBox(model);
	private static String delete, update1, update2;
	private static BufferedImage image1;

	public Select_Update_Delete_Shelf_Data() {
		// try {
		// image1 = ImageIO.read(new File(
		// "D:\\SAIF\\OPEN CV Eg Images\\s\\try.jpg"));
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		Dimension dim = getPreferredSize();
		dim.width = 200;
		setPreferredSize(dim);

		Update = new JButton("UPDATE");
		Delete = new JButton("DELETE");
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FloorPlan.check) {
					delete = (String) shelfNameList.getSelectedItem();
					if (delete != null && delete != "") {
						delete();
						select();
					} else {
						JOptionPane
								.showMessageDialog(null, "No Data Available");
					}
				}
			}
		});

		Update.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (FloorPlan.check) {
					update1 = (String) shelfNameList.getSelectedItem();
					if (update1 != null && update1 != "") {
						update2 = JOptionPane
								.showInputDialog("Enter Shelf Name: ");
						if (update2 != null && update2 != "") {
							update();
							select();
						} else {
							JOptionPane.showMessageDialog(null, "Invalid Data");
						}
					} else {
						JOptionPane
								.showMessageDialog(null, "No Data Available");
					}

				}
			}
		});
		shelfNameList.setBorder(BorderFactory.createEtchedBorder());
		Border innerBorder = BorderFactory.createTitledBorder("Shelf Data");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.weighty = 0.1;

		gc.gridx = 0; // column
		gc.gridy = 0;// row
		gc.anchor = GridBagConstraints.CENTER;
		add(shelfNameList, gc);

		gc.weighty = 0;

		gc.gridx = 0; // column
		gc.gridy = 1;// row
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.CENTER;
		add(Update, gc);

		gc.weighty = 2;

		gc.gridx = 1; // column
		gc.gridy = 1;// row
		gc.anchor = GridBagConstraints.CENTER;
		add(Delete, gc);

	}

	public static void update() {
		Connection c = null;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:SmartShoppingSolution.db");
			c.setAutoCommit(false);
			preparedStatement = c
					.prepareStatement("UPDATE SSS_Table set Shelf_Name = ? where Shelf_Name= ?;");
			preparedStatement.setString(1, update2);
			preparedStatement.setString(2, update1);
			preparedStatement.executeUpdate();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	public static void delete() {
		Connection c = null;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:SmartShoppingSolution.db");
			c.setAutoCommit(false);
			preparedStatement = c
					.prepareStatement("DELETE FROM SSS_Table WHERE Shelf_Name = ?");
			preparedStatement.setString(1, delete);
			preparedStatement.executeUpdate();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	public static void select() {
		Connection c = null;
		Statement stmt = null;
		try {
			String shelf_name = "null";
			int x = 0, y = 0;
			image1 = ImageIO.read(new File(
					"D:\\SAIF\\OPEN CV Eg Images\\s\\try.jpg"));
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:SmartShoppingSolution.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM SSS_Table;");
			shelfNameList.removeAllItems();
			while (rs.next()) {
				shelf_name = rs.getString("Shelf_Name");
				x = rs.getInt("X_Co_ordinate");
				y = rs.getInt("Y_Co_ordinate");
				{
					shelfNameList.addItem(shelf_name);
					FloorPlan.painting(x, y, shelf_name, image1);

				}
			}
			FloorPlan.painting(x, y, shelf_name, image1);

			rs.close();
			stmt.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
}

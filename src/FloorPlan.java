import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FloorPlan extends JPanel {
	private static int x_coordinate, y_coordinate;
	private static String shelfName;
	private JButton openFileButton;
	private JFileChooser fileChooser;
	private static JLabel image;
	public static boolean check = false;

	public FloorPlan() {
		Rgb2GrayAndChecking.convertingImageIntoGrayScale();
		setLayout(new BorderLayout());
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FloorPlanFileFilter());
		openFileButton = new JButton("SELECT FLOOR PLAN");
		add(openFileButton, BorderLayout.SOUTH);
		openFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						image.setIcon(new ImageIcon(ImageIO.read(file)));
						check = true;
						Select_Update_Delete_Shelf_Data.select();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		showingimage();
		image.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (check == true) {
					if (!Rgb2GrayAndChecking.checking(e.getX(), e.getY())) {
						JOptionPane
								.showMessageDialog(null, "Invalid Selection");
					} else {
						String shelfname = JOptionPane
								.showInputDialog("Enter Shelf Name:");
						if (shelfname == "" || shelfname == null) {
						} else if (shelfname != null && shelfname != "") {
							x_coordinate = e.getX();
							y_coordinate = e.getY();
							shelfName = shelfname;
							adding();
							Select_Update_Delete_Shelf_Data.select();

						}
					}
				}
			}
		});
	}

	private void showingimage() {
		image = new JLabel();
		add(new JScrollPane(image), BorderLayout.CENTER);
	}

	public static void painting(int x, int y, String name, BufferedImage i) {
		Graphics g = i.getGraphics();
		g.setColor(Color.BLACK);
		g.setFont(g.getFont().deriveFont(10f));
		g.drawString(name, x, y);
		g.dispose();
		try {
			ImageIO.write(i, "jpg", new File(
					"C:\\Users\\Jalal Wasif\\Documents\\try.jpg"));
			image.setIcon(new ImageIcon(ImageIO.read(new File(
					"C:\\Users\\Jalal Wasif\\Documents\\try.jpg"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void adding() {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:SmartShoppingSolution.db");
			c.setAutoCommit(false);
			String sql = "INSERT INTO SSS_Table (X_Co_ordinate,Y_Co_ordinate,Shelf_Name) "
					+ "VALUES (? , ? , ?);";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, x_coordinate);
			ps.setInt(2, y_coordinate);
			ps.setString(3, shelfName);
			ps.executeUpdate();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
}

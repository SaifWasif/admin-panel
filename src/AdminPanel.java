import java.awt.BorderLayout;

import javax.swing.JFrame;

public class AdminPanel extends JFrame {
	private FloorPlan floorPlan;
	private Select_Update_Delete_Shelf_Data select_update_delete_shelf_data;

	public AdminPanel() {
		super("Smart Shopping Solution");
		setLayout(new BorderLayout());
		floorPlan = new FloorPlan();
		select_update_delete_shelf_data = new Select_Update_Delete_Shelf_Data();
		add(select_update_delete_shelf_data, BorderLayout.EAST);
		add(floorPlan, BorderLayout.CENTER);
		setSize(720, 545);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		this.setResizable(false);
		setVisible(true);
	}
}

package pims.view;

public class InvTable {

	//Values for Inventory Table
		int id;
		String itemName;
		String description;
		String type;
		String brand;
		int quantity;
		
		public InvTable(int id, String item, String desc, String type, String brnd, int qty){
			
			this.id = id;
			this.itemName = item;
			this.description = desc;
			this.type = type;
			this.brand = brnd;
			this.quantity = qty;
			
		}
		
		//Getters
		public int getId() {
			return this.id;
		}
		public String getItemName() {
			return this.itemName;
		}
		public String getDescription() {
			return this.description;
		}
		public String getType() {
			return this.type;
		}
		public String getBrand() {
			return this.brand;
		}
		public int getQuantity() {
			return this.quantity;
		}
		
		//Setters
		public void setId(int id) {
			this.id = id;
		}
		public void setItemName(String item) {
			this.itemName = item;
		}
		public void setDescription(String desc) {
			this.description = desc;
		}
		public void setType(String type) {
			this.type = type;
		}
		public void setBrand(String brnd) {
			this.brand = brnd;
		}
		public void setQuantity(int qty) {
			this.quantity = qty;
		}
	
}

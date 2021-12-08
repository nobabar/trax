public class main {
	public static void main(String[] args) {
		Tiles droit0 = new Tiles(TileModel.CROSS, 0);
		
		Tiles tourne0 = new Tiles(TileModel.CURVE, 0);
		Tiles tourne1 = new Tiles(TileModel.CURVE, 1);
		Tiles tourne2 = new Tiles(TileModel.CURVE, 2);
		Tiles tourne3 = new Tiles(TileModel.CURVE, 3);
		
//		 System.out.println("tourne0 : \n" + tourne0);
//		 System.out.println("tourne1 : \n" + tourne1);
//		 System.out.println("tourne2 : \n" + tourne2);
//		 System.out.println("tourne3 : \n" + tourne3);
		
		System.out.println(droit0.toIcon().getIconHeight());
	}
}

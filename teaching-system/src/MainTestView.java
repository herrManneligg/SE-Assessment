

public class MainTestView {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model = new Model();
		InitialViewController initialViewControllerobject= new InitialViewController(model);
		InitialView initialView = new InitialView(model, initialViewControllerobject, System.out);
		System.out.println(initialView);

	}

}

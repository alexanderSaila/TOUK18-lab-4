public class Main {
    public static void main(String[] args) {

        startGame();

    }

    public static void startGame(){
        Controller controller = new Controller();

        RuleEngine ruleEngine = new RuleEngine();
        GUI gui = new GUI(controller);

        controller.setRuleEngine(ruleEngine);
        controller.setGui(gui);
    }
}

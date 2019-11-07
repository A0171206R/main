package wallet;

import org.apache.commons.logging.Log;
import wallet.logic.LogicManager;
import wallet.ui.Ui;

public class Main {
    /**
     * The Ui object that handles input and output of the application.
     */
    private Ui ui;
    /**
     * The TaskList object that handles the list of task added by the user.
     */
    private LogicManager logicManager;

    /**
     * Constructs a new Main object.
     */
    public Main() {
        ui = new Ui();
        logicManager = new LogicManager();
    }

    public static void main(String[] args) {
        new Main().run();
    }

    /**
     * Execute and run the Duke application.
     */
    public void run() {
        ui.welcomeMsg();
        boolean isExit = false;
        LogicManager.getReminder().autoRemindStart();
        while (!isExit) {
            String fullCommand = ui.readLine();
            if(fullCommand.equals("help")){
               ui.showHelp(LogicManager.getHelpList());
               continue;
            }
            //ui.printLine();
            isExit = logicManager.execute(fullCommand);
            //ui.printLine();
        }
        ui.byeMsg();
        LogicManager.getReminder().autoRemindStop();
    }
}

package commands;

import containers.ManagerMapAccess;
import entities.Consultant;
import entities.Manager;
import interfaces.IOAccess;

public class AddManager extends CommandStatus implements Command  {

    public void execute() {
        // before adding the manager, check if one exists with the same employee number
        if (ManagerMapAccess.getInstance().containsKey(cmdArgument.mEN)){
            successful =false;
            errorMessage =
                    "manager not added as there already " + "is a student with the Employee Number "
                            + cmdArgument.mEN;
        }


        //String response = IOAccess.getInstance().readString("Is the manager a consultant? (yes or no)");

        Manager mgr;
        if (cmdArgument.response.charAt(0) == 'y' || cmdArgument.response.charAt(0) == 'Y')
            mgr = new Consultant(cmdArgument.mName, cmdArgument.mSIN, cmdArgument.mEN);
        else
            mgr = new Manager(cmdArgument.mName, cmdArgument.mSIN, cmdArgument.mEN);

        // put the manager in the map
        Manager sameNumbermanager = ManagerMapAccess.getInstance().put(cmdArgument.mEN, mgr);
        if (sameNumbermanager != null) {
            // if put() returns a reference, then a manager was already stored with the same EN,
            // so put it back, and signal an error.
            ManagerMapAccess.getInstance().put(cmdArgument.mEN, sameNumbermanager); // put the original manager back
            successful = false;
            errorMessage = "Employee number in the manager dictionary even though " + "containsKey failed.  Manager " + cmdArgument.mName + " not entered.";
        }
        else
            successful= true;
    }


}

package Users;

import Utility.InputUtils;

public interface UserUI<U extends User, R extends UserRepo>{

     U displayLogin(R userRepo);
     U login(R userRepo);
    void forgetPassword(R userRepo);
    void displayDashboard(U user);

    default void exitToMenu(){
        System.out.println("Returning to Menu...");
        InputUtils.printBigDivider();
    }

}

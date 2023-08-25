
package com.crio.codingame.commands;

import java.util.List;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.User;
import com.crio.codingame.services.ContestService;
import com.crio.codingame.services.IUserService;

public class CreateUserCommand implements ICommand{

    private final IUserService userService;
    
    public CreateUserCommand(IUserService userService) {
        this.userService = userService;
    }

    // TODO: CRIO_TASK_MODULE_CONTROLLER
    // Execute create method of IUserService and print the result.
    // Look for the unit tests to see the expected output.
    // Sample Input Token List:- ["CREATE_QUESTION","Ross"]

    @Override
    public void execute(List<String> tokens) {
        // Integer id = Integer.parseInt(tokens.get(1));
       // String title = tokens.get(1);
        //String name = tokens.get(2);
       // Integer score = Integer.parseInt(tokens.get(3));
        //User question = userService.create(title,name);
        System.out.println(userService.create(tokens.get(1)).toString());
    }
    
}

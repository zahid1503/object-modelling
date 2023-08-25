package com.crio.codingame.commands;

import java.util.List;

//import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.services.IUserService;

public class AttendContestCommand implements ICommand{

    private final IUserService userService;
    
    public AttendContestCommand(IUserService userService) {
        this.userService = userService;
    }

    // TODO: CRIO_TASK_MODULE_CONTROLLER
    // Execute attendContest method of IUserService and print the result.
    // Also Handle Exceptions and print the error messsages if any.
    // Look for the unit tests to see the expected output.
    // Sample Input Token List:- ["ATTEND_CONTEST","3","Joey"]
    // Hint - Use Parameterized Exceptions in the Service class to match with the Unit Tests Output.

    @Override
    public void execute(List<String> tokens) {
        String contestName = tokens.get(1);

        String userName = tokens.get(2);

        // String contestId = tokens.get(1);

        try {
            // UserRegistrationDto userRegistrationDto =
            // userService.withdrawContest(contestId, userName);
            System.out.println(userService.attendContest(contestName, userName));
            // System.out.println(registerationStatus = NOT_REGISTERED);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }
    
}

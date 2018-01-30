package com.epam.command;


import com.epam.command.impl.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    private static Logger logger = Logger.getLogger(CommandProvider.class);

    private final static CommandProvider instance = new CommandProvider();
    private Map<CommandName, Command> repository = new HashMap<>();

    private CommandProvider() {
        repository.put(CommandName.INDEX, new Index());
        repository.put(CommandName.SIGN_IN, new SignIn());
        repository.put(CommandName.SIGN_UP, new SignUp());
        repository.put(CommandName.SIGN_OUT, new SignOut());
        repository.put(CommandName.MEDICAMENT, new GetMedicamentById());
        repository.put(CommandName.MEDICAMENTS_BY_PRODUCER, new GetMedicamentsByProducer());
        repository.put(CommandName.MEDICAMENTS_BY_NAME, new GetMedicamentsByName());
        repository.put(CommandName.MEDICAMENTS_ASC_SORTED_BY_PRICE, new GetAscSortedByPriceMedicaments());
        repository.put(CommandName.MEDICAMENTS_DESC_SORTED_BY_PRICE, new GetDescSortedByPriceMedicaments());
        repository.put(CommandName.ADD_MEDICAMENT, new AddMedicament());
        repository.put(CommandName.EDIT_MEDICAMENT, new EditMedicament());
        repository.put(CommandName.NEW_PRESCRIPTION, new NewPrescription());
        repository.put(CommandName.ADD_PRESCRIPTION, new AddPrescription());
        repository.put(CommandName.ADD_TO_CART, new AddToCart());
        repository.put(CommandName.PAY, new Pay());
        repository.put(CommandName.GET_MY_CART, new GetCartByUserId());
        repository.put(CommandName.GET_MY_ORDERS, new GetOrdersByUserId());
        repository.put(CommandName.ADD_USER, new AddUser());
        repository.put(CommandName.GET_USERS, new GetUsers());
        repository.put(CommandName.GET_MEDICAMENTS, new GetMedicaments());
        repository.put(CommandName.GET_MY_PRESCRIPTIONS, new GetPrescriptionsByUserId());
        repository.put(CommandName.GET_MY_REQUESTS, new GetRequestsByDoctorId());
        repository.put(CommandName.ADD_REQUEST, new AddRequest());
        repository.put(CommandName.GET_DOSAGES_BY_MEDICAMENT_ID, new GetDosagesByMedicamentId());
        repository.put(CommandName.GET_AND_EDIT_MEDICAMENT, new GetMedicamentByIdForPharmacist());
        repository.put(CommandName.SET_LOCALE, new SetLocale());
    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public Command getCommand(HttpServletRequest request) {
        Command iCommand = repository.get(CommandName.WRONG_REQUEST);
        String command = request.getRequestURI().replace("/","");
        command = command.replace(".do","");
        try {
            CommandName commandName = CommandName.valueOf(command.toUpperCase().replace('-', '_'));
            iCommand = repository.get(commandName);
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", e.getMessage());
        }
        return iCommand;
    }
}

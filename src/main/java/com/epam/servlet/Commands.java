package com.epam.servlet;

import com.epam.command.CommandName;
import com.epam.service.utils.Constants;

import java.util.HashSet;
import java.util.Set;

public class Commands {

    private static final Commands INSTANCE = new Commands();

    private Set<CommandName> adminRepository = new HashSet<>();
    private Set<CommandName> doctorRepository = new HashSet<>();
    private Set<CommandName> pharmacistRepository = new HashSet<>();
    private Set<CommandName> userRepository = new HashSet<>();

    private Commands(){
        adminRepository.add(CommandName.ADD_USER);
        adminRepository.add(CommandName.GET_USERS);
        doctorRepository.add(CommandName.GET_USERS);
        doctorRepository.add(CommandName.ADD_PRESCRIPTION);
        doctorRepository.add(CommandName.EXTEND_REQUEST);
        doctorRepository.add(CommandName.GET_MEDICAMENTS_WITH_PRESCRIPTION);
        doctorRepository.add(CommandName.GET_MY_REQUESTS);
        doctorRepository.add(CommandName.NEW_PRESCRIPTION);
        doctorRepository.add(CommandName.REJECT_REQUEST);
        pharmacistRepository.add(CommandName.ADD_MEDICAMENT);
        pharmacistRepository.add(CommandName.EDIT_MEDICAMENT);
        pharmacistRepository.add(CommandName.GET_AND_EDIT_MEDICAMENT);
        userRepository.add(CommandName.ADD_REQUEST);
        userRepository.add(CommandName.ADD_TO_CART);
        userRepository.add(CommandName.DELETE_FROM_CART);
        userRepository.add(CommandName.GET_MY_CART);
        userRepository.add(CommandName.GET_MY_ORDERS);
        userRepository.add(CommandName.GET_MY_PRESCRIPTIONS);
        userRepository.add(CommandName.PAY);
    }

    public static Commands getInstance(){
        return INSTANCE;
    }

    public String checkCommand(String command){
        CommandName commandName = CommandName.valueOf(command.toUpperCase());
        if(adminRepository.contains(commandName)){
            return Constants.ADMIN;
        }
        if(doctorRepository.contains(commandName)){
            return Constants.DOCTOR; }
        if(pharmacistRepository.contains(commandName)){
            return Constants.PHARMACIST;
        }
        if(userRepository.contains(commandName)){
            return Constants.USER;
        }
        return Constants.GUEST;
    }


}

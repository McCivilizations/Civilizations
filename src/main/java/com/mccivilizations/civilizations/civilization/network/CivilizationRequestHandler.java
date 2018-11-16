package com.mccivilizations.civilizations.civilization.network;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.network.reactive.IRequestHandler;

import java.util.function.Consumer;

public class CivilizationRequestHandler implements IRequestHandler<String, Civilization> {
    @Override
    public void handleRequest(String method, String param, Consumer<Civilization> callBack) {
        switch(method) {
            case Civilization.NAME:
                Civilizations.proxy.getCivilizationHandler().getCivilizationByName(param, callBack);
            default:
                callBack.accept(CivilizationsAPI.THE_FORGOTTEN);
        }
    }
}

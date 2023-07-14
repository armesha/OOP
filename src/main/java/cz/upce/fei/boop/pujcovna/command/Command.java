package cz.upce.fei.boop.pujcovna.command;

import cz.upce.fei.boop.pujcovna.kolekce.KolekceException;

public interface Command {
    void execute() throws KolekceException;
}

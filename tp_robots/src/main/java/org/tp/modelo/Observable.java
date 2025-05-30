package org.tp.modelo;

import org.tp.controlador.Subscriber;

public interface Observable {
    void update();
    void addObserver(Subscriber suscriptor);
    void removeObserver(Subscriber suscriptor);
}

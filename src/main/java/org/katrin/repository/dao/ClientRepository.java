package org.katrin.repository.dao;

import org.katrin.entity.Client;
import org.katrin.exception.ClientAlreadyExistsException;
import org.katrin.exception.EntityInstanceDoesNotExist;


public interface ClientRepository {
    Client add(Client client) throws ClientAlreadyExistsException;

    Client getById(int id);

    Client getByContactDataAndPassword(Client client) throws EntityInstanceDoesNotExist;
}

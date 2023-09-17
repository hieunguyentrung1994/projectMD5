package ra.securotyProject.service;

import ra.securotyProject.exception.AlreadyExistException;
import ra.securotyProject.exception.NotfoundException;

import java.util.List;

// T là Response
// K là Request
// E là Long
public interface IGenericService<T,K,E>{
    List<T> finAll();
    T findById(E id);
    T save(K k) throws AlreadyExistException;
    T update(K k, E id) throws NotfoundException;
    T delete(E id) throws NotfoundException;

}

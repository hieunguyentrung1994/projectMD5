package ra.securotyProject.service;
// T là chủ chính
// K là request
// Vlaf restPonse
public interface IGenericMapper<T,K,V> {
    T toEntity(K k);
    V toResponse(T t);
}
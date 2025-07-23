package learner.drew.ScreenMatch.service;

import com.fasterxml.jackson.databind.ObjectMapper;


public class DataConverterService implements IDataConverterService {
    private ObjectMapper mapper = new ObjectMapper();


    @Override // Retorno genérico
    public <T> T getData(String json, Class<T> clazz) {
        try{
            return mapper.readValue(json,clazz);
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }
}

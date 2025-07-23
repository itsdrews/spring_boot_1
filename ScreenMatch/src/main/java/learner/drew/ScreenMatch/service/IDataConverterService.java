package learner.drew.ScreenMatch.service;

public interface IDataConverterService {
   <T> T getData(String json, Class<T> clazz);
}

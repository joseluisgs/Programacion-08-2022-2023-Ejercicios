package service.storage.base;

import java.util.List;

public interface StorageServiceBase<T> {
    public void safeAll(List<T> entities) throws Exception;
    public List<T> loadAll() throws Exception;
}
